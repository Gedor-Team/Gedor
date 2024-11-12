from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
import time
import csv
# Define an empty list to store URLs
urls = []

# Open the file and read each line
with open('url.txt', 'r') as file:
    # Read each line, strip any extra whitespace, and append to the list
    urls = [line.strip() for line in file if line.strip()]

for url in urls:
    # Set up the Selenium WebDriver
    driver = webdriver.Chrome(service=ChromeService(ChromeDriverManager().install()))
    # Navigate to the page
    driver.get(url)

    # Wait for a few seconds to allow JavaScript to load
    time.sleep(10)  # Adjust based on the content loading time

    # Click the initial button with data-tab-index 1
    initial_button = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, "//button[contains(@data-tab-index, '1')]"))
    )
    initial_button.click()
    time.sleep(1)  # Wait a moment after clicking

    # Find the first "Informasi" button and click it
    info_button = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, "//button[contains(@aria-label, 'Informasi')]"))
    )
    driver.execute_script("arguments[0].scrollIntoView();", info_button)  # Scroll to ensure visibility
    info_button.click()
    time.sleep(2)

    # Function to click on "Lihat lainnya" buttons if present
    def click_see_more_buttons():
        try:
            buttons = driver.find_elements(By.XPATH, "//button[contains(@aria-label, 'Lihat lainnya')]")
            for button in buttons:
                driver.execute_script("arguments[0].scrollIntoView();", button)  # Ensure the button is in view
                time.sleep(2)  # Wait for the button to be in view
                button.click()  # Click the button
                time.sleep(2)  # Wait a moment after clicking
        except Exception as e:
            print(f"Error clicking 'Lihat lainnya': {e}")

    # Scroll the page and click "Lihat lainnya" buttons as necessary
    for _ in range(30):  # Adjust the range if necessary
        # Scroll down using PAGE_DOWN
        html = driver.find_element(By.TAG_NAME, 'html')
        html.send_keys(Keys.END)
        time.sleep(1)  # Wait to load more content

    click_see_more_buttons()

    # After scrolling, retrieve the updated page source
    html = driver.page_source

    # Parse the updated HTML content using BeautifulSoup
    soup = BeautifulSoup(html, 'html.parser')

    # Now you can select spans or any other elements you want
    divs = soup.select('span')

    # Write the text content of selected divs to a CSV file
    with open('complaints.csv', 'a', encoding='utf-8', newline='') as file:
        writer = csv.writer(file)

        for div in divs:
            # Get the text content and convert to lowercase
            complaint_lines = div.get_text(strip=True).lower().splitlines()
            
            # Filter out empty lines and combine the remaining lines into a single string
            complaint = '\n'.join(line for line in complaint_lines if line.strip())
            category = 'others'
            if any(word in complaint for word in ["sampah", "tpa", "tps"]):
                category="sampah"
            elif any(word in complaint for word in ["limbah", "pabrik", "bau"]):
                category="limbah"
            elif any(word in complaint for word in ["polusi", "udara", "air", "berisik", "bising", "tercemar"]):
                category="polusi"
            elif any(word in complaint for word in ["hutan", "pembalakan", "pohon"]):
                category="hutan"
            elif "hewan" in complaint:
                category="hewan"
            elif any(word in complaint for word in ["jalan", "publik", "umum", "toko"]):
                category="fasilitas umum"
            elif any(word in complaint for word in ["layanan", "servis", "galak", "kasar", "surat", "sop"]):
                category="layanan"            


            if len(complaint) > 80:
                # Write each complaint as a new row in the CSV
                writer.writerow([complaint,category])
                
    print("All div contents written to complaints.csv")


    # Close the browser
    driver.quit()
