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

import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.AdminHome;
import com.ithr.ppe.test.cucumber.pages.AdminVerify;

public class AdminFacade {
	public static Logger log = Logger.getLogger(AdminFacade.class);
	private final static String DBUID = "597844980";
	
	private static String msisdnFromAdminWithCreate(WebDriver driver, String opco, String subscription, String usergroup, Partners partner) {
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
		
		// perform any additional setup based on partner
		switch (partner) {
			case DROPBOX : adminSetupDropbox(adminhome);
					 break;
			default: break;
		}
		
		String msisdn = adminhome.getShortMSISDN();
		log.info("MSISDN is : " + msisdn);
			
		// TODO: Put a Proper test here and if there is a problem then we need to advise - so test can exit or otherwise
		String checkurl = adminhome.getSubscriptionCheckUrl();	
	
		// call the url to create user
		driver.get(checkurl);
			
		// check/test goes here
		userCreatedOk(driver, partner);
		
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
	
	private static void adminSetupDropbox(AdminHome adminhome)  {
		adminhome.setWithDropbox();
		adminhome.clearWithRandonID();
		adminhome.setDropboxUID(DBUID);	
	}
	
	private static boolean userCreatedOk(WebDriver driver, Partners partner) {
		AdminVerify verify = new AdminVerify(driver);
		
		if (partner == Partners.DROPBOX) {
			if (verify.isDropBoxIndividualCreated(DBUID)) {
				return true;
			}
		} else  {
			if (verify.isIndividualCreated()) {
				return true;
			}
		}
		return false;
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
		log.error("METHOD NOT PROPERLY IMPLEMENTED YET");
		
		AdminVerify verify = new AdminVerify(driver);
		if (verify.isIndividualCreated()) {
			log.info(" Admin Created This: " + msisdn);
		}
		return msisdn;
	}
	
	public static String msisdnFromAdmin(WebDriver driver, String opco, String subscription, String usergroup, Partners partner) {
		// check for special not valid
		// TODO: take out the case of the string
		log.info("sub is " + subscription + " user is " + usergroup);
		if  (subscription.contains("Not Valid") && usergroup.contains("Not Valid")) {
			return msisdnFromAdminNoCreate(driver, opco);
		} else {
			return msisdnFromAdminWithCreate(driver, opco, subscription, usergroup, partner);
		}
	}
}
