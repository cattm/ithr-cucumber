package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.commons.DateStamp;
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
		if (fileToCheck.contains("NOT FOUND")) {
			log.error("Issue creating Parser " + fileToCheck);
			ErrorCollector.fail("Failed to initialise Parser correctly " + fileToCheck + " file to parse");			
		} else {
			parser = JsonParser.getInstance();
			parser.initialise(path + fileToCheck);
		}
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
	public String getPartnerUserName(WebDriver driver, String adminurl, Customer customer) {	
		switch (customer.getPartner()) {
		case SPOTIFY :
			try {
				SpotifyFacade spot = new SpotifyFacade();
				partnerUserName = spot.getUser (driver, adminurl, customer.getOpco());
			} catch (Exception e) {
				log.error("Cannot get Spotify user name");
				partnerUserName = "ERROR";
				ErrorCollector.fail("Did not get Partner username to use" );
			}
			break;
			
		case NETFLIX :
		case HBO:
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
	
	public String getNewMsisdn(WebDriver driver, Customer customer) {
		return AdminFacade.msisdnFromAdmin(driver, customer.getOpco(), customer.getSubscription(), customer.getUserGroup(), customer.getPartner());
	}
	
	public boolean LoginOk(WebDriver driver, Customer customer, String pincode, String url) {
		boolean ok = false;
		try {
			ok = IdentityFacade.loginToPPE (driver, customer.getOpco(), customer.getPartner(), customer.getMsisdn() , pincode, url);		
		} catch (InterruptedException e) {
			log.error("Login got interrupted " + e);
			ErrorCollector.fail("Login did not work for: " + customer.getMsisdn());		
		}
		return ok;
	}
	
	public boolean verifyOffers(UserEntertainment entpage, Customer customer) {
		return (verifyOffersAvailableText(entpage) && verifyPrePurchaseOffers( entpage,  customer));		
	}

	private boolean verifyOffersAvailableText(UserEntertainment entpage)  {
		log.info("TEST: Verify Available Offers page");
		String subtext = entpage.getSubscriptionText();
		log.info("Text to check is: " + subtext);			  			
		ErrorCollector.verifyTrue(checker.checkSubscriptionText(subtext), "Subscription text is not correct");
		return true;
	}
	
	private boolean verifyPrePurchaseOffers(UserEntertainment entpage, Customer customer) {
		OfferMap om = OfferMap.getInstance();
		Boolean found = false;
		if (om.getStartListLoaded()) {
			log.info("verifyPrePurchaseOffers Required");
			try {
				String offers = om.getStartingOffersFor(customer.getSubscription(), customer.getUserGroup());
				String [] splitoffers = om.SplitOffers(offers);
				// now we check for each offer that there is something on the page
				for (int i = 0; i < splitoffers.length; i++) {
					String tmp = splitoffers[i].toLowerCase();
					String tofind = "div[id='"+ customer.getOpco() + "-" + tmp.replace(" ", "-") + "']";
					log.info("now checking for : " + tmp);
					found = entpage.isMyOfferPresent(tofind);
					log.info("And search returned " + found.toString());
					if (!found) ErrorCollector.fail("required offer " + tmp + "not found on page");
				}				
		
			}  catch (InterruptedException e)  {
				log.error("Problem with Element locator " + e);
				ErrorCollector.fail("Problem with Elemement loactor");
			} catch (Exception e) {
				log.error("No pre purchase offers defined for this customer " + e);
				ErrorCollector.fail("No Pre Purchase offers defined for this customer");
			}		
			return found;
		} else {
			log.info("verifyPrePurchaseOffers NOT required");
			return true;
		}
		
	}
	
}
