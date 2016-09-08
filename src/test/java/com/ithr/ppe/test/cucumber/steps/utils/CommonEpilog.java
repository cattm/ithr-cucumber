package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.partners.DropBoxRefresh;
import com.ithr.ppe.test.cucumber.steps.interfaces.IEpilog;

public class CommonEpilog implements IEpilog 
{
	public static Logger log = Logger.getLogger(CommonEpilog.class);
	

	private static JsonParser parser = null;
	private static opcoTextChecker checker = null;

	
	public void initialiseChecks() {
		checker = opcoTextChecker.getInstance();
		parser = JsonParser.getInstance();
	}
	private boolean refreshPPEDropBox(WebDriver driver, String url) {		
		driver.get(url + "?partner=dropbox");		
		DropBoxRefresh refreshpage = new DropBoxRefresh(driver);
		try {
			refreshpage.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Timed out on loading the DropBoxRefresh page " + e);
		}		
			
		//TODO: there is more to check on this page!
		// heading; title; text; button;
		ErrorCollector.verifyEquals(refreshpage.getOkText(), parser.stripHTML(parser.getCancelOkButton()).toUpperCase(),"Cancel button text is incorrect");		  
	    boolean checked = checker.checkDropBoxSubscribedText(refreshpage.getHeading());
		ErrorCollector.verifyTrue(checked,"Not on the correct page");
		return checked;	
	}
	
	private boolean refreshPPEPartner(WebDriver driver, String url, Customer customer) {
		driver.get(url);
		UserEntertainment entpage = new UserEntertainment(driver);
		try {
			entpage.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Timed out on loading the UserEntertainment page " + e);
		}					
		// TODO: doesnt check position. Loops around to wait for image - its a bit slow sometimes ?
		String textfound = "";
		try {
			if (entpage.isManageSubscriptionTextPresent()) {		
				 textfound = entpage.getManageSubscriptionText();
			}
		} catch (InterruptedException e) {
			log.error("Interrupted while getting the subscriptiontext " + e);
		}
		
		// TODO: implement a check on the offers now available
		verifyPostPurchaseOffers(entpage, customer);
		
		log.info("Text to Check is: " + textfound);	
			
		boolean ok = false;
	
		String reftext = parser.getHeading();
		log.info("The Parser would have returned: " + reftext);
		//ok = textfound.equals(reftext);
	
		
		switch (customer.getPartner()) {
		case SPOTIFY :	ok = checker.checkSpotifySubscibedText(textfound);
					break;
		case SKY : ok = checker.checkSkySubscibedText(textfound);
					break;
		case NOWTV : ok = checker.checkNowTVSubscribedText(textfound);
					break;
		case NETFLIX : ok = checker.checkNetflixSubscribedText(textfound);
					break;
		default : break;	
		}
		
	    return ok;	   
		
	}
	
	// refresh PPE should:
	// refresh the browser to display the offers and subscribed offers (to cancel)
	// it should verify the subscribed offer is in the correct location 
	// it should select the subscribed offer and verify the text on the page
	
	public boolean refresh(WebDriver driver, String baseopcourl, Customer customer) {
		log.info("TEST: Reopen on home page displays correct offers");
	
		
		//TODO need to fix needing this - VERY FLAKY
		try {
			Thread.sleep(CommonConstants.SLOW);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("This Code needs removing Anyway " + e);
			
		}		
		switch (customer.getPartner()) {
			case DROPBOX :	return refreshPPEDropBox(driver, baseopcourl);
			default : return refreshPPEPartner(driver, baseopcourl, customer);
		}				
	}
	
		
	private boolean verifyPostPurchaseOffers(UserEntertainment entpage, Customer customer) {
		OfferMap om = OfferMap.getInstance();
		Boolean found = false;
		if (om.getEndListLoaded()) {
			log.info("verifyPostPurchaseOffers Required");
			try {
				String offers = om.getEndingOffersFor(customer.getSubscription(), customer.getUserGroup());
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
				log.error("No post purchase offers defined for this customer " + e);
				ErrorCollector.fail("No post Purchase offers defined for this customer");
			}		
			return found;
		} else {
			log.info("verifyPostPurchaseOffers NOT required");
			return true;
		}
	}


}
