package com.ithr.ppe.test.cucumber.pages;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {
	private static final long SLOW = 3000;
	protected WebDriver driver;
	
	public PageBase(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement waitClickable (By by) {
	   WebDriverWait wait = new WebDriverWait(driver,30);
	   WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
	   return element;
	}
	
	public WebElement waitClickable (WebElement tofind) {
		   WebDriverWait wait = new WebDriverWait(driver,10);
		   WebElement element = wait.until(ExpectedConditions.elementToBeClickable(tofind));
		   return element;
	}
	
	public boolean HandleSubWindow (String wintitle, String checkresponse) throws InterruptedException {
			boolean handled = false;
			String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
	        String subWindowHandler = null;
	       
	        // this works as long as the popup is created - if it doesnt get created it will burn forever or until test times out
	        while (driver.getWindowHandles().size() < 2) {
	            Thread.sleep(SLOW);
	        }

			Set<String> handles = driver.getWindowHandles(); // get all window handles
			Iterator<String> iterator = handles.iterator();
			while (iterator.hasNext()){
			     subWindowHandler = iterator.next();
			}
			driver.switchTo().window(subWindowHandler); // switch to popup window
			                                             
			// Check the window is the one we want and set handled accordingly
			if (driver.getTitle().contentEquals(wintitle)) {
				String winText = driver.findElement(By.cssSelector("h1")).getText();
				if (winText.contains(checkresponse)) {
					driver.close();
					handled = true;
				}
			}
			
			driver.switchTo().window(parentWindowHandler);  // switch back to parent window
			return handled;
	}
		 
	public boolean isAlertPresent(){
		       try{
		           driver.switchTo().alert();
		           return true;
		       }//try
		       catch(Exception e){
		           return false;
		       }//catch
	}
		 
	public boolean isElementPresent(By by) {
			try {
			 driver.findElement(by);
			 return true;
			} catch (NoSuchElementException e) {
			 return false;
			}
	}
		 
	public boolean bodyLoaded() throws InterruptedException {
			
			 	boolean present = false;
			 	
			 	// this will loop until the driver timeout is triggered or it becomes true
			 	while (!present) {
			 		present = isElementPresent (By.tagName("body"));
			 		Thread.sleep(SLOW);
			 	}
			 	
			 	return present;
	}
	
	public boolean elementLoaded(By by) throws InterruptedException {
		
	 	boolean present = false;
	 	
	 	// this will loop until the driver timeout is triggered or it becomes true
	 	while (!present) {
	 		present = isElementPresent (by);
	 		Thread.sleep(SLOW);
	 	}
	 	
	 	return present;
	}
	
	
}
