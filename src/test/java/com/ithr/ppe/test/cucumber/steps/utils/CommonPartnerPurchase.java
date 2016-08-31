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
	
// Maybe a case for using a Builder pattern here?	
	

	
	public void locateJsonParseFile (String path, String filename) {
		log.info("going to search for :" + path + " and file " + filename);
		String fileToCheck = CommandExecutor.execFindExactJsonFile(path, filename + " v");
		log.info("going to use json file: " + fileToCheck);
		parser = new JsonParser(path + fileToCheck);
	}
	
	public void defineCheckerToUse(String file, String opco) {
		try {
			checker = new opcoTextChecker(file, opco);
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
	
	public boolean validatePrePurchaseOffers(UserEntertainment entpage) {
		// this method will get all the available offers and check they are as expected 
		// I am not sure how I will implement this yet.
		// depends on the country, price plan; the tariff; the data pack; and what has already been purchased
		// TODO: Implement this - for the moment pass it
		log.info("NOT IMPLMENTED");
		return true;
	}
	
	public boolean validatePostPurchaseOffers(UserEntertainment entpage) {
		// TODO: Implement this - for the moment pass it
		log.info("NOT IMPLMENTED");
		return true;
	}
	
	public boolean selectPartnerOffer(Partners partner, UserEntertainment entpage)  {
		// this is a very primative implementation
		// we may have to cope with a situation where there are a number of offers from same partner
		// we will need to select the correct one
		// TODO: build a more comprehensive selection solution
		
		myPartner = partner;
		boolean found = false;
		String imagestring = "";
		switch (myPartner) {
		case DROPBOX    :
			//dropbox is a special case we dont have to do anything
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
		// max wait here is 20 * SLOW - which is a long time on a slow environment
		for (int i = 0; i < 20 && !done; i++) {
			// do the sleep first - to give it a chance
			try {
				Thread.sleep(CommonConstants.SLOW);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("sleep interrupted");
			}
			
			String notice = offer.getSuccessText();		
			log.info("current success text is : " + notice);
			
			if (checker.checkCompleteMsg(notice)) {
					log.info("purchase process complete");
					done = true;
			} else {
				log.info("Waiting for purchase notification.....");
			}
		}
		return done;	
	}	
	
	public boolean acceptTheOffer(WebDriver driver, String opco, Partners partner)  {
		if (myPartner == null) {
			myPartner = partner;
		} else {
			if (myPartner != partner) log.error("attempting to overwrite partner with new value");
		}
		
		String buttontext = parser.getOffersOkButton();			
	    String ucbuttontext = buttontext.toUpperCase();
	    BasicPartnerOffer offer = new BasicPartnerOffer(driver);
	    String offerbuttontext = offer.getAcceptOfferText();
	    ErrorCollector.verifyEquals(offerbuttontext, ucbuttontext, "Button text is incorrect");
  
		offer.clickAcceptOffer();
		
		// At this point we need to know if the action involves a partner interaction or not
		boolean registered = true;
		boolean checkreturnpage = true;
		switch (myPartner) {
		case DROPBOX :
			// we finish at a download page I think
			IVFPartner dropbox = new VFDropboxFacade();
			registered = dropbox.PurchaseOffer(driver, opco);
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
			WaitforPurchaseToComplete ( offer);
				
			// check the page
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
		//
		String onpage = refreshpage.getHeading();
		return checker.checkDropBoxSubscribedText(onpage);
		
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
		String displayoffer = offer.getUserOffer();
		String title = parser.getOffersTitle();
		String textstripped = parser.stripHTML(title);	
		ErrorCollector.verifyEquals(displayoffer,textstripped, "The offer title is incorrect");

	
		// check the text bullets from text
		String offertext = offer.getOfferDetail();
		String crstripped = StringUtils.replace(offertext, "\n", " ");
		String text = parser.getOffersText();
		String ntextstripped = parser.stripHTML(text);
		ErrorCollector.verifyEquals(crstripped, ntextstripped,"The offer detail is incorrect");

	  
		// check T&C label from label
		String offertnc = offer.getOfferTnC();
		String offertncstripped = StringUtils.replace(offertnc, "\n", " ");
		String tnc = parser.getOffersTnCText();	
		String tncstripped = parser.stripHTML(tnc);
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
		String happens = offer.getHappensNextText();
		String mypagehappens = StringUtils.replace(happens, "\n", " ");			  
		String checkhappens = parser.getSubscribeSuccessText();
		String mycheckhappens = parser.stripHTML(checkhappens);	
		ErrorCollector.verifyEquals(mypagehappens, mycheckhappens,"What happens next text is incorrect");
		
		return true;
	}

}
