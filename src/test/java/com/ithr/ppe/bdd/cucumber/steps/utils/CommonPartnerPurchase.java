package com.ithr.ppe.bdd.cucumber.steps.utils;

/**
 * Implements the basic model of a partner offer purchase
 * It will be common for most if not all purchases 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.base.Customer;
import com.ithr.ppe.bdd.commons.CommonConstants;
import com.ithr.ppe.bdd.commons.Partners;
import com.ithr.ppe.bdd.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.bdd.cucumber.pages.UserEntertainment;
import com.ithr.ppe.bdd.cucumber.steps.interfaces.IExternalPartner;
import com.ithr.ppe.bdd.cucumber.steps.interfaces.IPartnerPurchase;
import com.ithr.ppe.bdd.cucumber.steps.interfaces.IVFPartner;

public class CommonPartnerPurchase implements IPartnerPurchase {
	public static Logger log = Logger.getLogger(CommonPartnerPurchase.class);

	private static JsonParser parser = null;
	private static opcoTextChecker checker = null;
	private Partners myPartner = null;
	private boolean firstpurchase = true;
	
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
	
	private String formButtonLocator(boolean lower) {
		String prototype = parser.getOpco() + "-" + parser.getPartner()  + "-" + parser.getType();
		
		if (parser.getTrial().contentEquals("true")) {
			prototype = prototype + "-trial";
		}
		if (parser.getpackageId().contentEquals("")) {
			log.info("formed : " + prototype.toLowerCase());
			return prototype.toLowerCase();
		} else if (lower) {
			prototype = prototype +"-" + parser.getpackageId();
			log.info("formed : " + prototype.toLowerCase());
			return prototype.toLowerCase();
		} else {
			prototype = prototype +"-" + parser.getpackageId();
			log.info("formed : " + prototype);
			return prototype;
		}
	}
	
	public boolean selectPartnerOffer(Partners partner, UserEntertainment entpage)  {
		// this is a very primative implementation
		// we may have to cope with a situation where there are a number of offers from same partners
		// we will need to select the correct one
		// TODO: build a more comprehensive selection solution -- clickWithinSinglePartnerList(String partnerstring, String offerstring);
		
		log.info("Setting mypartner to " + partner.toString());
		myPartner = partner;
		boolean found = false;
		String imagestring = "";
		switch (myPartner) {
		case BILDPLUS   : 
		case CHILITV    : 
		case HBO		: 
		case NETFLIX    : 
		case NOWTV		:
		case SPOTIFY 	: 		
		case SKY 		: 
			imagestring = formButtonLocator(true);
			break;	
		case DEEZER		: 
			imagestring = formButtonLocator(false);
			break;
		case DROPBOX    :
			//dropbox is an example of where we do not need to find the correct offer to select
			found = true;				
		default : 
			break;
		}	
		if (!found) { 
			// TODO: small wait loop because it can be very slow - lest see if we can remove this please
			try {
				Thread.sleep(CommonConstants.SLOW * 3);
			} catch (InterruptedException e) {
				log.info("got interrupted while sleeping " +  e);
			}
			
			// found = entpage.checkOfferImagePresent(imagestring);
			
			try {
					found = entpage.clickWithinSinglePartnerList(parser.getPartner(), imagestring);
					//entpage.clickOfferImage(imagestring);
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
	
	private boolean RegisterExternal (IExternalPartner external, WebDriver driver, String opco, String partnerusername) {
		boolean registered = false;
		try {
			registered = external.register(driver, opco, partnerusername);
		} catch (Exception e) {
			log.error("External Partner Register failed " + e);
			ErrorCollector.fail("External Partner register failed");
		}
		return registered;
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
		case CHILITV :
			if (firstpurchase) {
				log.info("Register for Chili");
				IExternalPartner chili = new ChiliFacade();
				registered = RegisterExternal(chili, driver, opco, partnerusername);
				firstpurchase = false;
			}
			break;
		case BILDPLUS :
			checkreturnpage = false;
			break;
		case DEEZER :
			checkreturnpage = false;
			break;
		case DROPBOX :
			// we finish at a download page
			IVFPartner dropbox = new VFDropboxFacade();
			registered = acceptVFPOffer(dropbox, driver);
			checkreturnpage = false;
			break;
		case HBO : 
			if (firstpurchase) {
				log.info("Register for HBO");
				IExternalPartner hbo = new HBOFacade();
				registered = RegisterExternal(hbo, driver, opco, partnerusername);
				firstpurchase = false;
			}
			break;
			
		case SPOTIFY :	
			if (firstpurchase) {
				log.info("Register for Spotify");
				IExternalPartner spot = new SpotifyFacade();
				registered = RegisterExternal(spot, driver, opco, partnerusername);
				firstpurchase = false;
			}
			break;
			
		case NETFLIX :
			if (firstpurchase) {
				log.info("Register for Netflix");
				IExternalPartner pa = new NetflixFacade();
				registered = RegisterExternal(pa, driver, opco, partnerusername);
				firstpurchase = false;
				checkreturnpage = false;
			}
			break;
					
		case SKY :
		default : break;
		}
		
		if (checkreturnpage && registered) {
			log.info("going into delay loop: registered and checking return page ");
			//TODO: need to add a NICE confirmation text check that the purchase has completed and then it is safe to check all the text and reopen ppe
			WaitforPurchaseToComplete (offer);
			return verifyNextStepsText(offer);
		}	else return registered;
				
	}
		
	private boolean titleValid(Customer customer) {
		
		if (customer.getOpco().equalsIgnoreCase("ES") && customer.getPartner().toString().equalsIgnoreCase("Spotify")) {
			return false;
		}
		if (customer.getOpco().equalsIgnoreCase("NL") && customer.getPartner().toString().equalsIgnoreCase("Netflix")) {
			return false;
		}
		if (customer.getOpco().equalsIgnoreCase("IT") && customer.getPartner().toString().equalsIgnoreCase("Netflix")) {
			return false;
		}
		if (customer.getOpco().equalsIgnoreCase("IT") && customer.getPartner().toString().equalsIgnoreCase("ChiliTV")) {
			return false;
		}
		return true;
		
	}
	
	public boolean verifyOfferText(BasicPartnerOffer offer, Customer customer){
		/*
		For some offers for some countries this field is not set
		example - NL Netflix
		I will comment out until I find a nice way of making it optional
		*/
		log.info("TEST: Verify the Offer");
		if (titleValid(customer)) {
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
			//log.info("Raw HTML from page : " + offer.getRawOfferTnC());
			//log.info("Raw Text from HTML page : "+ offer.getOfferTnC() );
			String offertncstripped = StringUtils.replace(offer.getOfferTnC(), "\n", " ");	
			//debug
			//log.info("Straight from JSON: " + parser.getOffersTnCText());
			//log.info("And with nons replaced: " + parser.getOffersTnCText().replaceAll("\\P{Print}","X"));
			//log.info("and in UTF8 form : " + parser.toUTF(parser.getOffersTnCText()));
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
