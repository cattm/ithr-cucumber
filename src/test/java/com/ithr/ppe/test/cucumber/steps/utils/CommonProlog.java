package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.commons.DateStamp;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.interfaces.IProlog;

public class CommonProlog implements IProlog {
	public static Logger log = Logger.getLogger(CommonProlog.class);

	private static JsonParser parser = null;
	private static opcoTextChecker checker = null;

	private String partnerUserName = "none valid";
	//private Partners myPartner = null;

	public void createParser (String path, String filename) {
		log.info("going to search for :" + path + " and file " + filename + " for parser setup");
		String fileToCheck = CommandExecutor.execFindExactJsonFile(path, filename + " v");
		log.info("going to use json file: " + fileToCheck);
		parser = JsonParser.getInstance();
		parser.initialise(path + fileToCheck);
	}

	public void createChecker(String file, String opco) {
		log.info("going to setup Checker for : " + opco);
		try {
			checker = opcoTextChecker.getInstance();
			checker.initialise(file, opco);
		} catch (IOException e) {
			log.error("Issue creating opcoTextchecker " + e);
			ErrorCollector.fail("Failed to initialise checker correctly");
		}
	}
	
	// TODO: make sure if any of the next 3 methods fail the test FAILS!!! 
	public String getPartnerUserName(WebDriver driver, String adminurl, String opco, Partners partner) {	
		switch (partner) {
		case SPOTIFY :
			try {
				SpotifyFacade spot = new SpotifyFacade();
				partnerUserName = spot.getUser (driver, adminurl, opco);
			} catch (Exception e) {
				log.error("Cannot get Spotify user name");
				partnerUserName = "ERROR";
				ErrorCollector.fail("Did not get Partner username to use" );
			}
			break;
			
		case NETFLIX :
			DateStamp myds = new DateStamp();
			String rn = myds.getRanDateFormat();
			partnerUserName = "ithrtest" + rn + "@ithr.com";
			break;
		case SKY : // none required
		case DROPBOX :
		case NOWTV :
		default : 
			break;
		}
		
		return partnerUserName;
	}
	
	public String getNewMsisdn(WebDriver driver, String opco, String subscription, String usergroup, Partners partner) {
		return AdminFacade.msisdnFromAdmin(driver, opco, subscription, usergroup, partner);
	}
	
	public boolean LoginOk(WebDriver driver, String opco, Partners partner, String msisdn, String pincode, String url) {
		boolean ok = false;
		try {
			ok = IdentityFacade.loginToPPE (driver, opco, partner, msisdn , pincode, url);		
		} catch (InterruptedException e) {
			log.error("Login got interrupted " + e);
			ErrorCollector.fail("Login did not work for: " + msisdn);
			
		}
		return ok;
	}

	public boolean verifyOffersAvailableText(UserEntertainment entpage)  {
		log.info("TEST: Verify Available Offers page");
		String subtext = entpage.getSubscriptionText();
		log.info("Text to check is: " + subtext);			  			
		ErrorCollector.verifyTrue(checker.checkSubscriptionText(subtext), "Subscription text is not correct");
		return true;
	}
	
	
	/*
	 * TODO: the methods of this type are intended to verify the correct offers exist both pre and post the purchase activity
	 * I an not sure if this is an intelligent thing to attempt - we may be going a step too far
	 * I either have to model in a text file and read it in OR
	 * Use Backgroud BDD label to set up maps/table for tariff something like
	 *   Background: Previously Going to purchase Spotify
	 *   Given I am a "GB" customer purchasing spotify with:
	 * | package     | usergroup | shouldsee    |
	 * | PK_4GTariff | 4glarge   |  netflix, nowtv, sky |
	 *   AND when I have finished:
	 * | nowsee |
	 * | netflix,sky |	 
	 */
	public boolean verifyPrePurchaseOffers(UserEntertainment entpage) {	
		log.info("verifyPrePurchaseOffers NOT IMPLMENTED YET");
		return true;
	}
	
}
