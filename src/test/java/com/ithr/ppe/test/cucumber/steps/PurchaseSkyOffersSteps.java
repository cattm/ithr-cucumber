package com.ithr.ppe.test.cucumber.steps;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSkyOffer;
import com.ithr.ppe.test.cucumber.steps.utils.AdminActivities;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.IdentityActivities;
import com.ithr.ppe.test.cucumber.steps.utils.JsonParser;
import com.ithr.ppe.test.cucumber.steps.utils.opcoTextChecker;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PurchaseSkyOffersSteps extends StepBase {
	public static Logger log = Logger.getLogger(PurchaseSkyOffersSteps.class);
	 	

	public PurchaseSkyOffersSteps() {
		super();
	}
	
	private boolean AcceptTheOffer () throws Exception {
		
		String buttontext = jsonParse.getOffersOkButton();		
	    // the button text is all upper case!
	    String ucbuttontext = buttontext.toUpperCase();
	    log.info("Button String should be : " +  ucbuttontext);
	    UserSkyOffer skyoffer = new UserSkyOffer(driver);
	    String offerbuttontext = skyoffer.getAcceptOfferText();
	    log.info("Button String is : " + offerbuttontext);
	    if (checkAsserts) ErrorCollector.verifyTrue(ucbuttontext.equals(offerbuttontext), "Button text is incorrect");
	   
	    skyoffer.clickAcceptOffer();
	 			  
		// check the page displayed
		VerifyHappensNext(skyoffer);
	  	
	    return true;
	    
	}
	
	private boolean ReOpenPPE () throws Exception {		
		log.info("TEST: Check reopen on home page displays correct offers");
		//TODO need to fix needing this - NO fixed sleeps
		Thread.sleep(5000);
		driver.get(baseUserUrl + opco);
		
		UserEntertainment entpage = new UserEntertainment(driver);
		entpage.bodyLoaded();
		
		// TODO: check the resultant image and offer are in the correct place
		// Also See Spotify Test and see if we can absorb
		String textfound = entpage.getSkySubscriptionText();
		log.info("Text to Check is: " + textfound);
		ScenarioScreenshot();
	    return textChecker.checkSkySubscibedText(textfound);
	    
	}
	
	
	/* TODO: The verification logic here and elsewhere in this file is VERY similar to that in other Steps
	*  We should be able to refactor this quite easily to pass a page object and a parser object and do the required checks
	*/
	private boolean VerifyAvailableOffers (UserEntertainment entpage) {
		log.info("TEST: Check Available Offers page");		 
		String subtext = entpage.getSubscriptionText();
		log.info("Text to check is: " + subtext);			  			
		boolean ok = textChecker.checkSubscriptionText(subtext);
		if (checkAsserts) {
			ErrorCollector.verifyTrue(ok, "subscription text is incorrect");
		    ScenarioScreenshot();
		}
		return true;
	}
	private boolean VerifyOffer(UserSkyOffer offer) {
		// look out for html in the json which we wont see in the page data an also the addition of Carriage Returns
		// check description of offer as title
		String displayoffer = offer.getUserOffer();
	    log.info("Text to Check is:  " + displayoffer);
		String title = jsonParse.getOffersTitle();
		log.info("Reference Title is: " + title);
		if (checkAsserts) ErrorCollector.verifyTrue(displayoffer.equals(title), "The offer title is incorrect");
		  
		// TODO: check the text bullets from text
		// TODO: This test FAILS currently with text that looks identical 
		String offertext = offer.getOfferDetail();
		String crstripped = StringUtils.replace(offertext, "\n", " ");
		log.info("Text to Check is:  " +  crstripped);
		String text = jsonParse.getOffersText();
		log.info("Reference Text is: " + text);
		String textstripped = jsonParse.stripHTML(text);
		log.info("Stripped is: " + textstripped);
		if (checkAsserts) ErrorCollector.verifyTrue(crstripped.equals(textstripped),"The offer detail is incorrect");
		  
		// check T&C label from label
		String offertnc = offer.getOfferTnC();
		log.info("Text to Check is:  " + offertnc);
		String tnc = jsonParse.getOffersTnCText();
		log.info("Reference T & C is: " + tnc);
		String tncstripped = jsonParse.stripHTML(tnc);
		log.info("Stripped is: " + tncstripped);
		if (checkAsserts) ErrorCollector.verifyTrue(offertnc.equals(tncstripped), "The T & C text is incorrect");

		ScenarioScreenshot();
		return true;
	}
	
	private boolean VerifyHappensNext (UserSkyOffer offer) {
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
		
		ScenarioScreenshot();
		return true;
	}

	@Before("@skypurchase")
	public void setUp(Scenario scenario) throws Exception {
		super.setUp(scenario);
		log.info("SetUp");
	}
	
	@After("@skypurchase")
	public void tearDown() {
		log.info("TearDown");
		super.tearDown();
	}
	
	@Given("^I am a \"([^\"]*)\" customer purchasing sky package$")
	public void SkyCustomer(String opco) throws Exception {
	   log.info("Given: I am a " + opco);
	   this.opco = opco.toLowerCase();
	   
	   // set up first check file for standard text
	   textChecker = new opcoTextChecker(testReferenceDir, this.opco);
	}
	
	@When("^my sky profile has a ([^\"]*) tariff with a ([^\"]*) usergroup$")
	public void PackageInGroup(String mypackage, String usergroup) throws Exception {
		log.info("When: my sky profile has a  " + mypackage + " tariff with a " + usergroup);

		this.subscription = mypackage;
		this.userGroup = usergroup;	
		try {	
			// set up msisdn	
			driver.get(baseAdminUrl);
			shortMsisdn = AdminActivities.msisdnFromAdmin(driver, opco, subscription, userGroup);
			// handle the AA aspect
	
			String url = baseUserUrl + opco;
			IdentityActivities.loginToPPE (driver, shortMsisdn , pinCode, url);
		
		} catch(Exception e) {
			log.info("caught Exception: " + e);
			e.printStackTrace();
			StackTraceElement[] stackTrace = e.getStackTrace(); 	
			StackTraceElement mystackline = stackTrace[stackTrace.length - 1];
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("PackageInGroup - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure		
		}
	}

	@Then("^my sky offer details will come from ([^\"]*)$")
	public void OfferContainsStringsFrom(String reffilename) throws Exception {
		log.info("Then: my offer will come from " + reffilename + "file");
		fileToCheck = reffilename;
		if (!fileToCheck.contains("Not Valid")) {
			refFileValid = true;
			log.info("The reference file is Valid");
		}

		try {	  
			  /////////////////////////////////////////////////////////
			  // might be a short wait here....while new page loads...
			  driver.manage().window().setSize(new Dimension(600, 600));		
			  
			  // Entertainment page - offer page directly or click on image icon to get text
			  UserEntertainment entpage = new UserEntertainment(driver);
			  entpage.bodyLoaded();
			  
			  
			  // There should be available offers for THIS MSISDN -
			  // if there are no offers this is probably an error
			  // the manage subscriptions section should be empty  - NO Subscriptions
			  VerifyAvailableOffers(entpage);
			  log.info("Selecting sky Offer");
			  
			  
			  // at this point if there is no reference file then we should not try to select offer
			  // because it probably isnt there and also probably should not be!
			  // TODO: need a better Click than this - then we can make it a general model
			  // e.g its its sky.png in uk and skytv.png in de!
			  if (entpage.checkOfferImagePresent("sky")) {
				  entpage.clickOfferImage("sky");

			  
				  UserSkyOffer skyoffer = new UserSkyOffer(driver);
				  // TODO A check here?
				  skyoffer.bodyLoaded();
				  skyoffer.setTnC();
			  				  
				  if (refFileValid) {
					  log.info("TEST: Check Sky Offer");	
					  
					  // can now set up JSON parser reference file
					  jsonParse = new JsonParser(refDir + opco + "/" + fileToCheck);
					  VerifyOffer(skyoffer);
				  }
			  }
			  else {
				  log.info("NO VALID OFFER For Sky Visible");
				  // TODO: check this out what is the correct behavior
				  // this may be correct behavior for some combinations
				  Assert.fail("No Valid Sky Offer - Aborting Test");
			  }
		}
		catch (Exception e){
			log.info("caught Exception: " + e);
			e.printStackTrace();
			StackTraceElement[] stackTrace = e.getStackTrace(); 	
			StackTraceElement mystackline = stackTrace[stackTrace.length - 1];
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("OfferContainsStringFrom - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure	
		}
	}
	
	@And("^I will accept the sky offer$")
	public void AcceptTheSkyOffer() throws Exception {
		if (!refFileValid) {
			log.info("And: I Will NOT Accept the sky Offer ");
		}
		else {
			log.info("And: I Will Accept the sky Offer ");
			try {	 
				boolean offeraccepted = AcceptTheOffer();		
				if (checkAsserts) ErrorCollector.verifyTrue(offeraccepted,"offer not accepted");
				boolean ppeopen = ReOpenPPE();
				if (checkAsserts) ErrorCollector.verifyTrue(ppeopen, "reopen failed");
				  
			} catch(Exception e){
				log.info("caught Exception: " + e);
				//e.printStackTrace();
				StackTraceElement[] stackTrace = e.getStackTrace(); 	
				StackTraceElement mystackline = stackTrace[stackTrace.length - 1];
				String name = this.getClass().getSimpleName();
				ReportScreen(name);
				Assert.fail("AcceptTheSkyOffer - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure	
			}
		}
	}
} // end class
