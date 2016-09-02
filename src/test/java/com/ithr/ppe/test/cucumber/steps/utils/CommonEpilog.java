package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

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

	private void loadParser() {
		parser = JsonParser.getInstance();
	}
	private void loadChecker(){
		checker = opcoTextChecker.getInstance();
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
	
	private boolean refreshPPEPartner(WebDriver driver, String url, Partners partner) {
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
		verifyPostPurchaseOffers(entpage);
		
		log.info("Text to Check is: " + textfound);	
			
		boolean ok = false;
	
		String reftext = parser.getHeading();
		log.info("The Parser would have returned: " + reftext);
		//ok = textfound.equals(reftext);
	
		
		switch (partner) {
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
	
	public boolean refreshPPE(WebDriver driver, String baseopcourl, Partners partner) {
		log.info("TEST: Reopen on home page displays correct offers");
		loadParser();
		loadChecker();
		
		//TODO need to fix needing this - VERY FLAKY
		try {
			Thread.sleep(CommonConstants.SLOW);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("This Code needs removing Anyway " + e);
			
		}		
		switch (partner) {
			case DROPBOX :	return refreshPPEDropBox(driver, baseopcourl);
			default : return refreshPPEPartner(driver, baseopcourl, partner);
		}				
	}
	
		
	private boolean verifyPostPurchaseOffers(UserEntertainment entpage) {
		log.info("verifyPostPurchaseOffers NOT IMPLMENTED");
		return true;
	}


}
