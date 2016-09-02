package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixLoginOrRegister;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixOffer;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixSuccess;
import com.ithr.ppe.test.cucumber.steps.interfaces.IExternalPartner;


public class NetflixFacade implements IExternalPartner{
	
	public static Logger log = Logger.getLogger(NetflixFacade.class);

	public boolean register(WebDriver driver, String opco, String usernametouse) {
		// have had to slow this baby down - site is very flaky
		
		log.info("Going to hit continue");
		try {
			Thread.sleep(CommonConstants.FAST);
		} catch (InterruptedException e) {
			log.error("sleep got interrupted " + e);
		}
		
		NetflixOffer offer = new NetflixOffer(driver);
		try {
			offer.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading offer page " + e);
		}
		
		
		offer.clickSubmit();
		try {
			Thread.sleep(CommonConstants.MEDIUM);
		} catch (InterruptedException e) {
			log.error("Interrupted sleep - remove this delay please " + e);
		}
		// TODO we have a timing issue here it works some times......
		// check the page loaded before we hit stuff
		log.info("Going to do username/password");
		NetflixLoginOrRegister logorreg = new NetflixLoginOrRegister(driver);
		try {
			logorreg.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading login or register page " + e);
		}
		logorreg.setEmail(usernametouse);
		try {
			Thread.sleep(CommonConstants.FAST);
		} catch (InterruptedException e) {
			log.error("sleep got interrupted " + e);
		}
		logorreg.setPassword("passwordnf");
		try {
			Thread.sleep(CommonConstants.FAST);
		} catch (InterruptedException e) {
			log.error("sleep got interrupted " + e);
		}
		logorreg.clickContinue();
		
		log.info("Going to check success page for netflix");
		try {
			Thread.sleep(CommonConstants.FAST);
		} catch (InterruptedException e) {
			log.error("Interrupted sleep - remove this delay please " + e);
		}
		
		NetflixSuccess netsuccess = new NetflixSuccess(driver);
		try {
			netsuccess.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading success page " + e);
		}
;
		// TODO: this will be opco dependant - uk solution in place
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
