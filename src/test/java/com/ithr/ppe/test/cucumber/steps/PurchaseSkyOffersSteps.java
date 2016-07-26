package com.ithr.ppe.test.cucumber.steps;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSkyOffer;
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

public class PurchaseSkyOffersSteps extends StepBase {
	public static Logger log = Logger.getLogger(PurchaseSkyOffersSteps.class);
	
	// TODO: Check which of these are always required and promote to baseclass
	private opcoTextChecker textChecker = null;
	private String fileToCheck =  "";
	private Boolean refFileValid = false;
	
	

	private JsonParser jsonParse; 	

	public PurchaseSkyOffersSteps() {
		super();
	}
	
	private boolean AcceptTheOffer () throws Throwable {
		
		String buttontext = jsonParse.getOffersOkButton();		
	    // the button is all upper case!
	    String ucbuttontext = buttontext.toUpperCase();
	    log.info("Button String is : " +  ucbuttontext);
	    
	    UserSkyOffer skyoffer = new UserSkyOffer(driver);
		skyoffer.clickAcceptOffer(buttontext);
	  
	 			  
		// check the page displayed
		log.info("TEST: Check on confirm page after accepting offer");
		String confirmation = skyoffer.getSuccessText();
		log.info("Text to Check is : " + confirmation);
		
		// get a reference to the property value text
		boolean ok = textChecker.checkConfirmText(confirmation);
		if (checkAsserts) ErrorCollector.verifyTrue(ok);
	  
		log.info("TEST: Check Success text");
	 	String happens = skyoffer.getHappensNextText();
		String myhappens = StringUtils.replace(happens, "\n", " ");
		log.info("happens next to check is:  " + myhappens);
	  
		String checkhappens = jsonParse.getSubscribeSuccessText();
		checkhappens = jsonParse.stripHTML(checkhappens);
		log.info("happens next Reference is: " + checkhappens);
		// Assert check the text and this will do for now
		if (checkAsserts) ErrorCollector.verifyTrue(myhappens.equals(checkhappens));
	  	
	    return true;
	    
	}
	
	private boolean ReOpenPPE () throws Throwable {		
		log.info("TEST: Check reopen on home page displays correct offers");
		//TODO need to fix needing this!
		Thread.sleep(5000);
		driver.get(baseUserUrl + opco);
		
		UserEntertainment entpage = new UserEntertainment(driver);
		entpage.bodyLoaded();
		
		// TODO: this is not a brilliant test -- I dont think it checks the resultant image and offer are in the correct place
		String textfound = entpage.getSkySubscriptionText();
		log.info("Text to Check is: " + textfound);
	    boolean ok = textChecker.checkSkySubscibedText(textfound);
	    if (checkAsserts) ErrorCollector.verifyTrue(ok);	    
	    return true;
	}

	@Before("@skypurchase")
	public void setUp() throws Exception {
		super.setUp();
		log.info("SetUp");
	}
	
	@After("@skypurchase")
	public void tearDown(Scenario scenario) throws Exception {
		log.info("TearDown");
		super.tearDown(scenario);
	}
	
	@Given("^I am a \"([^\"]*)\" customer purchasing sky package$")
	public void SkyCustomer(String opco) throws Throwable {
	   log.info("Given: I am a " + opco);
	   this.opco = opco.toLowerCase();
	   
	   // set up first check file for standard text
	   textChecker = new opcoTextChecker(testReferenceDir, this.opco);
	}
	
	@When("^my sky profile has a ([^\"]*) with a ([^\"]*)$")
	public void PackageInGroup(String mypackage, String usergroup) throws Throwable {
		log.info("When: I have a  " + mypackage + " with a " + usergroup);

		this.subscription = mypackage;
		this.userGroup = usergroup;	
		try {	
			// set up msisdn
			shortMsisdn = msisdnFromAdmin();		
			// handle the AA aspect
			loginToPPE(shortMsisdn);
		
		} catch(Exception e) {
			log.info("caught Exception: " + e);
			Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String line = linenumber.toString(); 
			if (!checkAsserts) GetDebugScreenShot(line);
			Assert.fail("Aborting Test"); //To fail test in case of any element identification failure		
		}
	}

	@Then("^my sky offer will come from ([^\"]*)$")
	public void OfferContainsStringsFrom(String reffilename) throws Throwable {
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
			  // the manage subscriptions section should be empty "you have no subscriptions...."
			  log.info("TEST: Check Available Offers page");
			  String subtext = entpage.getSubscriptionText();
			  log.info("Text to check is: " + subtext);			  			
			  boolean ok = textChecker.checkSubscriptionText(subtext);
		
			  if (checkAsserts) ErrorCollector.verifyTrue(ok);
			  log.info("selecting offer");
			  
			  // at this point if there is no reference file then we should not try to select offer
			  // because it probably isnt there and also probably should not be!
			  // TODO: need a better Click than this - then we can make it a general model
			  // e.g its its sky.png in uk and skytv.png in de!
			  if (entpage.checkOfferImage("sky")) {
				  entpage.ClickOfferImage("sky");

			  
				  UserSkyOffer skyoffer = new UserSkyOffer(driver);
				  // TODO A check here?
				  skyoffer.bodyLoaded();
				  skyoffer.setTnC();
			  				  
				  if (refFileValid) {
					  log.info("TEST: Check Sky Offer");
					  String displayoffer = skyoffer.getUserOffer();
					  log.info("Text to Check is:  " + displayoffer);
					  
					  // can now set up JSON parser reference file
					  jsonParse = new JsonParser(refDir + opco + "/" + fileToCheck);
					  String title = jsonParse.getOffersTitle();
					  log.info("Reference Text is: " + title);
					  if (checkAsserts) ErrorCollector.verifyTrue(displayoffer.equals(title));
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
			Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String line = linenumber.toString(); 
			if (!checkAsserts) GetDebugScreenShot(line);
			Assert.fail("Aborting Test"); 		
		}
	}
	
	@And("^I will accept the sky offer$")
	public void AcceptTheSkyOffer() throws Throwable {
		if (!refFileValid) {
			log.info("And: I Will NOT Accept the sky Offer ");
		}
		else {
			log.info("And: I Will Accept the sky Offer ");
			try {				
				boolean offeraccepted = AcceptTheOffer();
				
				boolean ppeopen = ReOpenPPE();			
				  
			} catch(Exception e){
				log.info("caught Exception: " + e);
				Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				String line = linenumber.toString(); 
				if (!checkAsserts) GetDebugScreenShot(line);
				Assert.fail("Aborting Test"); 		
			}
		}
	}
} // end class
