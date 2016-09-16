package com.ithr.ppe.test.cucumber.pages;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixLoginOrRegister;

public class PageBase {
	public static Logger log = Logger.getLogger(PageBase.class);
	protected WebDriver driver;
	
	public PageBase(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement waitClickable (By by) {
	   WebElement element = null;
	   WebDriverWait wait = new WebDriverWait(driver,CommonConstants.SLOWSECS * 2);

	   try {
			element = wait.until(ExpectedConditions.elementToBeClickable(by));	
		} catch (TimeoutException et) {
			log.info("Element not visible after timeout " + et);
		}
	   return element;
	}
	
	public WebElement waitClickable (WebElement tofind) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver,CommonConstants.SLOWSECS * 2);
		try {
			element = wait.until(ExpectedConditions.elementToBeClickable(tofind));	
		} catch (TimeoutException et) {
			log.info("Element not visible after timeout " + et);
		}
		return element;
	}
	
	public WebElement waitVisible(WebElement tofind) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, CommonConstants.SLOWSECS);
		try {
			element = wait.until(ExpectedConditions.visibilityOf(tofind));	
		} catch (TimeoutException et) {
			log.info("Element not visible after timeout " + et);
		}
		
		return element;
	}
	
	public boolean HandleSubWindow (String wintitle, String checkresponse) {
			boolean handled = false;
			String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
	        String subWindowHandler = null;
	       
	        // this works as long as the popup is created - if it doesnt get created it will burn forever or until test times out
	        while (driver.getWindowHandles().size() < 2) {
	        	
	            try {
					Thread.sleep(CommonConstants.SLOW);
				} catch (InterruptedException e) {
					log.info("SLEEP got interrupted " + e);
				}
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
		 
	public boolean bodyLoaded()  {	
			 	boolean present = false;
			 	
			 	// this will loop until the driver timeout is triggered or it becomes true
			 	while (!present) {
			 		present = isElementPresent (By.tagName("body"));
			 		try {
						Thread.sleep(CommonConstants.SLOW);
					} catch (InterruptedException e) {
						log.info("SLEEP got interrupted " + e);
					}
			 	}		 	
			 	return present;
	}
	
	public boolean elementLoaded(By by) {
		
	 	boolean present = false;	
	 	// loop for max of 5 times SLOW to check if its there
	 	for (int i = 0; (i < 5 && !present); i++) {
	 		present = isElementPresent (by);
	 		try {
				Thread.sleep(CommonConstants.SLOW);
			} catch (InterruptedException e) {
				log.info("SLEEP got interrupted " + e);
			}
	 	}
	 	
	 	return present;
	}
	
	
}
