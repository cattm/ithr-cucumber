package com.ithr.ppe.test.cucumber.steps.utils;

/**
 * Implements the basic model of a partner offer purchase
 * It will be common for most if not all purchases 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.interfaces.IExternalPartner;
import com.ithr.ppe.test.cucumber.steps.interfaces.IPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.interfaces.IVFPartner;

public class CommonPartnerPurchase implements IPartnerPurchase {
	public static Logger log = Logger.getLogger(CommonPartnerPurchase.class);

	private static JsonParser parser = null;
	private static opcoTextChecker checker = null;
	private Partners myPartner = null;
	
/*
 * We analyse the offers
 * Accept the offers
 * check the result page
 * 
 */
	public void initialiseChecks() {
		parser = JsonParser.getInstance();
		checker = opcoTextChecker.getInstance();
	}
	public boolean selectPartnerOffer(Partners partner, UserEntertainment entpage)  {
		// this is a very primative implementation
		// we may have to cope with a situation where there are a number of offers from same partners
		// we will need to select the correct one
		// TODO: build a more comprehensive selection solution
		log.info("Setting mypartner to " + partner.toString());
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
				} catch (Exception e) {
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
	
	public boolean acceptTheOffer(WebDriver driver, Customer customer)  {
		String opco = customer.getOpco();
		String partnerusername = customer.getUserName();
		if (myPartner == null) {
			myPartner = customer.getPartner();
		} else {
			if (myPartner != customer.getPartner()) log.error("attempting to overwrite partner with new value: why? > " + customer.getPartner().toString());
		}					

	    BasicPartnerOffer offer = new BasicPartnerOffer(driver);	   
	    ErrorCollector.verifyEquals(offer.getAcceptOfferText(), parser.getOffersOkButton().toUpperCase(), "Button text is incorrect"); 
		offer.clickAcceptOffer();
		
		// At this point we need to know if the action involves a partner interaction or not
		boolean registered = true;
		boolean checkreturnpage = true;
		switch (myPartner) {
		case DEEZER :
			checkreturnpage = false;
			break;
		case DROPBOX :
			// we finish at a download page
			IVFPartner dropbox = new VFDropboxFacade();
			registered = acceptVFPOffer(dropbox, driver);
			checkreturnpage = false;
			break;
		case SPOTIFY :	
			try {
				IExternalPartner spot = new SpotifyFacade();
				registered = spot.register(driver, opco, partnerusername);
			} catch (Exception e) {
				log.error("Register for Spotify failed " + e);
			}
			break;
			
		case NETFLIX :
			IExternalPartner pa = new NetflixFacade();
			registered = pa.register(driver, opco, partnerusername);
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
		
	public boolean verifyOfferText(BasicPartnerOffer offer, Customer customer){
		/*
		For some offers for some countries this field is not set
		example - NL Netflix
		I will comment out until I find a nice way of making it optional
		*/
		if (!(customer.getOpco().equalsIgnoreCase("NL") && customer.getPartner().toString().equalsIgnoreCase("Netflix"))) {
			log.info("TEST: Verify the Offer");
			String textstripped = parser.stripHTML(parser.getOffersTitle());	
			ErrorCollector.verifyEquals(offer.getUserOffer(),textstripped, "The offer title is incorrect");
		}
	
		// check the text bullets from text
		String crstripped = StringUtils.replace(offer.getOfferDetail(), "\n", " ");
		String ntextstripped = parser.stripHTML(parser.getOffersText());
		ErrorCollector.verifyEquals(crstripped, ntextstripped,"The offer detail is incorrect");
		
	  
		// check T&C label from label
		// some opcos dont have t and c
		TandCRequired needTandC = TandCRequired.getInstance();
		if (needTandC.hasTnc(customer.getOpco().toUpperCase(), customer.getPartner().toString())) {
			String offertncstripped = StringUtils.replace(offer.getOfferTnC(), "\n", " ");	
			String tncstripped = parser.stripHTML(parser.getOffersTnCText());
			ErrorCollector.verifyEquals(offertncstripped,tncstripped, "The T & C text is incorrect");
		}
	
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
