import json

DATA_PATH = "./data.json"

with open(DATA_PATH) as f:
    data = json.load(f)

print(f"{data['monster']}")
