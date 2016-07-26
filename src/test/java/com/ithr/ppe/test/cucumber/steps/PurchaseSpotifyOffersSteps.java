package com.ithr.ppe.test.cucumber.steps;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.DateStamp;
import com.ithr.ppe.test.cucumber.pages.SpotifyHelper;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSpotifyOffer;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifyLoginOrRegister;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifyRegistration;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifySuccess;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.JsonParser;
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
	private opcoTextChecker textChecker = null;
	private String fileToCheck =  "";
	private Boolean refFileValid = false;
	private String userNameToUse = "";
	private JsonParser jsonParse; 	
	
	public PurchaseSpotifyOffersSteps() {
		super();
	}
	
	private String getSpotifyUser () throws Throwable {
		DateStamp myds = new DateStamp();
		String rn = myds.getRanDateFormat();
		String urlString = baseSpotifyHelper + "?username=ithrtest" +  rn + "&opco=" + opco;
		log.info(urlString);
		driver.get(urlString);
		SpotifyHelper spotpage = new SpotifyHelper(driver);
		
		return spotpage.getUserName();
	}
	
	private boolean RegisterForSpotify () throws Throwable  {
		// Spotify choose to register
		SpotifyLoginOrRegister logorreg = new SpotifyLoginOrRegister(driver);
		logorreg.clickRegister();
  
		// fill in registration form
		SpotifyRegistration regpage = new SpotifyRegistration(driver);
		regpage.setUser(userNameToUse);
		regpage.setPassword("password");
		regpage.setEmail(userNameToUse + "@ithr.com");
		regpage.setEmailConfirm(userNameToUse + "@ithr.com");
		regpage.setDob("1960", "May", "23");
		regpage.setMale();
		regpage.clickSubmit();
  
		// get success page and check (for piece of mind that we are on the correct page)
		SpotifySuccess spotsuccess = new SpotifySuccess(driver);
		spotsuccess.bodyLoaded(); // give the page a chance to load
		
		if (spotsuccess.getHello().contentEquals("hello world")) {
			spotsuccess.hitOk();
			return true;
		} else {
			// Well it all went wrong 
			return false;
		}
	}
	
	private boolean AcceptTheOffer() throws Throwable {
		String buttontext = jsonParse.getOffersOkButton();			
		// ACCEPT THE OFFER
	    // the button is all upper case!
	    String ucbuttontext = buttontext.toUpperCase();
	    log.info("Button String is : " +  ucbuttontext);
	    
	    UserSpotifyOffer spotifyoffer = new UserSpotifyOffer(driver);
		spotifyoffer.clickAcceptOffer(buttontext);		
	
		// if this is true we are ok
		if (RegisterForSpotify()) {
			
			// check the page displayed
			log.info("TEST: Check on confirm page after accepting offer");
			String confirmation = spotifyoffer.getSuccessText();
			log.info("Text to Check is : " + confirmation);
			
			// get a reference to the property value text
			boolean ok = textChecker.checkConfirmText(confirmation);
			if (checkAsserts) ErrorCollector.verifyTrue(ok);
		  
			log.info("TEST: Check Success text");
		 	String happens = spotifyoffer.getHappensNextText();
			String myhappens = StringUtils.replace(happens, "\n", " ");
			log.info("happens next to check is:  " + myhappens);
		  
			String checkhappens = jsonParse.getSubscribeSuccessText();
			checkhappens = jsonParse.stripHTML(checkhappens);
			log.info("happens next Reference is: " + checkhappens);
			// Assert check the text and this will do for now
			if (checkAsserts) ErrorCollector.verifyTrue(myhappens.equals(checkhappens));
			return true;
		}
					    
	    return false;
	}
	
	
	private boolean ReOpenPPE () throws Throwable {
		log.info("TEST: Check reopen on home page displays correct offers");
		//TODO need to fix needing this - VERY FLAKY
		Thread.sleep(5000);
		driver.get(baseUserUrl + opco);
		
		UserEntertainment entpage = new UserEntertainment(driver);
		entpage.bodyLoaded();
					
		// TODO: this is not a brilliant loop round until its visible or throw an exception if it times out
		// all a bit scruffy really!
		String textfound = "";
		if (entpage.isSpotifySubscriptionTextPresent()) {		
			 textfound = entpage.getSpotifySubscriptionText();
		}
		
		log.info("Text to Check is: " + textfound);
		
	    return textChecker.checkSpotifySubscibedText(textfound);	    
	}
	
	@Before("@spotifypurchase")
	public void setUp() throws Exception {
		super.setUp();
		log.info("SetUp");
	}
	
	@After("@spotifypurchase")
	public void tearDown(Scenario scenario) throws Exception {
		log.info("TearDown");
		super.tearDown(scenario);
	}
	
	@Given("^I am a \"([^\"]*)\" customer purchasing spotify$")
	public void SpotifyCustomer(String opco) throws Throwable {
	   log.info("Given: I am a " + opco + " customer purchasing spotify");
	   this.opco = opco.toLowerCase();
	   
	   // set up first check file for standard text
	   textChecker = new opcoTextChecker(testReferenceDir, this.opco);
	}


	@When("^my spotify profile has a ([^\"]*) with a ([^\"]*)$")
	public void PackageInGroup(String mypackage, String usergroup) throws Throwable {
		log.info("When: I have a " + mypackage + " with a " + usergroup);
		this.subscription = mypackage;
		this.userGroup = usergroup;	
		
		try {
			// set up msisdn
			shortMsisdn = msisdnFromAdmin();
		
			// get Spotify user
			userNameToUse = getSpotifyUser ();
			log.info("username is " + userNameToUse);
			if (userNameToUse.contains("ERROR")) {
				log.error("spotify helper did not return a valid username");
				Assert.fail("Spotify username is invalid - Aborting Test");
			}
			
			// handle the AA aspect
			loginToPPE(shortMsisdn);
			
		} catch (Exception e){
			log.error("caught Exception: " + e);
			Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String line = linenumber.toString(); 
			if (!checkAsserts) GetDebugScreenShot(line);
			Assert.fail("Aborting Test"); //To fail test in case of any element identification failure		
		}
		
	}
	@Then("^my spotify offer will come from ([^\"]*)$")
	public void OfferContainsStringsFrom(String reffilename) throws Throwable {
		log.info("Then: my offer will come from " + reffilename + "file");
		fileToCheck = reffilename;
		if (!fileToCheck.contains("Not Valid")) {
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
			  // the manage subscriptions section should be empty "you have no subscriptions...."
			  log.info("TEST: Check Available Offers page");
			  String subtext = entpage.getSubscriptionText();
			  log.info("Text to check is: " + subtext);			  			
			  boolean ok = textChecker.checkSubscriptionText(subtext);
			  if (checkAsserts) ErrorCollector.verifyTrue(ok);
			  
			  log.info("selecting offer");
			  if (entpage.checkOfferImage("spotify")) {
				  entpage.ClickOfferImage("spotify");
				  
				  //  on journey to accept offer
				  UserSpotifyOffer spotifyoffer = new UserSpotifyOffer(driver);
				 
				  spotifyoffer.bodyLoaded();
				  spotifyoffer.setTnC();
				  // - click......
				  
				  if (refFileValid) {
					  log.info("TEST: Check Spotify Offer");
					  String displayoffer = spotifyoffer.getUserOffer();
					  log.info("Text to Check is:  " + displayoffer);
					  
					  // can now set up JSON parser reference file
					  jsonParse = new JsonParser(refDir + opco + "/" + fileToCheck);
					  String title = jsonParse.getOffersTitle();
					  log.info("Reference Text is: " + title);
					  if (checkAsserts) ErrorCollector.verifyTrue(displayoffer.equals(title));
				  }
		  			  
			  }
			  else {
				  log.error("NO VALID OFFER For Spotify Visible");
				  // TODO: check this out what is the correct behavior
				  // this may be correct behavior for some combinations
				  Assert.fail("No Valid Offer - Aborting Test");
			  }
		} catch (Exception e){
			log.error("caught Exception: " + e);
			Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String line = linenumber.toString(); 
			if (!checkAsserts) GetDebugScreenShot(line);
			Assert.fail("Aborting Test"); //To fail test in case of any element identification failure		
		}
	}

	
	@And("^I will accept the spotify offer$")
	public void AcceptTheSpotifyOffer() throws Throwable {
		
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
				log.error("caught Exception: " + e);
				Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				String line = linenumber.toString(); 
				if (!checkAsserts) GetDebugScreenShot(line);
				Assert.fail("Aborting Test"); //To fail test in case of any element identification failure		
			}
		}		  				
	
	}


}
