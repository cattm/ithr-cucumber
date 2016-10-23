package com.ithr.ppe.bdd.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.commons.Partners;
import com.ithr.ppe.bdd.cucumber.pages.AdminHome;
import com.ithr.ppe.bdd.cucumber.pages.AdminVerify;

public class AdminFacade {
	public static Logger log = Logger.getLogger(AdminFacade.class);
	private final static String DBUID = "597844980";
	private static String checkUrl = "";
	
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
		checkUrl = adminhome.getSubscriptionCheckUrl();	
	
		// call the url to create user
		driver.get(checkUrl);
			
		// check/test goes here - if its not created properly then we either corrupt the string or throw an exception
		boolean userok = userCreatedOk(driver, partner);
		if (userok) {
			return msisdn;
		}
		else return "ERROR";
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
		//driver.post(checkurl);
		log.error("METHOD NOT PROPERLY IMPLEMENTED YET");
		
		AdminVerify verify = new AdminVerify(driver);
		if (verify.isIndividualCreated()) {
			log.info(" Admin Created This: " + msisdn);
		}
		return msisdn;
	}
	
	public static String getCheckUrl() {
		return checkUrl;
	}
	
	public static boolean addUserGroup (WebDriver driver, String checkurl, String oldgroup, String newgroup) {
	// checkurl will be of the form 
	// https://dit.offers.vodafone.com/purchaseTariff?msisdn=499713301678&userGroup=ug_ppe_red&opco=de&packageId=PK_RedTariff&chargingMethod=non_recurring
	// replacement is of the form "ug_ppe_redplus"
	// or maybe......https://dit.offers.vodafone.com/purchaseTariff?msisdn=399189938199&opco=it&userGroup=some_new_grpup
	// look for "msisdnUpdated" : true
		String newurl = checkurl.replace(oldgroup, newgroup);
		//
		// now strip end of url - &packageId=PK_RedTariff&chargingMethod=non_recurring onwards
		int index = newurl.indexOf("&packageId");
		String revised = newurl.substring(0, index);
		log.info("Revised url is " + revised);
			
		driver.get(revised);
		AdminVerify verify = new AdminVerify(driver);
		if (verify.isInGroup(newgroup)) {
			return true;
		}
		return false;
	}
	
	public static boolean deletePartnerSubscriptionsInER(WebDriver driver, String urlbase, String opco, String partner, String msisdn) {
		//https://dit.offers.vodafone.com/new/cancelSubscription?msisdn=449451953699&opco=gb&partner=spotify
		String urltouse = urlbase +  "new/cancelSubscription?msisdn="+ msisdn + "&opco=" + opco + "&partner=" + partner.toLowerCase();
		driver.get(urltouse);
		log.info("url to remove is" + urltouse);
		
		AdminVerify verify = new AdminVerify(driver);
		if (verify.isRemoved(partner)) {
			return true;
		}
		return false;
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
