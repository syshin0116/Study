# Q&A Chatbot
from langchain_community.llms import OpenAI
from langchain_community.llms import HuggingFaceHub
from langchain.chains import LLMChain
from langchain.prompts import PromptTemplate
from dotenv import load_dotenv
import streamlit as st
import os

load_dotenv() # take enviroment variables form .env file

## Function to load OpenAI model and get response

def get_openai_response(question):
    # llm = OpenAI(openai_api_key=os.environ["OPEN_API_KEY"], model_name = "text-davinci-003", temperature=0.5)
    llm_hugginface = HuggingFaceHub(repo_id="google/flan-t5-large", model_kwargs={"temperature":0.5, "max_length":516})
    template = """Question: {question}
    Answer: Let's think step by step."""
    prompt = PromptTemplate(template=template, input_variables=["question"])
    llm_chain = LLMChain(prompt=prompt, llm=llm_hugginface)
    response = llm_chain.invoke(question)['text']
    return response

## initialize streamlit app
st.set_page_config(page_title="Q&A Demo")
st.header("Langchain Application")

input = st.text_input("Input: ", key="input")
response = get_openai_response(input)
submit = st.button("Submit")

## if button is clicked
if submit:
    st.subheader("The Response is")
    st.write(response)