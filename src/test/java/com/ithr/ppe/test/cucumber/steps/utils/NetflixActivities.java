package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.cucumber.pages.partners.NetflixLoginOrRegister;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixOffer;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixSuccess;


public class NetflixActivities implements PartnerInterface{
	
	public static Logger log = Logger.getLogger(NetflixActivities.class);

	public boolean register(WebDriver driver, String opco, String usernametouse) {
		
		log.info("Going to hit continue");
		NetflixOffer offer = new NetflixOffer(driver);
		try {
			offer.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading offer page" + e);
		}
		offer.clickSubmit();
		
		// TODO we have a timing issue here it works some times......
		// checkthe page loaded before we hit stuff
		log.info("Going to do username/password");
		NetflixLoginOrRegister logorreg = new NetflixLoginOrRegister(driver);
		try {
			logorreg.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading login or register page" + e);
		}
		logorreg.setEmail(usernametouse);
		logorreg.setPassword("password");
		logorreg.clickContinue();
		
		log.info("Going to check success page for netflix");
		NetflixSuccess netsuccess = new NetflixSuccess(driver);
		try {
			netsuccess.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading success page" + e);
		}
		
		// TODO: this will be opco dependant
		if (netsuccess.getNetflixSuccess().contentEquals("Your Netflix membership, which begins with a free trial, has begun.")) {
			return true;
		}
		return false;		
	}
	
	public boolean login (WebDriver driver, String opco, String usernametouse) {
		return false;
	}
	
	public String terminateUser(WebDriver driver, String baseurl, String opco, String username) {
		return "NOT IMPLEMENTED YET";

	}
	
	public String getUserStatus(WebDriver driver, String baseurl, String opco, String username) {
		return "NOT IMPLEMENTED YET";

	}
	
	public String getUser (WebDriver driver, String baseurl, String opco) {
		return "NOT IMPLEMENTED YET";

	}
}