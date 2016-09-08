package com.ithr.ppe.test.cucumber.steps;

/**
 * Implements and example of terminating an offer and then attempting to use the same partner identifier to purchase again
 * 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
// TODO - Add all the checks on correct offer etc

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.PageBase;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSpotifyOffer;
import com.ithr.ppe.test.cucumber.steps.utils.AdminFacade;
import com.ithr.ppe.test.cucumber.steps.utils.IdentityFacade;
import com.ithr.ppe.test.cucumber.steps.utils.SpotifyFacade;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
// TODO: This is a hack to check opportunities for reuse!

public class PurchaseSpotifyTrial extends StepBase{
	public static Logger log = Logger.getLogger(PurchaseSpotifyTrial.class);

	String userNameToUse;
	protected String opco = "gb"; //default
	protected String subscription;
	protected String userGroup;
	protected String shortMsisdn;

	protected String 			fileToCheck =  "";
	protected Boolean 			refFileValid = false;

	public PurchaseSpotifyTrial() {
		// return
	}
	@Before("@trial")
	public void setUp(Scenario scenario) throws Exception {
		
		super.setUp(scenario);
		log.info("SetUp");
	}
	
	@After("@trial")
	public void tearDown() {
		log.info("TearDown");
	
		super.tearDown();
	}
	
	private boolean PerformBasicSpotifyPurchase ( ) throws Exception {
		// Very little checking here - assume it works otherwise its not worth it
		// this is a Background step activity
		driver.get(baseAdminUrl);
		shortMsisdn = AdminFacade.msisdnFromAdmin(driver, opco, subscription, userGroup, Partners.SPOTIFY);	
		SpotifyFacade spot = new SpotifyFacade();
		userNameToUse = spot.getUser(driver, basePartnerHelper, opco);
		String url = baseUserUrl + opco;
		log.info ("msisdn is: " + shortMsisdn + " username is: " + userNameToUse);
		IdentityFacade.loginToPPE (driver, opco, Partners.SPOTIFY, shortMsisdn , pinCode, baseUserUrl);
			
		driver.manage().window().setSize(new Dimension(600, 600));	
		// TODO: these MUST BE REMOVED
		Thread.sleep(10000); 
		
		// Entertainment page - offer page directly or click on image icon to get text
		UserEntertainment entpage = new UserEntertainment(driver);	
		entpage.bodyLoaded();
		Thread.sleep(10000);
		
		log.info("going to check for spotify button");	 		  
		if (entpage.checkOfferImagePresent("spotify")) {
			entpage.clickOfferImage("spotify");
		  
			UserSpotifyOffer spotifyoffer = new UserSpotifyOffer(driver);
			spotifyoffer.bodyLoaded();
			spotifyoffer.setTnC();
			spotifyoffer.clickAcceptOffer();				
			// if this is true we are ok we have registered on spotify
			if (spot.register(driver, opco, userNameToUse)) {
				boolean done = false;
				// TODO: test code to tidy up and add a check  
				for (int i = 0; i < 6 && !done; i++) {
					String notice = spotifyoffer.getSuccessText();		
					log.info(notice);
					if (!notice.contains("Completing purchase"))  {
						log.info("purchase complete");
						done = true;
					} else {
						log.info("Waiting.......");
						Thread.sleep(CommonConstants.SLOW);
					}
				}
	    		return true; 
	    	}
		}
		log.info("return false");
		return false;
	}
	
	private boolean CheckSpotifyIsPurchased () throws InterruptedException {
		driver.get(baseUserUrl + opco);		
		UserEntertainment newentpage = new UserEntertainment(driver);
		newentpage.bodyLoaded();	
		String textfound = "";
		if (newentpage.isManageSubscriptionTextPresent()) {		
	 		textfound = newentpage.getManageSubscriptionText();
	 		log.info(textfound);
		}
		return false;
	}
	
	@Given("^I am a \"([^\"]*)\" customer already purchased Spotify with:$")
	public void AlreadyPurchasedSpotify(String opco, DataTable params) throws Throwable {

		this.opco = opco.toLowerCase();
		log.info("Already Purchased Spotify");
		String thepackage = null;
	    String usergroup  = null;
	    String containedin = null;
		for (Map<String, String> map : params.asMaps(String.class, String.class)) {
	        thepackage = map.get("package");
	        usergroup = map.get("usergroup");
	        containedin = map.get("contained in");
	        log.info("we have :" + thepackage + " and " + usergroup + " and " + containedin);
		}
		this.subscription = thepackage;
		this.userGroup = usergroup;
		this.fileToCheck = containedin;
		
		if (PerformBasicSpotifyPurchase()) {
			// lets check the purcse moved from offersha
			CheckSpotifyIsPurchased();
		}
		
	}

	@Given("^I can delete my user in spotify$")
	public void CanDeleteSpotifyUser() throws Throwable {
		log.info("have deleted Spotify User");
		// we could do this with curl or within a browser
		// lets check status and then delete 
		SpotifyFacade spot = new SpotifyFacade();
		String response = spot.getUserStatus(driver, basePartnerHelper, opco, userNameToUse);
		log.info(response);
		response = spot.terminateUser(driver, basePartnerHelper, opco, userNameToUse);
		log.info(response);
		//  TODO: check order is something like -- acquiredOrderIds" : [ "Vodafone_67327944-ee97-4ff9-a297-3706981565a2" ]
	}

	@When("^I subscribe to spotify with a new msisdn$")
	public boolean UseNewMsisdnToPurchaseSpotify() throws Throwable {
		log.info("New Subscription Old Spotify User");
		// get a new MSISDN from a NEW BROWSER
		// kill the old one and start a new one
		getNewDriver();
		
		driver.get(baseAdminUrl);;
		shortMsisdn = AdminFacade.msisdnFromAdmin(driver, opco, subscription, userGroup, Partners.SPOTIFY);	
	
		IdentityFacade.loginToPPE (driver, opco, Partners.SPOTIFY, shortMsisdn , pinCode, baseUserUrl);
		
		driver.manage().window().setSize(new Dimension(600, 600));	
		// TODO: these MUST BE REMOVED
		Thread.sleep(10000); 
		
		// Entertainment page - offer page directly or click on image icon to get text
		UserEntertainment entpage = new UserEntertainment(driver);	
		entpage.bodyLoaded();
		Thread.sleep(10000);
		
		
		// TODO: need to look very carefully at the offer
		log.info("going to check for spotify button");	 		  
		if (entpage.checkOfferImagePresent("spotify")) {
			entpage.clickOfferImage("spotify");
		  
			UserSpotifyOffer spotifyoffer = new UserSpotifyOffer(driver);
			spotifyoffer.bodyLoaded();
			spotifyoffer.setTnC();
			spotifyoffer.clickAcceptOffer();		
			
			// use old spotify username to login
			SpotifyFacade spot = new SpotifyFacade();
			if (spot.login(driver, opco, userNameToUse)) {
				boolean done = false;
				// TODO: test code to tidy up and add a check  
				for (int i = 0; i < 6 && !done; i++) {
					String notice = spotifyoffer.getSuccessText();		
					log.info(notice);
					if (!notice.contains("Completing purchase"))  {
						log.info("purchase complete");
						done = true;
					} else {
						log.info("Waiting.......");
						Thread.sleep(CommonConstants.SLOW);
					}
				}
	    		return true; 
	    	}
		}
		log.info("return false");
		return false;
				
		
		
		// offer should be ... trial
	}

	@When("^I subscribe to spotify with same msisdn$")
	public void UseOldMsisdnTpPurchaseSpotify() {
		log.info("Old MSISDN Old Spotify User");
		// offer (when expired will be paid!!!
	}
	
	@Then("^I will purchase spotify trial$")
	public void TrialPurchase() throws Throwable {
		log.info("purchase trial");
	}
	

	@Then("^I will purchase spotify paid$")
	public void PaidPurchase() throws Throwable {
		log.info("purchase paid");
	}
}
