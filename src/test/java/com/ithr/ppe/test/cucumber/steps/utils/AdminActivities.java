package com.ithr.ppe.test.cucumber.steps.utils;

/**
 * Implements the manipulation of the admin tool to get a new usable MSISDN
 * The model will sleect and create the required profile and check it or
 * just return an MSISDN from the required OPCO
 * Valid Text Combinations include all drop down list items for tariff and usergroup
 * Take notice of No parent subcription and No usergroup text offerings
 * Not Valid, Not Valid combination is used to simply return a raw MSISDN
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.cucumber.pages.AdminHome;
import com.ithr.ppe.test.cucumber.pages.AdminVerify;

public class AdminActivities {
	public static Logger log = Logger.getLogger(AdminActivities.class);
	
	private static String msisdnFromAdminWithCreate(WebDriver driver, String opco, String subscription, String usergroup) {
		// open admin page and setup subscription in ER
		AdminHome adminhome = new AdminHome(driver);
		adminhome.setOpco(opco);
		
		if (!subscription.contains("Not Valid")) {
			log.info("subscription is Valid");
			adminhome.setSubscription(subscription);
		}
				
		if (!usergroup.contains("No usergroup")) {			
			log.info("User Group is Valid");
			adminhome.setUserGroup(usergroup);
		} else {
			log.info("Setting No User Group flag");
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
	
	private static String msisdnFromAdminNoCreate(WebDriver driver, String opco) {
		log.info("Just getting the msisdn");
		AdminHome adminhome = new AdminHome(driver);
		adminhome.setOpco(opco);
			
		String msisdn = adminhome.getShortMSISDN();
		log.info(" No Create MSISDN is : " + msisdn);		
		return msisdn;
	}
	
	private static String msisdnFromAdminWithPost(WebDriver driver, String opco, String subscription, String usergroup) {
		AdminHome adminhome = new AdminHome(driver);
		adminhome.setOpco(opco);
		
		if (!subscription.contains("Not Valid")) {
			log.info("subscription is Valid");
			adminhome.setSubscription(subscription);
		}
				
		if (!usergroup.contains("No usergroup")) {			
			log.info("User Group is Valid");
			adminhome.setUserGroup(usergroup);
		} else {
			adminhome.setNoUserGroup();
		}
			
		String msisdn = adminhome.getShortMSISDN();
		log.info("MSISDN is : " + msisdn);
		
		 
		// TODO: Put a Proper test here and if there is a problem then we need to advise - so test can exit or otherwise
		String checkurl = adminhome.getSubscriptionCheckUrl();	
		
		//TODO - need to post this url not GET
		//driver.get(checkurl);
		log.error("METHOD NOT PROPERLY implemented yet");
		
		AdminVerify verify = new AdminVerify(driver);
		if (verify.isIndividualCreated()) {
			log.info(" Admin Created This: " + msisdn);
		}
		return msisdn;
	}
	
	public static String msisdnFromAdmin(WebDriver driver, String opco, String subscription, String usergroup) {
		// check for special not valid
		// TODO: take out the case of the string
		log.info("sub is " + subscription + " user is " + usergroup);
		if  (subscription.contains("Not Valid") && usergroup.contains("Not Valid")) {
			return msisdnFromAdminNoCreate(driver, opco);
		} else {
			return msisdnFromAdminWithCreate(driver, opco, subscription, usergroup);
		}
	}
}
