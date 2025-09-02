# 🤖 LangGraph Supervisor Test

A simple implementation of LangGraph multi-agent supervisor system with three specialized agents.

## Features

- **Math Expert**: Handles mathematical calculations (addition, multiplication, division)
- **Research Expert**: Performs web searches and information gathering
- **Joke Expert**: Creates jokes on various topics
- **Supervisor Agent**: Coordinates and delegates tasks to appropriate agents
- **LangSmith Integration**: Tracing and monitoring support

## Setup

1. Install dependencies:
```bash
uv sync
```

2. Create a `.env` file in the project root:
```bash
# OpenAI API Key
OPENAI_API_KEY=your_openai_api_key_here

# LangSmith Configuration (optional)
LANGCHAIN_API_KEY=your_langchain_api_key_here
LANGCHAIN_TRACING_V2=true
LANGCHAIN_PROJECT=langgraph-supervisor-test
```

3. Run the application:
```bash
python main.py
```

## Usage Examples

### Math Calculations
```
You: What's 25 * 4 + 10?
Assistant: I'll help you calculate 25 * 4 + 10. Let me break this down step by step...
```

### Research Queries
```
You: What are the headcounts of FAANG companies?
Assistant: Here are the headcounts for each of the FAANG companies in 2024...
```

### Jokes
```
You: Tell me a programming joke
Assistant: Why do programmers prefer dark mode? Because light attracts bugs!
```

### Mixed Requests
```
You: Tell me a math joke and then calculate 15 * 3
Assistant: [Supervisor will coordinate between joke_expert and math_expert]
```

## Architecture

The supervisor uses a hierarchical multi-agent architecture where:

1. **Supervisor Agent** receives user input and decides which specialized agent to delegate to
2. **Specialized Agents** perform specific tasks using their tools
3. **Tool-based Handoff** mechanism enables communication between agents
4. **LangSmith Integration** provides tracing and monitoring

## Dependencies

- `langgraph-supervisor`: Core supervisor functionality
- `langchain-openai`: OpenAI integration
- `python-dotenv`: Environment variable management
- `langsmith`: Tracing and monitoring

## Environment Variables

- `OPENAI_API_KEY`: Required for OpenAI model access
- `LANGCHAIN_API_KEY`: Optional, for LangSmith tracing
- `LANGCHAIN_TRACING_V2`: Set to "true" to enable tracing
- `LANGCHAIN_PROJECT`: Project name for LangSmith
