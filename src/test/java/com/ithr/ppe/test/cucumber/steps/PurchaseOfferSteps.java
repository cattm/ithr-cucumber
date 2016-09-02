package com.ithr.ppe.test.cucumber.steps;
/**
 * Implements the cucumber steps required to Purchase a Partner Offer
 * This class with catch exceptions and report 
 * This class will control the execution of the test
 * This class will capture screens as required
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.interfaces.IEpilog;
import com.ithr.ppe.test.cucumber.steps.interfaces.IPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.interfaces.IProlog;
import com.ithr.ppe.test.cucumber.steps.utils.AdminFacade;
import com.ithr.ppe.test.cucumber.steps.utils.CommonEpilog;
import com.ithr.ppe.test.cucumber.steps.utils.CommonPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.utils.CommonProlog;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.IdentityFacade;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class PurchaseOfferSteps extends StepBase {
	public static Logger log = Logger.getLogger(PurchaseOfferSteps.class);
	
	private IProlog pl = new CommonProlog();
	private IPartnerPurchase cpp = new CommonPartnerPurchase();
	private IEpilog ep = new CommonEpilog();
	
	private Partners myPartner = null;
	private String userNameToUse = "";
	
	public PurchaseOfferSteps() {
		super();
	}
	
	@Before("@dropboxpurchase, @netflixpurchase, @nowtvpurchase, @skypurchase, @spotifypurchase")
	public void setUp(Scenario scenario) throws Exception {
		super.setUp(scenario);
		log.info("SetUp");
	}
	
	@After("@dropboxpurchase, @netflixpurchase, @nowtvpurchase, @skypurchase, @spotifypurchase")
	public void tearDown() {
		log.info("TearDown");
		super.tearDown();
	}
	
	@Given("^I am a \"([^\"]*)\" customer purchasing the \"([^\"]*)\" offer$")
	public void PartnerCustomer(String opco, String partner) {
	   log.info("Given: I am a " + opco + " customer purchasing " + partner + " offer");
	   
	   // convert string to enum;
	   this.myPartner = Partners.valueOf(partner.toUpperCase());
	   
	   this.opco = opco.toLowerCase();	
	   log.info("opco set to " + this.opco);
	   // set up first check file for standard text
	   pl.createCheckerToUse(testReferenceDir, this.opco);
	}
	
	@When("^my profile has a ([^\"]*) tariff with a ([^\"]*) usergroup$")
	public void PackageInGroup(String mypackage, String usergroup) {
		log.info("When: I have a " + mypackage + " with a " + usergroup + " usergroup");
		this.subscription = mypackage;
		this.userGroup = usergroup;	
		
		try {		
			// get a partner user - TODO: check if we need to do this here or can do it later 
			userNameToUse = pl.getPartnerUserName(driver, basePartnerHelper, opco, myPartner);			
			log.info("username is " + userNameToUse);
			if (userNameToUse.contains("ERROR")) {
				log.error(" Helper did not return a valid username");
				Assert.fail("username is invalid - Aborting Test");
			}
			
			// Need an MSISDN to log in
			// we may have set this up in ER or not - handled in AdminActivities based on params
			driver.get(baseAdminUrl);
			shortMsisdn = AdminFacade.msisdnFromAdmin(driver, opco, subscription, userGroup, myPartner);
			
			// handle the AA aspect and login
			IdentityFacade.loginToPPE (driver, opco, myPartner, shortMsisdn , pinCode, baseUserUrl);
			
		} catch (Exception e){
			log.error("Caught Exception: " + e);
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("Package In Group - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure				
		}
		
	}
	
	private boolean checkAndSelectExternal(String reffilename)  {
		// Entertainment page - offer page directly or click on image icon to get text
		UserEntertainment entpage = new UserEntertainment(driver);
		try {
			entpage.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("interrupted page loaded check " + e);
		}
		 		  
		// There should be available offers for THIS MSISDN -
		// if there are no offers this is probably an error
		// the manage subscriptions section should be empty "you have no subscriptions..."
		pl.verifyOffersAvailableText(entpage);
		CheckedScenarioScreenshot();	
		  
		log.info("selecting offer");
		  
		// TODO: put in check ok
		pl.verifyPrePurchaseOffers(entpage);
		  
		if (cpp.selectPartnerOffer(myPartner, entpage)) {		  
			//  on journey to accept offer
			BasicPartnerOffer offer = new BasicPartnerOffer(driver);
			try {
				offer.bodyLoaded();
			} catch (InterruptedException e) {
				log.error("interrupted page loaded check " + e);
			}
			offer.setTnC();			  

			log.info("TEST: Check Partner Offer");				 	 
			// can now locate JSON parser reference file
			String roughpath = refDir + opco + "/";
			pl.createJsonParserFromFile(roughpath, reffilename);	
			cpp.verifyOfferText(offer);
			CheckedScenarioScreenshot();
		}
		else {
			  log.error("NO VALID OFFER  Visible");
			  // TODO: check this out what is the correct behavior
			  // this may be correct behavior for some combinations
			  Assert.fail("No Valid Offer - Aborting Test");
		}
		
		return true;
	}
	
	private boolean checkAndSelectInternal(String reffilename) {
	//  page looks different to standard model - no mini icons to select....
		BasicPartnerOffer offer = new BasicPartnerOffer(driver);
		try {
			offer.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("interrupted page loaded check " + e);
		}
		offer.setTnC();			  
				  
		log.info("TEST: Check Internal Partner Offer");				 	 
		// can now locate JSON parser reference file
		String roughpath = refDir + opco + "/";
		pl.createJsonParserFromFile(roughpath, reffilename);	
		cpp.verifyOfferText(offer);
		CheckedScenarioScreenshot();
		return true;
	}
	
	
	@Then("^my offer details will come from ([^\"]*)$")
	public void OfferContainsStringsFrom(String reffilename)  {
		log.info("Then: my offer will come from " + reffilename + " file");
		ErrorCollector.verifyFalse(!reffilename.contains("Not Valid"), "The Reference File is set to not valid");	
		
		try {
			// might be a short wait here....while new page loads...
			  driver.manage().window().setSize(new Dimension(600, 600));
			  switch (myPartner) {
			  	case DROPBOX : checkAndSelectInternal(reffilename);
			  	break;
			    default : checkAndSelectExternal(reffilename);
			    break;
			  }
			  
		  			  		 
		} catch (Exception e){
			log.error("caught Exception: " + e);
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("OfferContainsStringsFrom - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure				
		}
	}


	@And("^I will accept and confirm the offer$")
	public void AcceptPartnerOffer() {		
		log.info("And: I Will Accept the Offer ");
		try {					
			boolean offeraccepted = cpp.acceptTheOffer(driver, opco, myPartner);			
			CheckedScenarioScreenshot();
			ErrorCollector.verifyTrue(offeraccepted,"offer not accepted");
				
			// now go back to PPE and refresh and chec pwd
			String urltouse = baseUserUrl + opco;			
			boolean ppeopen = ep.refreshPPE(driver, urltouse, myPartner);				
			CheckedScenarioScreenshot();
			ErrorCollector.verifyTrue(ppeopen, "reopen failed");
				
		}catch(Exception e){
			log.error("caught Exception: " + e);			
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			Assert.fail("Accept Partner Offer - Abort Test on Exception : MSISDN " + shortMsisdn); //To fail test in case of any element identification failure			
		}
	}		  					
		
}
