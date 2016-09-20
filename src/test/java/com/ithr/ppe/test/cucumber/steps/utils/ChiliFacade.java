package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.cucumber.pages.partners.ChiliRegister;
import com.ithr.ppe.test.cucumber.steps.interfaces.IExternalPartner;

public class ChiliFacade implements IExternalPartner {
	public static Logger log = Logger.getLogger(ChiliFacade.class);
	
	public boolean register(WebDriver driver, String opco, String usernametouse) {
		// this will be a popup web page to fill in
		log.info("Going to continue");
		try {
			Thread.sleep(CommonConstants.SLOW);
		} catch (InterruptedException e) {
			log.error("sleep got interrupted " + e);
		}
	
		ChiliRegister form = new ChiliRegister(driver);
		form.bodyLoaded();
		form.clickContinue();
		
		log.info("Going to continue to put in username and password");
		form.setEmail(usernametouse);
		form.setEmailConfirm(usernametouse);
		form.setPassword("password");
		form.clickTerms();		
		form.clickCarryOn();
		log.info("Going to continue to names and date");
		form.setFirstName("David");
		form.setLastName("Jones");
		form.setDob();	
		form.clickCarryOn();
		log.info("Going to continue back to Vf");
		form.clickContinue();
		
		return true;
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
