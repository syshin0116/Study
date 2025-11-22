from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup as bs

options = webdriver.ChromeOptions()
options.add_argument('--headless')
options.add_argument('--no-sandbox')
driver = webdriver.Chrome(options=options)

url = "https://store.kyobobook.co.kr/bestseller/total/annual?page=1&per=50"
driver.get(url)

# 특정 요소가 나타날 때까지 최대 10초 대기 (보통 1초 이내 완료)
wait = WebDriverWait(driver, 10)
wait.until(EC.presence_of_element_located((By.CLASS_NAME, "prod_link")))

response = driver.page_source
soup = bs(response, 'html.parser')

tags = soup.find_all('a', class_='prod_link line-clamp-2 font-medium text-black hover:underline fz-16 mt-2')
for i, t in enumerate(tags, start=1):
    print(f"{i}위 {t.text.strip()}")

driver.quit()
