package com.ithr.ppe.bdd.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.bdd.commons.CommonConstants;
import com.ithr.ppe.bdd.cucumber.pages.PageBase;

/**
 * Implements the Page Object Model for Netflix marketing offer page - just looking to hit submit
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
public class NetflixOffer extends PageBase {
	public static Logger log = Logger.getLogger(NetflixOffer.class);
	@FindBy(css="button[type='submit']")
	WebElement submit;
	
	
	public  NetflixOffer (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	}
	
	public void clickSubmit() {	
		for (int i = 0; i < 5 && !submit.isDisplayed(); i++) {
			try {
				Thread.sleep(CommonConstants.FAST);
			} catch (InterruptedException e) {
				log.error("Timer interrupted");
			}
			log.info("waiting for button to appear: " + i);
		}
		submit.click();
	}

	
}
