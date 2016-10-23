package com.ithr.ppe.bdd.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.commons.CommonConstants;
import com.ithr.ppe.bdd.cucumber.pages.partners.ChiliCompleteReg;
import com.ithr.ppe.bdd.cucumber.pages.partners.ChiliDetails1;
import com.ithr.ppe.bdd.cucumber.pages.partners.ChiliDetails2;
import com.ithr.ppe.bdd.cucumber.pages.partners.ChiliRegister;
import com.ithr.ppe.bdd.cucumber.steps.interfaces.IExternalPartner;

public class ChiliFacade implements IExternalPartner {
	public static Logger log = Logger.getLogger(ChiliFacade.class);
	
	public boolean register(WebDriver driver, String opco, String usernametouse) {
		// this will be a popup web page to fill in
		log.info("Going to continue Register for Chili TV");
		// this first page can take an age to load......
		ChiliRegister reg = new ChiliRegister(driver);
		// is there a probelm with cookie policy?
		reg.bodyLoaded();
		log.info("1");
		reg.acceptCookies();
		log.info("2");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reg.clickSomeButton();
		reg.clickContinue();
		log.info("3");

		
		log.info("Going to continue to put in username and password");
		ChiliDetails1 form1 = new ChiliDetails1(driver);
		//form1.doWait(5000);
		//form1.bodyLoaded();
		form1.setEmail(usernametouse);
		log.info("3");
		form1.setEmailConfirm(usernametouse);
		form1.setPassword("password");
		form1.clickTerms();		
		form1.clickCarryOn();
		
		log.info("Going to continue to names and date");
		ChiliDetails2 form2 = new ChiliDetails2(driver);
		//form2.doWait(5000);
		//form2.bodyLoaded();
		form2.setFirstName("David");
		form2.setLastName("Jones");
		form2.setDob();	
		form2.clickCarryOn();
		
		log.info("Going to continue back to Vf");
		ChiliCompleteReg creg = new ChiliCompleteReg(driver);
		creg.doWait(5000);
		creg.bodyLoaded();
		creg.clickSomeButton();
		creg.clickContinue();	
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
