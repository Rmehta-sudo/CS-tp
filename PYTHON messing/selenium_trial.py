from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
from selenium import webdriver
import urllib.request
from pynput.keyboard import Key, Controller

# SPEEDING IT UP USING WEBDRIVER.WAIT() INSTEAD OF TIME.SLEEP(1.5)   yesss

site = "https://banglarshiksha.gov.in/login"
driver = webdriver.Chrome()
driver.get(site)

img = driver.find_element(By.ID, "captcha_img4")
src = img.get_attribute("src")
#driver.get(src)

urllib.request.urlretrieve(src, "captcha.jpg")
#print(src)  #time.sleep(1)  ##
driver2 = webdriver.Chrome()
driver2.get("https://www.google.com/?olud")
driver2.find_element(By.CLASS_NAME,"DV7the").click()
time.sleep(1)  ##IMPORTANT LINE for popup to open


keyboard = Controller()
keyboard.type("file:///C:/Users/rachi/OneDrive/Desktop/CS/captcha.jpg") ## change this wherever its getting downloaded based on directory open in IDE/cmd terminal
time.sleep(1)  #IMPORTANT LINE time to fill box
keyboard.press(Key.enter)
keyboard.release(Key.enter)
WebDriverWait(driver2, 1).until(
    EC.presence_of_element_located((By.ID,"ucj-2"))
)
driver2.find_element(By.ID,"ucj-2").click()

WebDriverWait(driver2, 1.5).until(
    EC.presence_of_element_located((By.CLASS_NAME,"VfPpkd-LgbsSe.VfPpkd-LgbsSe-OWXEXe-k8QpJ.VfPpkd-LgbsSe-OWXEXe-dgl2Hf.nCP5yc.AjY5Oe.DuMIQc.LQeN7.kCfKMb"))
)
driver2.find_element(By.CLASS_NAME,"VfPpkd-LgbsSe.VfPpkd-LgbsSe-OWXEXe-k8QpJ.VfPpkd-LgbsSe-OWXEXe-dgl2Hf.nCP5yc.AjY5Oe.DuMIQc.LQeN7.kCfKMb").click()

WebDriverWait(driver2, 1).until(
    EC.presence_of_element_located((By.CLASS_NAME,"wCgoWb"))
)
number = driver2.find_element(By.CLASS_NAME,"wCgoWb").text

driver2.quit()
time.sleep(0.3)  ##

username = "----"
password = "------"

driver.find_element(By.ID,"user_name2").send_keys(username)
driver.find_element(By.ID,"user_pass2").send_keys(password)
driver.find_element(By.ID,"user_capcha4").send_keys(number)

time.sleep(0.5)

a = driver.find_elements(By.XPATH,"//button[@name ='submit1']")[4]
a.click()
time.sleep(100)
