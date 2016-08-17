package com.ithr.ppe.test.cucumber.steps;

/**
 * Implements the cucumber steps required to Purchase a sky offer
 * Expectation is that we may be able to template this in a common model and just extend that model 
 * This class with catch exceptions and report 
 * This class will control the execution of the test
 * This class will capture screens as required
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSkyOffer;
import com.ithr.ppe.test.cucumber.steps.utils.AdminActivities;
import com.ithr.ppe.test.cucumber.steps.utils.CommonPartnerPurchase;
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
	
	private CommonPartnerPurchase cpp = new CommonPartnerPurchase();

	public PurchaseSkyOffersSteps() {
		super();
	}
	
	
	@Before("@skypurchase")
	public void setUp(Scenario scenario) throws Exception {
		super.setUp(scenario);
		if (checkAsserts) cpp.setAssertCheck();
		log.info("SetUp");
	}
	
	@After("@skypurchase")
	public void tearDown() {
		log.info("TearDown");
		super.tearDown();
	}
	
	@Given("^I am a \"([^\"]*)\" customer purchasing sky package$")
	public void SkyCustomer(String opco) throws Exception {
	   log.info("Given: I am a " + opco + " customer purchasing SKY");
	   this.opco = opco.toLowerCase();	   
	   
	   // set up first check file for standard text   
	   cpp.defineCheckerToUse(testReferenceDir, this.opco);
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
			String url = baseUserUrl + opco;
			IdentityActivities.loginToPPE (driver, shortMsisdn , pinCode, url);
		
		} catch(Exception e) {
			log.info("caught Exception: " + e);
			//e.printStackTrace();
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
		
		if (!reffilename.contains("Not Valid")) {
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
			  cpp.verifyAvailableOffersText(entpage);
			  CheckedScenarioScreenshot();
			  log.info("Selecting sky Offer");
			  
			  
			  // at this point if there is no reference file then we should not try to select offer
			  // because it probably isnt there and also probably should not be!
			  // TODO: Need to consider there just might be multiple offers
			  // cpp.validatePrePurchaseOffers(entpage);
			  
			  if (cpp.selectPartnerOffer(Partners.SKY, entpage)) {
				    
				  // same page for all opcos/partners?
				  BasicPartnerOffer offer = new BasicPartnerOffer(driver);
				 
				  offer.bodyLoaded();
				  offer.setTnC();
				  
				  if (refFileValid) {
					  log.info("TEST: Check Sky Offer");	
					  
					  // can now locate JSON parser reference file
					  String roughpath = refDir + opco + "/";
					  cpp.locateJsonParseFile(roughpath, reffilename);					  
					  cpp.verifyOfferText(offer);
					  CheckedScenarioScreenshot();
				  }
			  }
			  else {
				  log.info("NO VALID OFFER Visible");
				  // TODO: check this out what is the correct behavior
				  // this may be correct behavior for some combinations
				  Assert.fail("No Valid Offer - Aborting Test");
			  }
		}
		catch (Exception e){
			log.info("caught Exception: " + e);
			//e.printStackTrace();
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
			log.info("And: I Will NOT Accept the Offer ");
		}
		else {
			log.info("And: I Will Accept the Offer ");
			try {	
				boolean offeraccepted = cpp.acceptTheOffer(driver, opco, Partners.SKY);		
				CheckedScenarioScreenshot();
				if (checkAsserts) ErrorCollector.verifyTrue(offeraccepted,"offer not accepted");
				String urltouse = baseUserUrl + opco;
				boolean ppeopen = cpp.refreshPPE(driver, urltouse);
				CheckedScenarioScreenshot();
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
