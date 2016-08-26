package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.UserMSISDNEntry;
import com.ithr.ppe.test.cucumber.pages.UserSMSChallenge;

public class IdentityActivities {
	public static Logger log = Logger.getLogger(IdentityActivities.class);
	
	public static void loginToPPE (WebDriver driver, String opco, Partners partner, String msisdn , String pin, String url) throws InterruptedException  {
		
		String myurl = url + opco;		
		//append if dropbox
		if (partner == Partners.DROPBOX) {
			myurl = myurl + "?partner=dropbox";
		}
		
		driver.get(myurl);
		log.info("LoginToPPE -");
		
		// Entry page - AAA MSISDN and PIN challenge
		UserMSISDNEntry msisdnentry = new UserMSISDNEntry(driver);
		// TODO: see if we can get rid of this very specific wait
		msisdnentry.elementLoaded(By.id("nextButton"));
	
		msisdnentry.setShortMobile(msisdn);
		msisdnentry.clickNextButton();
		log.info("Have Set Mobile Number");
		
		// SMS Challenge - pin
		UserSMSChallenge smschallenge = new UserSMSChallenge(driver);
		smschallenge.bodyLoaded();
		smschallenge.setSMS(pin);
		log.info("Have Set Pin");
		
		smschallenge.clickRegisterButton();
	
	}
}
