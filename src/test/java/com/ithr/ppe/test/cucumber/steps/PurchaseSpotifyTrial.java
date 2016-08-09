package com.ithr.ppe.test.cucumber.steps;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.cucumber.pages.PageBase;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSpotifyOffer;
import com.ithr.ppe.test.cucumber.steps.utils.AdminActivities;
import com.ithr.ppe.test.cucumber.steps.utils.IdentityActivities;
import com.ithr.ppe.test.cucumber.steps.utils.SpotifyActivities;

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
		shortMsisdn = AdminActivities.msisdnFromAdmin(driver, opco, subscription, userGroup);		
		userNameToUse = SpotifyActivities.getSpotifyUser(driver, baseSpotifyHelper, opco);
		String url = baseUserUrl + opco;
		log.info ("msisdn is: " + shortMsisdn + " username is: " + userNameToUse);
		IdentityActivities.loginToPPE (driver, shortMsisdn , pinCode, url);
			
		driver.manage().window().setSize(new Dimension(600, 600));		
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
			if (SpotifyActivities.RegisterForSpotify(driver, opco, userNameToUse)) {
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
						Thread.sleep(PageBase.SLOW);
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
		if (newentpage.isSpotifySubscriptionTextPresent()) {		
	 		textfound = newentpage.getSpotifySubscriptionText();
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
		// we can do this with curl or within a browser
		
	}

	@When("^I subscribe to spotify with a new msisdn$")
	public void UseNewMsisdnToPurchaseSpotify() throws Throwable {
		log.info("New Subscription Old Spotify User");
		// get a new MSISDN
		// use old spotify username
		// it should be trial
	}

	@Then("^I will purchase spotify trial$")
	public void TrialPurchase() throws Throwable {
		log.info("purchase trial");
	}
	
	@When("^I subscribe to spotify with same msisdn$")
	public void UseOldMsisdnTpPurchaseSpotify() {
		log.info("Old MSISDN Old Spotify User");
	}
	
	@Then("^I will purchase spotify paid$")
	public void PaidPurchase() throws Throwable {
		log.info("purchase paid");
	}
}
