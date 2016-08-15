package com.ithr.ppe.test.cucumber.steps;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.cucumber.pages.PageBase;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSpotifyOffer;
import com.ithr.ppe.test.cucumber.steps.utils.AdminActivities;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.IdentityActivities;
import com.ithr.ppe.test.cucumber.steps.utils.JsonParser;
import com.ithr.ppe.test.cucumber.steps.utils.SpotifyActivities;
import com.ithr.ppe.test.cucumber.steps.utils.opcoTextChecker;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PurchaseSpotifyOffersSteps extends StepBase {
	public static Logger log = Logger.getLogger(PurchaseSpotifyOffersSteps.class);
	
	private String userNameToUse = "";
	
	public PurchaseSpotifyOffersSteps() {
		super();
	}
	
	public String getSpotifyUserName () {
		return userNameToUse;
	}
	public void setSpotifyUserName (String uname) {
		userNameToUse = uname;
	}

	private boolean AcceptTheOffer() throws Exception {	
		
		String buttontext = jsonParse.getOffersOkButton();			
	    String ucbuttontext = buttontext.toUpperCase();
	    log.info("Button String should be : " +  ucbuttontext);
	    
	    UserSpotifyOffer spotifyoffer = new UserSpotifyOffer(driver);
	    String offerbuttontext = spotifyoffer.getAcceptOfferText();
	    log.info("Button String is : " + offerbuttontext);
	    if (checkAsserts) ErrorCollector.verifyTrue(ucbuttontext.equals(offerbuttontext), "Button text is incorrect");

	    
		spotifyoffer.clickAcceptOffer();		
	
		// if this is true we are ok
		if (SpotifyActivities.RegisterForSpotify(driver, opco, userNameToUse)) {
			
			//TODO: need to add a NICE confirmation text check that the purchase has completed and then it is safe to check all the text and reopen ppe
			boolean done = false;
			for (int i = 0; i < 10 && !done; i++) {
				String notice = spotifyoffer.getSuccessText();		
				log.info(notice);
				if (!notice.contains("Completing purchase"))  {
						log.info("purchase process complete");
						done = true;
				} else {
					log.info("Waiting for purchase notification.....");
					Thread.sleep(PageBase.SLOW);
				}
			}
			
			VerifyHappensNext(spotifyoffer);
			
			return true;
		}
					    
	    return false;  

	}
	
	private boolean ReOpenPPE () throws Exception {
		log.info("TEST: Check reopen on home page displays correct offers");
		
		//TODO need to fix needing this - VERY FLAKY
		Thread.sleep(5000);
		driver.get(baseUserUrl + opco);
		
		UserEntertainment entpage = new UserEntertainment(driver);
		entpage.bodyLoaded();
					
		// TODO: doesnt check position. Loops around to wait for image - its a bit slow sometimes ?
		String textfound = "";
		if (entpage.isSpotifySubscriptionTextPresent()) {		
			 textfound = entpage.getSpotifySubscriptionText();
		}
		
		log.info("Text to Check is: " + textfound);
	    return textChecker.checkSpotifySubscibedText(textfound);	    
	}
	
	// TODO: opportunities for abstraction
	private boolean verifyOffer (UserSpotifyOffer offer) {
		String displayoffer = offer.getUserOffer();
		log.info("Text to Check is:  " + displayoffer);
		String title = jsonParse.getOffersTitle();
		log.info("Reference Text is: " + title);
		String textstripped = jsonParse.stripHTML(title);
		if (checkAsserts) ErrorCollector.verifyTrue(displayoffer.equals(textstripped), "The offer title is incorrect");

	
		// check the text bullets from text
		String offertext = offer.getOfferDetail();
		String crstripped = StringUtils.replace(offertext, "\n", " ");
		log.info("Text to Check is:  " +  crstripped);
		String text = jsonParse.getOffersText();
		log.info("Reference Text is: " + text);
		textstripped = jsonParse.stripHTML(text);
		if (checkAsserts) ErrorCollector.verifyTrue(crstripped.equals(textstripped),"The offer detail is incorrect");

	  
		// check T&C label from label
		String offertnc = offer.getOfferTnC();
		log.info("Text to Check is:  " + offertnc);
		String tnc = jsonParse.getOffersTnCText();
		log.info("Reference T & C is: " + tnc);
		String tncstripped = jsonParse.stripHTML(tnc);
		if (checkAsserts) ErrorCollector.verifyTrue(offertnc.equals(tncstripped), "The T & C text is incorrect");
		CheckedScenarioScreenshot();
		return true;
	}
	
	private boolean VerifyAvailableOffers(UserEntertainment entpage) {
		log.info("TEST: Check Available Offers page");
		String subtext = entpage.getSubscriptionText();
		log.info("Text to check is: " + subtext);			  			
		boolean ok = textChecker.checkSubscriptionText(subtext);
		if (checkAsserts) ErrorCollector.verifyTrue(ok);
		CheckedScenarioScreenshot();
		
		return true;
	}
	
	
	private boolean VerifyHappensNext(UserSpotifyOffer offer) {
		// check the page actually displayed
		log.info("TEST: Check on confirm page after accepting offer");
		String confirmation = offer.getSuccessText();
		log.info("Text to Check is : " + confirmation);
					
		// get a reference to the property value text
		boolean ok = textChecker.checkConfirmText(confirmation);
		if (checkAsserts) ErrorCollector.verifyTrue(ok, "Confirmation text is incorrect");
				  
		log.info("TEST: Check Success text");
		String happens = offer.getHappensNextText();
		String myhappens = StringUtils.replace(happens, "\n", " ");
		log.info("happens next to check is:  " + myhappens);
				  
		String checkhappens = jsonParse.getSubscribeSuccessText();
		checkhappens = jsonParse.stripHTML(checkhappens);
		log.info("happens next Reference is: " + checkhappens);
		// Assert check the text and this will do for now
		if (checkAsserts) ErrorCollector.verifyTrue(myhappens.equals(checkhappens),"What happens next text is incorrect");

		CheckedScenarioScreenshot();
		
		return true;
	}
	
	@Before("@spotifypurchase")
	public void setUp(Scenario scenario) throws Exception {
		super.setUp(scenario);
		log.info("SetUp");
	}
	
	@After("@spotifypurchase")
	public void tearDown() {
		log.info("TearDown");
		super.tearDown();
	}
	
	@Given("^I am a \"([^\"]*)\" customer purchasing spotify$")
	public void SpotifyCustomer(String opco) throws Exception {
	   log.info("Given: I am a " + opco + " customer purchasing spotify");
	   this.opco = opco.toLowerCase();
	   
	   // set up first check file for standard text
	   textChecker = new opcoTextChecker(testReferenceDir, this.opco);
	}

	@When("^my spotify profile has a ([^\"]*) tariff with a ([^\"]*) usergroup$")
	public void PackageInGroup(String mypackage, String usergroup) throws Exception {
		log.info("When: I have a " + mypackage + " with a " + usergroup);
		this.subscription = mypackage;
		this.userGroup = usergroup;	
		
		try {
			// set up msisdn
			driver.get(baseAdminUrl);
			shortMsisdn = AdminActivities.msisdnFromAdmin(driver, opco, subscription, userGroup);
			// get Spotify user
			userNameToUse = SpotifyActivities.getSpotifyUser (driver, baseSpotifyHelper, opco);
			log.info("username is " + userNameToUse);
			if (userNameToUse.contains("ERROR")) {
				log.error("spotify helper did not return a valid username");
				Assert.fail("Spotify username is invalid - Aborting Test");
			}
			
			// handle the AA aspect
			String url = baseUserUrl + opco;
			IdentityActivities.loginToPPE (driver, shortMsisdn , pinCode, url);
			
		} catch (Exception e){
			log.info("caught Exception: " + e);
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("Package In Group - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure				
		}
		
	}
	@Then("^my spotify offer details will come from ([^\"]*)$")
	public void OfferContainsStringsFrom(String reffilename) throws Exception {
		log.info("Then: my offer will come from " + reffilename + "file");
		
		if (!reffilename.contains("Not Valid")) {
			refFileValid = true;
			log.info("The reference file is Valid");
		}		
		try {
			// might be a short wait here....while new page loads...
			  driver.manage().window().setSize(new Dimension(600, 600));		
			  
			  // Entertainment page - offer page directly or click on image icon to get text
			  UserEntertainment entpage = new UserEntertainment(driver);
			  entpage.bodyLoaded();
			 		  
			  // There should be available offers for THIS MSISDN -
			  // if there are no offers this is probably an error
			  // the manage subscriptions section should be empty "you have no subscriptions..."
			  VerifyAvailableOffers(entpage);
			  
			  
			  log.info("selecting offer");
			  if (entpage.checkOfferImagePresent("spotify")) {
				  entpage.clickOfferImage("spotify");
				  
				  //  on journey to accept offer
				  UserSpotifyOffer spotifyoffer = new UserSpotifyOffer(driver);
				  spotifyoffer.bodyLoaded();
				  spotifyoffer.setTnC();
				  
				  
				  if (refFileValid) {
					  log.info("TEST: Check Spotify Offer");
					  
					  // can now set up JSON parser reference file
					  String fileToCheck = CommandExecutor.execFindExactJsonFile(refDir + opco + "/", reffilename + " v");
					  log.info("going to use json file: " + fileToCheck);
					  jsonParse = new JsonParser(refDir + opco + "/" + fileToCheck);					  
					  verifyOffer(spotifyoffer);				  
				  }
		  			  
			  }
			  else {
				  log.error("NO VALID OFFER For Spotify Visible");
				  // TODO: check this out what is the correct behavior
				  // this may be correct behavior for some combinations
				  Assert.fail("No Valid Offer for Spotify - Aborting Test");
			  }
		} catch (Exception e){
			log.info("caught Exception: " + e);
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("Offer Contains Strings From - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure				
		}
	}

	
	@And("^I will accept the spotify offer$")
	public void AcceptTheSpotifyOffer() throws Exception {
		
		if (!refFileValid) {
			log.info("And: I Will NOT Accept the Spotify Offer ");
		}
		else {
			log.info("And: I Will Accept the Spotify Offer ");
			try {			
				boolean offeraccepted = AcceptTheOffer();
				if (checkAsserts) ErrorCollector.verifyTrue(offeraccepted);
				boolean ppeopen = ReOpenPPE();
				if (checkAsserts) ErrorCollector.verifyTrue(ppeopen);
				
			}catch(Exception e){
				log.info("caught Exception: " + e);			
				String name = this.getClass().getSimpleName();
				ReportScreen(name);
				Assert.fail("Accept Spotify Offer - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure			
			}
		}		  					
	}


}
