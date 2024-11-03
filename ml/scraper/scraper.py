from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
import time

# Set up the Selenium WebDriver
driver = webdriver.Chrome(service=ChromeService(ChromeDriverManager().install()))

# URL of the webpage to scrape
url = "https://www.google.com/maps/place/Dinas+Lingkungan+Hidup+Kabupaten+Sleman/@-7.7194201,110.352458,17z/data=!3m1!4b1!4m6!3m5!1s0x2e7a58c9f80c4591:0x2283005264f518b2!8m2!3d-7.7194201!4d110.3550329!16s%2Fg%2F1pzvnkc0y?entry=ttu&g_ep=EgoyMDI0MTAyOS4wIKXMDSoASAFQAw%3D%3D"

# Navigate to the page
driver.get(url)

# Wait for a few seconds to allow JavaScript to load
time.sleep(5)  # Adjust based on the content loading time

# Click the initial button with data-tab-index 1
initial_button = WebDriverWait(driver, 10).until(
    EC.presence_of_element_located((By.XPATH, "//button[contains(@data-tab-index, '1')]"))
)
initial_button.click()
time.sleep(1)  # Wait a moment after clicking

# Find the first "Semua ulasan" button and click it
review_button = WebDriverWait(driver, 10).until(
    EC.presence_of_element_located((By.XPATH, "//button[contains(@aria-label, 'Semua ulasan')]"))
)
driver.execute_script("arguments[0].scrollIntoView();", review_button)  # Scroll to ensure visibility
review_button.click()
time.sleep(2)

# Function to click on "Lihat lainnya" buttons if present
def click_see_more_buttons():
    try:
        buttons = driver.find_elements(By.XPATH, "//button[contains(@aria-label, 'Lihat lainnya')]")
        for button in buttons:
            driver.execute_script("arguments[0].scrollIntoView();", button)  # Ensure the button is in view
            time.sleep(0.5)  # Wait for the button to be in view
            button.click()  # Click the button
            time.sleep(2)  # Wait a moment after clicking
    except Exception as e:
        print(f"Error clicking 'Lihat lainnya': {e}")

# Scroll the page and click "Lihat lainnya" buttons as necessary
for _ in range(100):  # Adjust the range if necessary
    # Scroll down using PAGE_DOWN
    html = driver.find_element(By.TAG_NAME, 'html')
    html.send_keys(Keys.PAGE_DOWN)
    time.sleep(1)  # Wait to load more content

click_see_more_buttons()
    

# After scrolling, retrieve the updated page source
html = driver.page_source

# Parse the updated HTML content using BeautifulSoup
soup = BeautifulSoup(html, 'html.parser')

# Now you can select spans or any other elements you want
divs = soup.select('span')

# Write the text content of selected divs to a text file
with open('text.txt', 'a', encoding='utf-8') as file:
    for div in divs:
        # Get the text content and convert to lowercase
        complaint_lines = div.get_text(strip=True).lower().splitlines()
        
        # Filter out empty lines and combine the remaining lines into a single string
        complaint = '\n'.join(line for line in complaint_lines if line.strip())
        
        if len(complaint) > 80:
            complaint += '\n\n'
            file.write(complaint)

print("All div contents written to text.txt")

# Close the browser
driver.quit()
