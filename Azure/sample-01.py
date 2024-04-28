import openai
# imports
import time  # for measuring time duration of API calls
from openai import OpenAI
import os

openai.api_key = '4fa0071c577946ba93016cc504dbd0c9'
openai.azure_endpoint = 'https://team32.openai.azure.com/'
openai.api_type = 'azure'
openai.api_version = '2023-05-15'



# a ChatCompletion request
response = openai.chat.completions.create(
    model='dev-gpt-35-turbo-sample',
    messages=[
        {
            'role': 'system', 'content': "You're a helpful assistant",
            'role': 'user', 'content': "Count to 10, with a comma between each number and no newlines. E.g., 1, 2, 3, ...'"}
    ],
    temperature=0,
    stream=True 
)
start_time = time.time()

# create variables to collect the stream of chunks
collected_chunks = []
collected_messages = []
# iterate through the stream of events
for chunk in response:
    chunk_time = time.time() - start_time  # calculate the time delay of the chunk
    collected_chunks.append(chunk)  # save the event response
    chunk_message = chunk.choices[0].delta.content  # extract the message
    collected_messages.append(chunk_message)  # save the message
    print(f"{chunk_message}", end='')  # print the delay and text

# print the time delay and text received
print(f"Full response received {chunk_time:.2f} seconds after request")
# clean None in collected_messages
collected_messages = [m for m in collected_messages if m is not None]
full_reply_content = ''.join(collected_messages)
print(f"Full conversation received: {full_reply_content}")