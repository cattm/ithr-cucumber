package com.ithr.ppe.bdd.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.commons.CommonConstants;
import com.ithr.ppe.bdd.cucumber.pages.partners.HBORegistrationForm;
import com.ithr.ppe.bdd.cucumber.steps.interfaces.IExternalPartner;

public class HBOFacade implements IExternalPartner {
	public static Logger log = Logger.getLogger(HBOFacade.class);
	
	public boolean register(WebDriver driver, String opco, String usernametouse) {
		// this will be a popup web page to fill in
		log.info("Going to continue");
		try {
			Thread.sleep(CommonConstants.SLOW);
		} catch (InterruptedException e) {
			log.error("sleep got interrupted " + e);
		}
		
		HBORegistrationForm form = new HBORegistrationForm(driver);
		form.setFirstName("David");
		form.setLastName("Jones");
		form.setEmail(usernametouse);
		form.setPassword("password");
		form.setConfirmPassword("password");
		form.clickRegister();
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
