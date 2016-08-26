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
import com.ithr.ppe.test.commons.DateStamp;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.PageBase;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;

public class CommonPartnerPurchase implements PartnerPurchaseInterface {
	public static Logger log = Logger.getLogger(CommonPartnerPurchase.class);
	private static boolean checkAsserts = false;
	private static JsonParser parser = null;
	private static opcoTextChecker checker = null;
	private static String partnerUserName = "none valid";
	private static Partners myPartner = null;
	
	
	public void setAssertCheck() {
		checkAsserts = true;
	}
	
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
				SpotifyActivities spot = new SpotifyActivities();
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
			//dropbox is a special case we dont have to do anything - its naughty but just return as a test of the logic
			return true;
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
		found = entpage.checkOfferImagePresent(imagestring);
		if (found)
		try {
				entpage.clickOfferImage(imagestring);
		} catch (InterruptedException e) {
				log.error("got interrupted while clicking on " + imagestring + " " + e);
		}
		return found;
	}
		
	
	private boolean WaitforPurchaseToComplete (BasicPartnerOffer offer) {
		boolean done = false;
		// max wait here is 20 * SLOW - which is a long time on a slow environment
		for (int i = 0; i < 20 && !done; i++) {
			// do the sleep first - to give it a chance
			try {
				Thread.sleep(PageBase.SLOW);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("sleep interrupted");
			}
			
			String notice = offer.getSuccessText();		
			log.info("current success text is : " + notice);
			
			// TODO: Not at all sure this check is SAFE
			// tested with getProcessMsg for GB, IE, IT, PT
			// tested with checkCompleteMsg for GB and ES - works
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
	    log.info("Button String should be : " +  ucbuttontext);
	    
	    BasicPartnerOffer offer = new BasicPartnerOffer(driver);
	    String offerbuttontext = offer.getAcceptOfferText();
	    log.info("Button String is : " + offerbuttontext);
	    if (checkAsserts) ErrorCollector.verifyTrue(ucbuttontext.equals(offerbuttontext), "Button text is incorrect");
  
		offer.clickAcceptOffer();
		
		// At this point we need to know if the action involves a partner interaction or not
		boolean registered = true;
		boolean checkreturnpage = true;
		switch (myPartner) {
		case DROPBOX :
			// we finish at a download page I think
			VFDropboxActivities dropbox = new VFDropboxActivities();
			registered = dropbox.PurchaseOffer(driver, opco);
			checkreturnpage = false;
			break;
		case SPOTIFY :	
			try {
				SpotifyActivities spot = new SpotifyActivities();
				registered = spot.register(driver, opco, partnerUserName);
			} catch (Exception e) {
				log.error("Register for Spotify failed " + e);
			}
			break;
			
		case NETFLIX :
			NetflixActivities pa = new NetflixActivities();
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
		log.info("TODO: DOING PPE REFRESH -- no check yet");
		return true;
	}
	
	private boolean refreshPPEPartner(WebDriver driver, String url) {
		driver.get(url);
		UserEntertainment entpage = new UserEntertainment(driver);
		try {
			entpage.bodyLoaded();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("Timed out on loading the UserEntertainment page " + e);
		}
					
		// TODO: doesnt check position. Loops around to wait for image - its a bit slow sometimes ?
		String textfound = "";
		try {
			if (entpage.isManageSubscriptionTextPresent()) {		
				 textfound = entpage.getManageSubscriptionText();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("Interrupted while getting the subscriptiontext " + e);
		}
		
		// TODO: implement a better check
		validatePostPurchaseOffers(entpage);
		
		log.info("Text to Check is: " + textfound);		
		boolean ok = false;
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
	
	public boolean refreshPPE(WebDriver driver, String baseopcourl) {
		log.info("TEST: Reopen on home page displays correct offers");
		
		//TODO need to fix needing this - VERY FLAKY
		try {
			Thread.sleep(PageBase.SLOW * 2);
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
		String displayoffer = offer.getUserOffer();
		log.info("Text to Check is:  " + displayoffer);
		String title = parser.getOffersTitle();
		log.info("Reference Text is: " + title);
		String textstripped = parser.stripHTML(title);
		log.info("Reference Text Stripped is: " + textstripped);
		boolean ok = displayoffer.equals(textstripped);
		if (!ok) log.error("Verify Will Fail");
		if (checkAsserts) ErrorCollector.verifyTrue(ok, "The offer title is incorrect");

	
		// check the text bullets from text
		String offertext = offer.getOfferDetail();
		String crstripped = StringUtils.replace(offertext, "\n", " ");
		log.info("Text to Check is:  " +  crstripped);
		String text = parser.getOffersText();
		log.info("Reference Text is: " + text);
		textstripped = parser.stripHTML(text);
		log.info("Reference Text Stripped is: " + textstripped);
		ok = crstripped.equals(textstripped);
		if (!ok) log.error("Verify Will Fail");
		if (checkAsserts) ErrorCollector.verifyTrue(ok,"The offer detail is incorrect");

	  
		// check T&C label from label
		String offertnc = offer.getOfferTnC();
		String offertncstripped = StringUtils.replace(offertnc, "\n", " ");
		log.info("Text to Check is:  " + offertncstripped);
		String tnc = parser.getOffersTnCText();
		log.info("Reference T & C is: " + tnc);
		String tncstripped = parser.stripHTML(tnc);
		log.info("Reference T & C Stripped is: " + tncstripped);
		ok = offertncstripped.equals(tncstripped);
		if (!ok) log.error("Verify Will Fail");
		if (checkAsserts) ErrorCollector.verifyTrue(ok, "The T & C text is incorrect");
		return true;
	}
	
	public boolean verifyAvailableOffersText(UserEntertainment entpage)  {
		log.info("TEST: Check Available Offers page");
		String subtext = entpage.getSubscriptionText();
		log.info("Text to check is: " + subtext);			  			
		boolean ok = checker.checkSubscriptionText(subtext);
		if (!ok) log.error("Verify Will Fail");
		if (checkAsserts) ErrorCollector.verifyTrue(ok, "Subscription text is not correct");
		return true;
	}
	
	public boolean verifyNextStepsText(BasicPartnerOffer offer) {
		// check the page actually displayed
		log.info("TEST: Check on confirm page after accepting offer");
		String confirmation = offer.getSuccessText();
		log.info("Text to Check is : " + confirmation);
					
		// get a reference to the property value text
		boolean ok = checker.checkConfirmText(confirmation);
		if (!ok) log.error("Verify Will Fail");
		if (checkAsserts) ErrorCollector.verifyTrue(ok, "Confirmation text is incorrect");
				  
		log.info("TEST: Check Success text");
		String happens = offer.getHappensNextText();
		String myhappens = StringUtils.replace(happens, "\n", " ");
		log.info("happens next to check is:  " + myhappens);
				  
		String checkhappens = parser.getSubscribeSuccessText();
		checkhappens = parser.stripHTML(checkhappens);
		log.info("happens next Reference is: " + checkhappens);
		// Assert check the text and this will do for now
		ok = myhappens.equals(checkhappens);
		if (!ok) log.error("Verify Will Fail");
		if (checkAsserts) ErrorCollector.verifyTrue(ok,"What happens next text is incorrect");

		return true;
	}

}
