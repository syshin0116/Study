import openai
from dotenv import load_dotenv
load_dotenv()
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.document_loaders import WebBaseLoader
from langchain_community.vectorstores import Chroma
from langchain_openai.embeddings.azure import AzureOpenAIEmbeddings
from langgraph.prebuilt import ToolExecutor
from langchain.tools.retriever import create_retriever_tool
import operator
from typing import Annotated, Sequence, TypedDict
from langchain_core.messages import BaseMessage
import json
import operator
from typing import Annotated, Sequence, TypedDict
from langchain import hub
from langchain.prompts import PromptTemplate
from langchain.tools.render import format_tool_to_openai_function
from langchain_core.utils.function_calling import convert_to_openai_function
from langchain_core.utils.function_calling import convert_to_openai_tool
from langchain_core.messages import BaseMessage, FunctionMessage
from langchain.output_parsers.openai_tools import PydanticToolsParser
from langchain_core.pydantic_v1 import BaseModel, Field
from langchain_openai import AzureChatOpenAI
from langgraph.prebuilt import ToolInvocation
from langchain_core.output_parsers import StrOutputParser
from langgraph.graph import END, StateGraph
from langchain_core.messages import HumanMessage
from langchain_community.retrievers import BM25Retriever
from langchain.retrievers import EnsembleRetriever





gpt_35_model = 'dev-gpt-35-turbo-sample'
embedding_model = 'dev-text-embedding-ada-002-01' 
urls = [
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=39593&efYd=19971231#0000',
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=258015&efYd=20240101#0000',
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=260889&efYd=20240301#0000',
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=261251&efYd=20240322#0000'
]


docs = [WebBaseLoader(url).load() for url in urls]
docs_list = [item for sublist in docs for item in sublist]

text_splitter = RecursiveCharacterTextSplitter.from_tiktoken_encoder(
    chunk_size=100, chunk_overlap=50
)
doc_splits = text_splitter.split_documents(docs_list)

# 벡터 데이터베이스에 문서 추가
vectorstore = Chroma.from_documents(
    documents=doc_splits,
    collection_name="rag-chroma",
    embedding=AzureOpenAIEmbeddings(
        model=embedding_model# Azure OpenAIEmbedding model명
        ),
    persist_directory="./chroma_db"
)
retriever = vectorstore.as_retriever(search_type="mmr", search_kwargs={"k": 4})

bm25_retriever = BM25Retriever.from_documents(doc_splits)
bm25_retriever.k = 4

retriever = EnsembleRetriever(
    retrievers=[bm25_retriever, retriever], weights=[0.25, 0.75]
)



# 정보를 검색하고 반환하는 도구를 생성
tool = create_retriever_tool(
    retriever,
    "retriever",
    "Search and retrieve detailed information from government documents, including laws, regulations, and policy papers. This tool is designed to extract specific data and insights relevant to inquiries regarding governmental procedures, legal frameworks, and administrative guidelines.",
)

tools = [tool]


# 도구들을 실행할 ToolExecutor 객체를 생성
tool_executor = ToolExecutor(tools)

class AgentState(TypedDict):
    # AgentState 클래스: 메시지 시퀀스를 포함하는 타입 딕셔너리
    messages: Annotated[Sequence[BaseMessage], operator.add]
    
    
def should_retrieve(state):
    """
    에이전트가 더 많은 정보를 검색해야 하는지 또는 프로세스를 종료해야 하는지 결정합니다.

    이 함수는 상태의 마지막 메시지에서 함수 호출을 확인합니다. 함수 호출이 있으면 정보 검색 프로세스를 계속합니다. 그렇지 않으면 프로세스를 종료합니다.

    Args:
        state (messages): 현재 상태

    Returns:
        str: 검색 프로세스를 "계속"하거나 "종료"하는 결정
    """

    print("---DECIDE TO RETRIEVE---")
    messages = state["messages"]
    last_message = messages[-1]

    # 함수 호출이 없으면 종료
    if "function_call" not in last_message.additional_kwargs:
        print("---DECISION: DO NOT RETRIEVE / DONE---")
        return "end"
    # 함수 호출이 있으므로 계속
    else:
        print("---DECISION: RETRIEVE---")
        return "continue"


def grade_documents(state):
    """
    검색된 문서가 질문과 관련이 있는지 여부를 결정합니다.

    Args:
        state (messages): 현재 상태

    Returns:
        str: 문서가 관련이 있는지 여부에 대한 결정
    """

    print("---CHECK RELEVANCE---")

    # 데이터 모델
    class grade(BaseModel):
        """관련성 검사를 위한 이진 점수."""

        binary_score: str = Field(description="'yes' 또는 'no'의 관련성 점수")

    # LLM
    model = AzureChatOpenAI(
        temperature=0, model=gpt_35_model, streaming=True)

    # 도구
    grade_tool_oai = convert_to_openai_tool(grade)

    # 도구와 강제 호출을 사용한 LLM
    llm_with_tool = model.bind(
        tools=[convert_to_openai_tool(grade_tool_oai)],
        tool_choice={"type": "function", "function": {"name": "grade"}},
    )

    # 파서
    parser_tool = PydanticToolsParser(tools=[grade])

    # 프롬프트
    prompt = PromptTemplate(
        template="""You are a grader assessing relevance of a retrieved document to a user question. \n 
        Here is the retrieved document: \n\n {context} \n\n
        Here is the user question: {question} \n
        If the document contains keyword(s) or semantic meaning related to the user question, grade it as relevant. \n
        Give a binary score 'yes' or 'no' score to indicate whether the document is relevant to the question.""",
        input_variables=["context", "question"],
    )

    # 체인
    chain = prompt | llm_with_tool | parser_tool

    messages = state["messages"]
    last_message = messages[-1]

    question = messages[0].content
    docs = last_message.content

    score = chain.invoke({"question": question, "context": docs})

    grade = score[0].binary_score

    if grade == "yes":
        print("---DECISION: DOCS RELEVANT---")
        return "yes"

    else:
        print("---DECISION: DOCS NOT RELEVANT---")
        print(grade)
        return "no"


# Nodes


def agent(state):
    """
    현재 상태를 기반으로 에이전트 모델을 호출하여 응답을 생성합니다. 질문에 따라 검색 도구를 사용하여 검색을 결정하거나 단순히 종료합니다.

    Args:
        state (messages): 현재 상태

    Returns:
        dict: 메시지에 에이전트 응답이 추가된 업데이트된 상태
    """
    print("---CALL AGENT---")
    messages = state["messages"]
    model = AzureChatOpenAI(temperature=0, streaming=True,
                       model=gpt_35_model)
    functions = [convert_to_openai_function(t) for t in tools]
    model = model.bind_functions(functions)
    response = model.invoke(messages)

    print(f"""FIRST RESPONSE:
{response}
""")
    return {"messages": [response]}


def retrieve(state):
    """
    도구를 사용하여 검색을 실행합니다.

    Args:
        state (messages): 현재 상태

    Returns:
        dict: 검색된 문서가 추가된 업데이트된 상태
    """
    print("---EXECUTE RETRIEVAL---")
    messages = state["messages"]

    last_message = messages[-1]
    # 함수 호출에서 ToolInvocation을 구성
    action = ToolInvocation(
        tool=last_message.additional_kwargs["function_call"]["name"],
        tool_input=json.loads(
            last_message.additional_kwargs["function_call"]["arguments"]
        ),
    )
    # 도구 실행자를 호출하고 응답
    response = tool_executor.invoke(action)
    function_message = FunctionMessage(content=str(response), name=action.tool)

    # 이것은 기존 목록에 추가될 것이므로 리스트를 반환
    print(f"""EXECUTED RETRIEVAL:
{function_message}
""")
    return {"messages": [function_message]}


def rewrite(state):
    """
    질문을 변형하여 더 나은 질문을 생성합니다.

    Args:
        state (messages): 현재 상태

    Returns:
        dict: 재구성된 질문이 추가된 업데이트된 상태
    """

    print("---TRANSFORM QUERY---")
    messages = state["messages"]
    question = messages[0].content

    msg = [
        HumanMessage(
            content=f""" \n 
    Look at the input and try to reason about the underlying semantic intent / meaning. \n 
    Here is the initial question:
    \n ------- \n22
    {question} 
    \n ------- \n
    Formulate an improved question: """,
        )
    ]

    # 평가자
    model = AzureChatOpenAI(
        temperature=0, model=gpt_35_model, streaming=True)
    response = model.invoke(msg)
    print(f"""TRANSFORMED QUERY:
          {response}
          """)
    return {"messages": [response]}


def generate(state):
    """
    답변 생성

    Args:
        state (messages): 현재 상태

    Returns:
         dict: 재구성된 질문이 추가된 업데이트된 상태
    """
    print("---GENERATE---")
    messages = state["messages"]
    question = messages[0].content
    last_message = messages[-1]

    question = messages[0].content
    docs = last_message.content

    # 프롬프트
    prompt = hub.pull("rlm/rag-prompt")
    print("---------------------------")
    print(prompt)
    print()

    # LLM
    llm = AzureChatOpenAI(
        model=gpt_35_model,
        temperature=0, 
        streaming=True)

    # 후처리
    def format_docs(docs):
        return "\n\n".join(doc.page_content for doc in docs)

    # 체인
    rag_chain = prompt | llm | StrOutputParser()

    # 실행
    response = rag_chain.invoke({"context": docs, "question": question})
    return {"messages": [response]}


# StateGraph
workflow = StateGraph(AgentState)

# 순환할 노드들을 정의합니다.
workflow.add_node("agent", agent)  # 에이전트 노드
workflow.add_node("retrieve", retrieve)  # 정보 검색 노드
workflow.add_node("rewrite", rewrite)  # 정보 재작성 노드
workflow.add_node("generate", generate)  # 정보 생성 노드

# 에이전트 노드 호출하여 검색 여부 결정
workflow.set_entry_point("agent")

# 검색 여부 결정
workflow.add_conditional_edges(
    "agent",
    # 에이전트 결정 평가
    should_retrieve,
    {
        # 도구 노드 호출
        "continue": "retrieve",
        "end": END,
    },
)

# `action` 노드 호출 후 진행될 경로
workflow.add_conditional_edges(
    "retrieve",
    # 에이전트 결정 평가
    grade_documents,
    {
        "yes": "generate",
        "no": "rewrite",
    },
)
workflow.add_edge("generate", END)
workflow.add_edge("rewrite", "agent")

# 컴파일
app = workflow.compile()

# HumanMessage 객체를 사용하여 질문 메시지 정의
inputs = {
    "messages": [
        HumanMessage(
            content="""저는 근로소득 연봉이 대략 6000만원입니다 
전용 30m2 이하 도시형생활주택에서 월세를 이자제외하고 약 30만원 가량 받고 있습니다
이 경우 연간 임대료 2000만원 이하이기에 분리과세가 가능한 것으로 알고 있습니다 
그럼 근로소득과 임대소득을 합산하여 종합소득세를 내는 것이 아닌 
임대료로 받고 있는 30만원에 대해서만 임대소득세를 내면 되는 것일까요??
            """
        )
    ]
}

async def process_logs(app):
    async for output in app.astream_log(inputs, include_types=["llm"]):
        for op in output.ops:
            if op["path"] == "/streamed_output/-":
                pass
            elif op["path"].startswith("/logs/") and op["path"].endswith("/streamed_output/-"):
                print(op["value"])
                
                
import asyncio

asyncio.run(process_logs(app))
