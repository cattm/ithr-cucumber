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

	public void createJsonParserFromFile (String path, String filename) {
		log.info("going to search for :" + path + " and file " + filename);
		String fileToCheck = CommandExecutor.execFindExactJsonFile(path, filename + " v");
		log.info("going to use json file: " + fileToCheck);
		parser = JsonParser.getInstance();
		parser.initialise(path + fileToCheck);
	}

	public void createCheckerToUse(String file, String opco) {
		try {
			checker = opcoTextChecker.getInstance();
			checker.initialise(file, opco);
		} catch (IOException e) {
			log.error("Issue creating opcoTextchecker " + e);
		}
	}
	
	/* This method will attempt get a partner username if required
	 * 
	 */
	public String getPartnerUserName(WebDriver driver, String adminurl, String opco, Partners partner) {	
		switch (partner) {
		case SPOTIFY :
			try {
				SpotifyFacade spot = new SpotifyFacade();
				partnerUserName = spot.getUser (driver, adminurl, opco);
			} catch (Exception e) {
				log.error("Cannot get Spotify user name");
				partnerUserName = "ERROR";
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
		log.info("verifyPrePurchaseOffers NOT IMPLMENTED");
		return true;
	}
	
}
