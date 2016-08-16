package com.ithr.ppe.test.cucumber.steps;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.utils.AdminActivities;
import com.ithr.ppe.test.cucumber.steps.utils.CommonPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.IdentityActivities;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PurchaseSpotifyOffersSteps extends StepBase {
	public static Logger log = Logger.getLogger(PurchaseSpotifyOffersSteps.class);
	
	private CommonPartnerPurchase cpp = new CommonPartnerPurchase();
	
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


	
	@Before("@check")
	public void setUp(Scenario scenario) throws Exception {
		super.setUp(scenario);
		if (checkAsserts) cpp.SetAssertCheck();
		log.info("SetUp");
	}
	
	@After("@check")
	public void tearDown() {
		log.info("TearDown");
		super.tearDown();
	}
	
	@Given("^I am a \"([^\"]*)\" customer purchasing spotify$")
	public void SpotifyCustomer(String opco) throws Exception {
	   log.info("Given: I am a " + opco + " customer purchasing spotify");
	   this.opco = opco.toLowerCase();	   
	   // set up first check file for standard text
	   cpp.DefineCheckerToUse(testReferenceDir, this.opco);
	}

	@When("^my spotify profile has a ([^\"]*) tariff with a ([^\"]*) usergroup$")
	public void PackageInGroup(String mypackage, String usergroup) throws Exception {
		log.info("When: I have a " + mypackage + " with a " + usergroup);
		this.subscription = mypackage;
		this.userGroup = usergroup;	
		
		try {
			
			
			// get Spotify user - TODO: check if we need to do this here
			userNameToUse = cpp.GetPartnerUserName(driver, basePartnerHelper, opco);			
			log.info("username is " + userNameToUse);
			if (userNameToUse.contains("ERROR")) {
				log.error("spotify helper did not return a valid username");
				Assert.fail("Spotify username is invalid - Aborting Test");
			}
			
			// set up msisdn
			driver.get(baseAdminUrl);
			shortMsisdn = AdminActivities.msisdnFromAdmin(driver, opco, subscription, userGroup);
			
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
			  cpp.VerifyAvailableOffersText(entpage);
			  CheckedScenarioScreenshot();		  
			  log.info("selecting offer");
			  
			  if (entpage.checkOfferImagePresent("spotify")) {
				  entpage.clickOfferImage("spotify");
				  
				  //  on journey to accept offer
				  BasicPartnerOffer offer = new BasicPartnerOffer(driver);
				  offer.bodyLoaded();
				  offer.setTnC();
				  
				  
				  if (refFileValid) {
					  log.info("TEST: Check Spotify Offer");
					 	 
					  // can now locate JSON parser reference file
					  String roughpath = refDir + opco + "/";
					  cpp.LocateJsonParseFile(roughpath, reffilename);	
					  cpp.VerifyOfferText(offer);
					  CheckedScenarioScreenshot();
				  }
		  			  
			  }
			  else {
				  log.error("NO VALID OFFER  Visible");
				  // TODO: check this out what is the correct behavior
				  // this may be correct behavior for some combinations
				  Assert.fail("No Valid Offer - Aborting Test");
			  }
		} catch (Exception e){
			log.info("caught Exception: " + e);
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("OfferContainsStringsFrom - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure				
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
				boolean offeraccepted = cpp.AcceptTheOffer(driver, opco, Partners.SPOTIFY);		
				CheckedScenarioScreenshot();
				if (checkAsserts) ErrorCollector.verifyTrue(offeraccepted,"offer not accepted");
				String urltouse = baseUserUrl + opco;
				boolean ppeopen = cpp.RefreshPPE(driver, urltouse);
				CheckedScenarioScreenshot();
				if (checkAsserts) ErrorCollector.verifyTrue(ppeopen, "reopen failed");
				
			}catch(Exception e){
				log.info("caught Exception: " + e);			
				String name = this.getClass().getSimpleName();
				ReportScreen(name);
				Assert.fail("Accept Spotify Offer - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure			
			}
		}		  					
	}


}
