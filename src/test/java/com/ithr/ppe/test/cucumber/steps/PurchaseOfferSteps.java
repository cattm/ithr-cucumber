package com.ithr.ppe.test.cucumber.steps;
/**
 * Implements the cucumber steps required to Purchase a Partner Offer
 * This class with catch exceptions and report 
 * This class will control the execution of the test
 * This class will capture screens as required
 * 
 * Note the catch blocks here are for safety - in case I have not handled the exceptions properly at a lower level
 * TODO: remove if possible
 * 
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.base.ICustomerBuilder;
import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.base.VFCustomerBuilder;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.interfaces.IEpilog;
import com.ithr.ppe.test.cucumber.steps.interfaces.IPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.interfaces.IProlog;
import com.ithr.ppe.test.cucumber.steps.utils.CommonEpilog;
import com.ithr.ppe.test.cucumber.steps.utils.CommonPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.utils.CommonProlog;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;

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
		
	private Customer customer = null;
	private ICustomerBuilder bd = new VFCustomerBuilder();
	
	public PurchaseOfferSteps() {
		super();
	}
	

	@Before("@purchase")
	public void setUp(Scenario scenario) throws Exception {
		super.setUp(scenario);
		log.info("Steps SetUp");
	}
	
	@After("@purchase")
	public void tearDown() {
		log.info("Steps TearDown");
		super.tearDown();
	}
	
	
	// TOOD: it is vital that if any of the prolog methods fail the test fails need to make sure the exceptions are caught properly
	// REVIEW this
	@Given("^I am a \"([^\"]*)\" customer purchasing the \"([^\"]*)\" offer$")
	public void PartnerCustomer(String opco, String partner) {
	   log.info("Given: I am a " + opco + " customer purchasing " + partner + " offer");
	   bd.Build();
	   bd.updateBuild(opco.toLowerCase(), Partners.valueOf(partner.toUpperCase()));
	    	
	   // set up first check file for standard text
	   pl.createChecker(testReferenceDir, opco);
	}
	
	@When("^my profile has a ([^\"]*) tariff with a ([^\"]*) usergroup$")
	public void PackageInGroup(String mypackage, String usergroup) {
		log.info("When: I have a " + mypackage + " with a " + usergroup + " usergroup");
		bd.appendToBuild(mypackage, usergroup);
		customer = bd.getCustomer();	
		
		// try blocks in steps are really catch alls - in case I have not captured all at point of creation
		try {		
			
			String username = pl.getPartnerUserName(driver, basePartnerHelper, customer);
			customer.setUserName(username);
			log.info("username is " + username);
			if (username.contains("ERROR")) {
				log.error(" Helper did not return a valid username");
				ErrorCollector.fail("USERNAME is invalid - Aborting Test");
				return;
			}
			
			// Need an MSISDN to log in
			driver.get(baseAdminUrl);
			String msisdn = pl.getNewMsisdn(driver, customer);
			if (msisdn.contains("ERROR")) {
				log.error(" Helper did not return a valid username");
				ErrorCollector.fail("MSISDN is invalid - Aborting Test");
				return;
			}
			customer.setMsisdn(msisdn);
			// handle the AA aspect and login
			pl.LoginOk (driver, customer, pinCode, baseUserUrl);
			
		} catch (Exception e){
			log.error("Caught Exception: " + e);
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			ErrorCollector.fail("Package In Group - Abort Test on Exception : MSISDN " + customer.getMsisdn()); //To fail test in case of any element identification failure				
		}
		
	}
	
	private void SelectExternal() {
		UserEntertainment entpage = new UserEntertainment(driver);
		try {
			entpage.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("interrupted page loaded check " + e);
		}
		
		log.info("selecting offer");
		if (cpp.selectPartnerOffer(customer.getPartner(), entpage)) {		  
			//  on journey to accept offer
			
			BasicPartnerOffer offer = new BasicPartnerOffer(driver);
			try {
				offer.bodyLoaded();
			} catch (InterruptedException e) {
				log.error("interrupted page loaded check " + e);
			}
			
			// amazingly some opco/partners do not need T&C
			if (needTandC.hasTnc(customer.getOpco().toUpperCase(), customer.getPartner().toString())) {
				offer.setTnC();			  
			}
			
			log.info("TEST: Check Partner Offer");				 	 	
			cpp.verifyOfferText(offer, customer);
			CheckedScenarioScreenshot();
		}
		else {
			  log.error("NO VALID OFFER to select");
			  // TODO: check this out what is the correct behavior
			  // this may be correct behavior for some combinations
			  String name = this.getClass().getSimpleName();
			  ReportScreen(name);
			  ErrorCollector.fail("No Valid Offer - Aborting Test");
		}
		
		
	}
	
	private boolean CheckExternal()  {	
		// Entertainment page - offer page directly or click on image icon to get text
		UserEntertainment entpage = new UserEntertainment(driver);
		try {
			entpage.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("interrupted page loaded check " + e);
		}
		 		  
		// There should be available offers for THIS MSISDN -
		boolean ok = pl.verifyOffers(entpage, customer);
		CheckedScenarioScreenshot();		  	  
		return ok;
	}	
	private boolean SelectInternal() {
		
		//  page looks different to standard model - no mini icons to select....
		BasicPartnerOffer offer = new BasicPartnerOffer(driver);
		try {
			offer.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("interrupted page loaded check " + e);
		}	
		
		offer.setTnC();		
		
		log.info("TEST: Check Internal Partner Offer");				 	 
		cpp.verifyOfferText(offer, customer);
		CheckedScenarioScreenshot();
		return true;
	}
		
	@Then("^my offer details will come from ([^\"]*)$")
	public void OfferContainsStringsFrom(String reffilename)  {
		log.info("Then: my offer will come from " + reffilename + " file");
		ErrorCollector.verifyFalse(!reffilename.contains("Not Valid"), "The Reference File is set to not valid");	
		String roughpath = refDir + customer.getOpco() + "/";
		pl.createParser(roughpath, reffilename);	
		
		// now safe to initialise all parsers
		// TODO: this could be a lot better!
		cpp.initialiseChecks();

		try {
			// might be a short wait here....while new page loads...
			  driver.manage().window().setSize(new Dimension(600, 600));
			  switch (customer.getPartner()) {
			  	case DROPBOX : SelectInternal();
			  	break;
			    default : 
			    	if (CheckExternal()) {
			    		SelectExternal();
			    	};		    
			    break;
			  }
			  	  			  		 
		} catch (Exception e){
			log.error("caught Exception: " + e);
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			ErrorCollector.fail("OfferContainsStringsFrom - Abort Test on Exception : MSISDN " + customer.getMsisdn()); //To fail test in case of any element identification failure				
		}
	}

	@And("^I will accept and confirm the offer$")
	public void AcceptPartnerOffer() {		
		log.info("And: I Will Accept the Offer ");
		try {			
			log.info("partner is currently " + customer.getPartner().toString());
			boolean offeraccepted = cpp.acceptTheOffer(driver, customer);			
			CheckedScenarioScreenshot();
			ErrorCollector.verifyTrue(offeraccepted,"offer not accepted");
				
			// now go back to PPE and refresh and check 
			String urltouse = baseUserUrl + customer.getOpco();	
			ep.initialiseChecks();
			boolean ppeopen = ep.refresh(driver, urltouse, customer);				
			CheckedScenarioScreenshot();
			ErrorCollector.verifyTrue(ppeopen, "reopen failed");
				
		}catch(Exception e){ 
			log.error("caught Exception: " + e);			
			String name = this.getClass().getSimpleName();
			ReportScreen(name);
			ErrorCollector.fail("Accept Partner Offer - Abort Test on Exception : MSISDN " + customer.getMsisdn()); //To fail test in case of any element identification failure			
		}
	}		  					
		
}
