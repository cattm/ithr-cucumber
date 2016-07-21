package com.ithr.ppe.test.cucumber.steps;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.cucumber.pages.AdminHome;
import com.ithr.ppe.test.cucumber.pages.UserEntertainmentPage;
import com.ithr.ppe.test.cucumber.pages.UserMSISDNEntry;
import com.ithr.ppe.test.cucumber.pages.UserSMSChallenge;
import com.ithr.ppe.test.cucumber.pages.UserSkyOffer;
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
	private static String propertyFile = "test.properties";
	private opcoTextChecker textChecker = null;
	private String fileToCheck =  "";
	private String opco = "gb"; //default
	private Boolean refFileValid = false;
	private Boolean userGroupValid = false;
	
	private String shortMsisdn;
	private String subscription;
	private String userGroup;
	private String checkUrl;
	private JsonParser jsonParse; 	

	public PurchaseSkyOffersSteps() {
		super(propertyFile);
	}
	
	public void loginToPPE (String msisdn) throws InterruptedException {
		driver.get(baseUserUrl + opco);
		log.info("LoginToPPE -");
		// Entry page - AAA MSISDN and PIN challenge
		UserMSISDNEntry msisdnentry = new UserMSISDNEntry(driver);
		msisdnentry.elementLoaded(By.id("nextButton"));
		//msisdnentry.waitClickable(By.id("nextButton"));
		msisdnentry.setShortMobile(msisdn);
		msisdnentry.clickNextButton();
		log.info("Have Set Mobile Number");
		// SMS Challenge - pin
		UserSMSChallenge smschallenge = new UserSMSChallenge(driver);
		smschallenge.setSMS("0000");
		log.info("Have Set Pin");
		smschallenge.clickRegistertButton();
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
	
	@Given("^I am a \"([^\"]*)\" customer with ([^\"]*)$")
	public void CustomerWithPackage(String opco, String mypackage) throws Throwable {
	   log.info("Given: I am a " + opco + " customer with " + mypackage);
	   this.subscription = mypackage;
	   this.opco = opco.toLowerCase();
	   
	   // set up first check file for standard text
	   textChecker = new opcoTextChecker(testReferenceDir, opco);
	}

	@When("^I am in ([^\"]*)$")
	public void InGroup(String usergroup) throws Throwable {
		log.info("Then: I am in Group " + usergroup);
		this.userGroup = usergroup;
		if (!usergroup.contains("Not Valid")) {
			userGroupValid = true;
			log.info("User Group is Valid");
		}
		AdminHome adminhome = new AdminHome(driver);
		adminhome.setOpco(opco);
		
		if (userGroupValid) {
			adminhome.setSubscription(subscription);
			adminhome.setUserGroup(userGroup);
		} else {
			adminhome.setNoUserGroup();
		}
		shortMsisdn = adminhome.getShortMSISDN();
		log.info("MSISN is : " + shortMsisdn);
		// can put a check in hear to check the contents of the subscription
		checkUrl = adminhome.getSbuscriptionCheckUrl();		
		driver.get(checkUrl);
		
		try {
			  loginToPPE(shortMsisdn);
			  
		} catch(Exception e) {
			log.info("caught Exception: " + e);
			Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String line = linenumber.toString(); 
			if (!checkAsserts) GetDebugScreenShot(line);
			Assert.fail(); //To fail test in case of any element identification failure		
		}

	}

	@Then("^my offer will come from ([^\"]*)$")
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
			  UserEntertainmentPage entpage = new UserEntertainmentPage(driver);
			  entpage.bodyLoaded();
			  
			  
			  // There should be available offers for THIS MSISDN -
			  // if there are no offers this is probably an error
			  // the manage subscriptions section should be empty "you have no subscriptions...."
			  log.info("TEST: Check Available Offers page");
			  String subtext = entpage.getSubscriptionText();
			  log.info("Text to check is: " + subtext);			  			
			  boolean ok = textChecker.checkSubscriptionText(subtext);
		
			  if (checkAsserts) Assert.assertTrue(ok);
			  log.info("selecting offer");
			  
			  // at this point if there is no reference file then we should not try to select offer
			  // because it probably isnt there and also probably should not be!
			  // TODO: need a better Click than this - then we can make it a general model
			  // e.g its its sky.png in uk and skytv.png in de!
			  if (entpage.checkOfferImage("sky")) {
				  entpage.ClickOfferImage("sky");

			  
				  UserSkyOffer skyoffer = new UserSkyOffer(driver);
				  // TODO A check here of time?
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
					  if (checkAsserts) Assert.assertTrue(displayoffer.equals(title));
				  }
			  }
			  else {
				  log.info("NO VALID OFFER For Sky Visible");
				  // TODO: check this out what is the correct behavior
				  // this may be correct behavior for some combinations
				  Assert.fail();
			  }
		}
		catch(Exception e){
			// TODO: hard coded directory path to fix
			log.info("caught Exception: " + e);
			Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String line = linenumber.toString(); 
			if (!checkAsserts) GetDebugScreenShot(line);
			Assert.fail(); //To fail test in case of any element identification failure		
		}
	}
	
	@And("^I will accept the offer$")
	public void AcceptTheOffer() throws Throwable {
		if (!refFileValid) {
			log.info("And: I Will NOT Accept the Offer ");
		}
		else {
			log.info("And: I Will Accept the Offer ");
			try {
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
				if (checkAsserts) Assert.assertTrue(ok);
			  
				log.info("TEST: Check Success text");
			 	String happens = skyoffer.getHappensNextText();
				String myhappens = StringUtils.replace(happens, "\n", " ");
				log.info("happens next to check is:  " + myhappens);
			  
				String checkhappens = jsonParse.getSubscribeSuccessText();
				checkhappens = jsonParse.stripHTML(checkhappens);
				log.info("happens next Reference is: " + checkhappens);
				// Assert check the text and this will do for now
				if (checkAsserts) Assert.assertTrue(myhappens.equals(checkhappens));
			  
			  
				
				log.info("TEST: Check reopen on home page displays correct offers");
				driver.get(baseUserUrl + opco);
			
				UserEntertainmentPage entpage = new UserEntertainmentPage(driver);
				entpage.bodyLoaded();
				// TODO: this is not a brilliant test -- I dont think it checks its in the correct place
				String textfound = entpage.getSkySubscriptionText();
				log.info("Text to Check is: " + textfound);
			    ok = textChecker.checkSkySubscibedText(textfound);
			    if (checkAsserts) Assert.assertTrue(ok);
			    
			    // just hold this last page for a while 
				Thread.sleep(5000);
				
			  
			}catch(Exception e){
				log.info("caught Exception: " + e);
				Integer linenumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				String line = linenumber.toString(); 
				if (!checkAsserts) GetDebugScreenShot(line);
				Assert.fail(); //To fail test in case of any element identification failure		
			}
		}
	}
} // end class
