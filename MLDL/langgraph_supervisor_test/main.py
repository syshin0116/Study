import os
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from langgraph_supervisor import create_supervisor
from langgraph.prebuilt import create_react_agent

# Load environment variables
load_dotenv()


def add(a: float, b: float) -> float:
    """Add two numbers."""
    return a + b


def multiply(a: float, b: float) -> float:
    """Multiply two numbers."""
    return a * b


def divide(a: float, b: float) -> float:
    """Divide two numbers."""
    if b == 0:
        return "Error: Cannot divide by zero"
    return a / b


def web_search(query: str) -> str:
    """Search the web for information."""
    # Mock web search function - in real implementation, you would use actual web search API
    if "FAANG" in query or "tech companies" in query:
        return (
            "Here are the headcounts for each of the FAANG companies in 2024:\n"
            "1. **Facebook (Meta)**: 67,317 employees.\n"
            "2. **Apple**: 164,000 employees.\n"
            "3. **Amazon**: 1,551,000 employees.\n"
            "4. **Netflix**: 14,000 employees.\n"
            "5. **Google (Alphabet)**: 181,269 employees."
        )
    elif "weather" in query.lower():
        return "Today's weather is sunny with a temperature of 22°C."
    elif "news" in query.lower():
        return "Latest tech news: AI developments continue to accelerate in 2024."
    else:
        return f"Search results for '{query}': Here are some relevant information about your query."


def create_joke(topic: str = "general") -> str:
    """Create a joke about a given topic."""
    jokes = {
        "programming": "Why do programmers prefer dark mode? Because light attracts bugs!",
        "math": "Why was 6 afraid of 7? Because 7, 8 (ate), 9!",
        "general": "Why don't scientists trust atoms? Because they make up everything!",
        "ai": "Why did the neural network break up with the decision tree? It said their relationship wasn't deep enough!",
    }
    return jokes.get(topic.lower(), jokes["general"])


def setup_langsmith():
    """Setup LangSmith tracing"""
    # Set LangSmith environment variables
    os.environ["LANGCHAIN_TRACING_V2"] = "true"
    os.environ["LANGCHAIN_PROJECT"] = "langgraph-supervisor-test"

    # Check if LANGCHAIN_API_KEY is set
    if not os.getenv("LANGCHAIN_API_KEY"):
        print("Warning: LANGCHAIN_API_KEY not found in environment variables.")
        print(
            "Please set your LangSmith API key in the .env file or environment variables."
        )


def main():
    """Main function to run the LangGraph supervisor"""

    # Setup LangSmith
    setup_langsmith()

    # Check for OpenAI API key
    if not os.getenv("OPENAI_API_KEY"):
        print("Error: OPENAI_API_KEY not found in environment variables.")
        print(
            "Please set your OpenAI API key in the .env file or environment variables."
        )
        return

    # Initialize the model
    model = ChatOpenAI(model="gpt-4.1", temperature=0.1)

    # Create specialized agents with explicit v1 version for parallel execution
    math_agent = create_react_agent(
        model=model,
        tools=[add, multiply, divide],
        name="math_expert",
        prompt="You are a math expert. Always use one tool at a time. Provide clear explanations of your calculations.",
        version="v1",  # Explicitly use v1 for parallel tool execution
    )

    research_agent = create_react_agent(
        model=model,
        tools=[web_search],
        name="research_expert",
        prompt="You are a world class researcher with access to web search. Do not do any math calculations.",
        version="v1",  # Explicitly use v1 for parallel tool execution
    )

    joke_agent = create_react_agent(
        model=model,
        tools=[create_joke],
        name="joke_expert",
        prompt="You are a comedy expert who creates jokes. Use the create_joke tool to generate appropriate jokes.",
        version="v1",  # Explicitly use v1 for parallel tool execution
    )

    # Create supervisor workflow with parallel execution enabled
    workflow = create_supervisor(
        [math_agent, research_agent, joke_agent],
        model=model,
        prompt=(
            "You are a team supervisor managing three specialized agents: "
            "1. math_expert - for mathematical calculations and problems "
            "2. research_expert - for web searches and information gathering "
            "3. joke_expert - for creating jokes and humor "
            "Choose the most appropriate agent based on the user's request. "
            "If the request involves multiple tasks, you can coordinate between agents in parallel when possible."
        ),
        parallel_tool_calls=True,  # Enable parallel agent execution
    )

    # Compile the workflow
    app = workflow.compile()

    print("🤖 LangGraph Supervisor is ready!")
    print("Available agents: Math Expert, Research Expert, Joke Expert")
    print("Type 'quit' to exit\n")

    # Interactive loop
    while True:
        user_input = input("You: ").strip()

        if user_input.lower() in ["quit", "exit", "q"]:
            print("Goodbye!")
            break

        if not user_input:
            continue

        try:
            # Simple approach: just get the final result first
            print("\n🔄 Running supervisor...")

            result = app.invoke({"messages": [{"role": "user", "content": user_input}]})

            print("\n📋 Final Result:")
            print("=" * 50)
            if result and "messages" in result:
                for msg in result["messages"]:
                    msg.pretty_print()
            print("=" * 50 + "\n")

        except Exception as e:
            print(f"❌ Error occurred: {e}\n")


if __name__ == "__main__":
    main()
