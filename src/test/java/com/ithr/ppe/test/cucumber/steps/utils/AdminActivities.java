package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.commons.lang3.StringUtils;
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
		log.info("MSISDN is : " + msisdn);
		
		 
		
		// TODO: Put a Proper test here and if there is a proble then we need to advise - so test can exit or otherwise
		String checkurl = adminhome.getSubscriptionCheckUrl();	
		
		// TODO: 16/08/2016 - Remove this temporary fix when the "0" has been removed 
		// this makes it work with UK - since Ion has foobarred the admin tool for the moment
		// msisdn=xx0yyyy etc
		// need to substitute - just do for uk for now
		// also need to fix msisdn returned
		//String tmp = StringUtils.replace(checkurl, "msisdn=440", "msisdn=449");
		driver.get(checkurl);
		//msisdn = msisdn.replaceFirst("^0*", "9");
		// End of temporary fix
		
		// check/test goes here
		
		AdminVerify verify = new AdminVerify(driver);
		if (verify.isIndividualCreated()) {
			log.info(" Admin Created This: " + msisdn);
		}
		return msisdn;
	}
}
