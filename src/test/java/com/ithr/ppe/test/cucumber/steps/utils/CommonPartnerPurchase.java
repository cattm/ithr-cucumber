package com.ithr.ppe.test.cucumber.steps.utils;

/**
 * Implements the basic model of a partner offer purchase
 * It will be common for most if not all purchases 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.commons.DateStamp;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.PageBase;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.partners.DropBoxRefresh;

public class CommonPartnerPurchase implements IPartnerPurchase {
	public static Logger log = Logger.getLogger(CommonPartnerPurchase.class);

	private static JsonParser parser = null;
	private static opcoTextChecker checker = null;
	private static String partnerUserName = "none valid";
	private static Partners myPartner = null;
	
/*
 * We analyse the offers
 * Accept the offers
 * check the result page
 * 
 */
	
	public void locateJsonParseFile (String path, String filename) {
		log.info("going to search for :" + path + " and file " + filename);
		String fileToCheck = CommandExecutor.execFindExactJsonFile(path, filename + " v");
		log.info("going to use json file: " + fileToCheck);
		parser = JsonParser.getInstance();
		parser.initialise(path + fileToCheck);
	}
	
	public void defineCheckerToUse(String file, String opco) {
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
	
	
	/*
	 * TODO: the two methods are intended to verify the correct offers exist both pre and post the purchase activity
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
	public boolean validatePrePurchaseOffers(UserEntertainment entpage) {	
		log.info("validatePrePurchaseOffers NOT IMPLMENTED");
		return true;
	}
	
	public boolean validatePostPurchaseOffers(UserEntertainment entpage) {
		log.info("validatePostPurchaseOffers NOT IMPLMENTED");
		return true;
	}
	
	public boolean selectPartnerOffer(Partners partner, UserEntertainment entpage)  {
		// this is a very primative implementation
		// we may have to cope with a situation where there are a number of offers from same partners
		// we will need to select the correct one
		// TODO: build a more comprehensive selection solution
		
		myPartner = partner;
		boolean found = false;
		String imagestring = "";
		switch (myPartner) {
		case DROPBOX    :
			//dropbox is an example of where we do not need to find the correct offer to select
			found = true;
		case NETFLIX    : 
			imagestring = "netflix";
			break;
		case NOWTV		:
			imagestring = "nowtv";
			break;
		case SPOTIFY 	: 
			imagestring = "spotify";
			break;		
		case SKY 		: 
			imagestring = "sky";
			break;		
		
		default : 
			break;
		}	
		if (!found) { 
			found = entpage.checkOfferImagePresent(imagestring);
			if (found)
				try {
				entpage.clickOfferImage(imagestring);
				} catch (InterruptedException e) {
				log.error("got interrupted while clicking on " + imagestring + " " + e);
			}
		}
		return found;
	}
		
	
	private boolean WaitforPurchaseToComplete (BasicPartnerOffer offer) {
		boolean done = false;
		// max wait here is 20 * SLOW - which is a very long time on a slow environment
		for (int i = 0; i < 20 && !done; i++) {
			// do the sleep first - to give it a chance
			try {
				Thread.sleep(CommonConstants.SLOW);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("sleep interrupted" + e);
			}
			
			String notice = offer.getSuccessText();		
			log.debug("current success text is : " + notice);
			
			if (checker.checkCompleteMsg(notice)) {
					log.info("purchase process complete");
					done = true;
			} else {
				log.info("Waiting.....");
			}
		}
		return done;	
	}	
	
	public boolean acceptVFPOffer(IVFPartner vfpartner, WebDriver driver) {
		boolean done = false;
		if (vfpartner.PurchaseOffer(driver)) {
			try {
				Thread.sleep(CommonConstants.SLOW);
			} catch (InterruptedException e) {
				log.error("Remove this timer if you can");
			}
			done = vfpartner.MoveToDownload(driver);
		} else done = false;
		return done;		
	}
	
	public boolean acceptTheOffer(WebDriver driver, String opco, Partners partner)  {
		if (myPartner == null) {
			myPartner = partner;
		} else {
			if (myPartner != partner) log.error("attempting to overwrite partner with new value");
		}
					

	    BasicPartnerOffer offer = new BasicPartnerOffer(driver);	   
	    ErrorCollector.verifyEquals(offer.getAcceptOfferText(), parser.getOffersOkButton().toUpperCase(), "Button text is incorrect");
  
		offer.clickAcceptOffer();
		
		// At this point we need to know if the action involves a partner interaction or not
		boolean registered = true;
		boolean checkreturnpage = true;
		switch (myPartner) {
		case DROPBOX :
			// we finish at a download page
			IVFPartner dropbox = new VFDropboxFacade();
			registered = acceptVFPOffer(dropbox, driver);
			checkreturnpage = false;
			break;
		case SPOTIFY :	
			try {
				IExternalPartner spot = new SpotifyFacade();
				registered = spot.register(driver, opco, partnerUserName);
			} catch (Exception e) {
				log.error("Register for Spotify failed " + e);
			}
			break;
			
		case NETFLIX :
			IExternalPartner pa = new NetflixFacade();
			registered = pa.register(driver, opco, partnerUserName);
			// at this point we need to return because there is nothing else to check for netflix
			checkreturnpage = false;
			break;
						
		default : break;
		}
		
		if (checkreturnpage && registered) {
			log.info("going into delay loop: registered and checking return page ");
			//TODO: need to add a NICE confirmation text check that the purchase has completed and then it is safe to check all the text and reopen ppe
			WaitforPurchaseToComplete (offer);
			return verifyNextStepsText(offer);
		}	else return registered;
				
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
	
	private boolean refreshPPEPartner(WebDriver driver, String url) {
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
		validatePostPurchaseOffers(entpage);
		
		log.info("Text to Check is: " + textfound);	
			
		boolean ok = false;
	
		String reftext = parser.getHeading();
		log.info("The Parser would have returned: " + reftext);
		//ok = textfound.equals(reftext);
	
		
		switch (myPartner) {
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
	
	public boolean refreshPPE(WebDriver driver, String baseopcourl) {
		log.info("TEST: Reopen on home page displays correct offers");
		
		//TODO need to fix needing this - VERY FLAKY
		try {
			Thread.sleep(CommonConstants.SLOW);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("This Code needs removing Anyway " + e);
			
		}		
		switch (myPartner) {
			case DROPBOX :	return refreshPPEDropBox(driver, baseopcourl);
			default : return refreshPPEPartner(driver, baseopcourl);
		}				
	}
	
	public boolean verifyOfferText(BasicPartnerOffer offer){
		log.info("TEST: Verify the Offer");
		String textstripped = parser.stripHTML(parser.getOffersTitle());	
		ErrorCollector.verifyEquals(offer.getUserOffer(),textstripped, "The offer title is incorrect");

	
		// check the text bullets from text
		String crstripped = StringUtils.replace(offer.getOfferDetail(), "\n", " ");
		String ntextstripped = parser.stripHTML(parser.getOffersText());
		ErrorCollector.verifyEquals(crstripped, ntextstripped,"The offer detail is incorrect");

	  
		// check T&C label from label
		String offertncstripped = StringUtils.replace(offer.getOfferTnC(), "\n", " ");	
		String tncstripped = parser.stripHTML(parser.getOffersTnCText());
		ErrorCollector.verifyEquals(offertncstripped,tncstripped, "The T & C text is incorrect");
		return true;
	}
	
	public boolean verifyAvailableOffersText(UserEntertainment entpage)  {
		log.info("TEST: Verify Available Offers page");
		String subtext = entpage.getSubscriptionText();
		log.info("Text to check is: " + subtext);			  			
		ErrorCollector.verifyTrue(checker.checkSubscriptionText(subtext), "Subscription text is not correct");
		return true;
	}
	
	public boolean verifyNextStepsText(BasicPartnerOffer offer) {
		// check the page actually displayed
		log.info("TEST: Verify confirm page after accepting offer");
		String confirmation = offer.getSuccessText();
		log.info("Text to Check is : " + confirmation);
		ErrorCollector.verifyTrue(checker.checkConfirmText(confirmation), "Confirmation text is incorrect");
				  
		log.info("TEST: Verify whats going to happen next");
		String mypagehappens = StringUtils.replace(offer.getHappensNextText(), "\n", " ");			  
		String mycheckhappens = parser.stripHTML(parser.getSubscribeSuccessText());	
		ErrorCollector.verifyEquals(mypagehappens, mycheckhappens,"What happens next text is incorrect");
		
		return true;
	}

}
