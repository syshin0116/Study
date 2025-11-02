
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
options = webdriver.ChromeOptions()
options.add_argument('--headless')
options.add_argument('--no-sandbox')

driver = webdriver.Chrome(options=options)
url = 'https://store.kyobobook.co.kr/bestseller/total?page=1&ymw=2024&per=50'
driver.get(url)
time.sleep(1)
response = driver.page_source

from bs4 import BeautifulSoup as bs

soup = bs(response, 'html.parser')

tags = soup.find_all('a', class_='prod_link line-clamp-2 font-medium text-black hover:underline fz-16 mt-2')

for i, t in enumerate(tags, start=1):
  print(f"{i}위 {t.text.strip()}")