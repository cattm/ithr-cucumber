package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.cucumber.pages.AdminHome;
import com.ithr.ppe.test.cucumber.pages.AdminVerify;

public class AdminActivities {
	public static Logger log = Logger.getLogger(AdminActivities.class);
	
	public static String msisdnFromAdmin(WebDriver driver, String opco, String subscription, String usergroup) {
		// open admin page and setup subscription in ER
		AdminHome adminhome = new AdminHome(driver);
		adminhome.setOpco(opco);
		
		if (!subscription.contains("Not Valid")) {
			log.info("subscription is Valid");
			adminhome.setSubscription(subscription);
		}
				
		if (!usergroup.contains("Not Valid")) {			
			log.info("User Group is Valid");
			adminhome.setUserGroup(usergroup);
		} else {
			adminhome.setNoUserGroup();
		}
			
		String msisdn = adminhome.getShortMSISDN();
		log.info("MSISN is : " + msisdn);
		
		// TODO: Put a Proper test here
		String checkurl = adminhome.getSubscriptionCheckUrl();		
		driver.get(checkurl);
		
		// check/test goes here
		AdminVerify verify = new AdminVerify(driver);
		if (verify.isIndividualCreated()) {
			log.info(" ER Admin Created: " + msisdn);
		}
		return msisdn;
	}
}
