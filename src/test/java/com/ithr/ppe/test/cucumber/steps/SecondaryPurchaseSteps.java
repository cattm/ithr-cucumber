package com.ithr.ppe.test.cucumber.steps;



import java.util.List;
import java.util.Map;

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

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class SecondaryPurchaseSteps extends StepBase {
	
	public static Logger log = Logger.getLogger(SecondaryPurchaseSteps.class);

	private IProlog pl = new CommonProlog();
	private IPartnerPurchase cpp = new CommonPartnerPurchase();
	private IEpilog ep = new CommonEpilog();
		
	private Customer customer = null;
	private ICustomerBuilder bd = new VFCustomerBuilder();
	
	public SecondaryPurchaseSteps() {
		super();
	}
	

	@Before("@checkit")
	public void setUp(Scenario scenario) throws Exception {
		super.setUp(scenario);
		log.info("SetUp");
	}
	
	@After("@checkit")
	public void tearDown() {
		log.info("TearDown");
		super.tearDown();
	}

	private boolean performInitialProlog(String opco, String partner, List<Map<String, String>> details) {
		String sub = "";
		String group = "";
		String file = "";
		for (Map<String, String> mymap : details) {
			sub = mymap.get("package");
			group = mymap.get("usergroup");
			file = mymap.get("contained in");
		}
		
		bd.Build();
		bd.updateBuild(opco.toLowerCase(), Partners.valueOf(partner.toUpperCase()));		    	   
		bd.appendToBuild(sub, group);
		customer = bd.getCustomer();	
		pl.createChecker(testReferenceDir, opco);
		String roughpath = refDir + customer.getOpco() + "/";
		pl.createParser(roughpath, file);
		
		customer.setUserName(pl.getPartnerUserName(driver, baseAdminUrl, customer));
	
		driver.get(baseAdminUrl);
		String msisdn = pl.getNewMsisdn(driver, customer);
		if (!msisdn.equals("FAILED")) {
			customer.setMsisdn(msisdn);
		} else return false;
		
		boolean loginok = pl.LoginOk(driver, customer, pinCode, baseUserUrl);
		driver.manage().window().setSize(new Dimension(600, 600));
		CheckedScenarioScreenshot();
		return loginok;
	}
	
	private boolean performInitialPurchase() {
		cpp.initialiseChecks();
		/*
		 * At this point we need to do some selecting 
		 */
		log.info("selecting offer");
		UserEntertainment entpage = new UserEntertainment(driver);
		try {
			entpage.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("interrupted page loaded check " + e);
		}
		
		boolean ok = false;
		if (cpp.selectPartnerOffer(customer.getPartner(), entpage)) {		  
			//  on journey to accept offer	
			CheckedScenarioScreenshot();
			BasicPartnerOffer offer = new BasicPartnerOffer(driver);
			try {
				offer.bodyLoaded();
			} catch (InterruptedException e) {
				log.error("interrupted page loaded check " + e);
			}
			
			if (needTandC.hasTnc(customer.getOpco().toUpperCase(), customer.getPartner().toString())) {
				offer.setTnC();			  
			}
				
			ok = true;
		} else log.info("could not select offer");
		if (ok) {
			return cpp.acceptTheOffer(driver, customer);
		}
		return ok;		
	}
	
	private boolean checkInitialPurchaseOutcome() {
		String urltouse = baseUserUrl + customer.getOpco();	
		ep.initialiseChecks();
		return ep.refresh(driver, urltouse, customer);
	}
	
	@Given("^I am a \"([^\"]*)\" customer Who Initially purchases \"([^\"]*)\" offer:$")
	public void aCustomerIntialPurchase(String opco, String partner, List<Map<String, String>> details) throws Throwable {
		log.info("aCustomerInitialPurchase");
		if (performInitialProlog(opco, partner, details)) {
			CheckedScenarioScreenshot();
			if (performInitialPurchase()) {
				CheckedScenarioScreenshot();
				checkInitialPurchaseOutcome();
				CheckedScenarioScreenshot();
			}
		}	 
		// if any of these fail we need to abort and fail the test
	}

	
	/* we have msisdn
	 * Need to refresh parsers
	 * its a different service - get another username
	 * select offer
	 * accept offer
	 * check post offer
	 * signify success
	 * 
	 */
	@Given("^I can see the \"([^\"]*)\" offer$")
	public void iCanSeeTheOffer(String partner) throws Throwable {
		log.info("iCanSeeTheOffer from " + partner);	
		customer.setPartner(Partners.valueOf(partner.toUpperCase()));
		customer.setUserName(pl.getPartnerUserName(driver, baseAdminUrl, customer));
		// maybe check the offer is there? Or defer?
		
	}

	@Then("^my offer details are ([^\"]*)$")
	public void myOfferDetailsAreIn(String containedin) throws Throwable {
		log.info("myOfferDetailsAreIn " + containedin);
		String roughpath = refDir + customer.getOpco() + "/";
		// rebase the parser
		pl.createParser(roughpath, containedin);		
	}

	@And("^I will purchase the secondary offer$")
	public void iWillPurchaseTheOffer() throws Throwable {
		log.info("IWillPurchaseTheOffer");
		UserEntertainment entpage = new UserEntertainment(driver);
		try {
			entpage.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("interrupted page loaded check " + e);
		}
		if (cpp.selectPartnerOffer(customer.getPartner(), entpage)) {
			BasicPartnerOffer offer = new BasicPartnerOffer(driver);
			try {
				offer.bodyLoaded();
			} catch (InterruptedException e) {
				log.error("interrupted page loaded check " + e);
			}
			CheckedScenarioScreenshot();
			offer.setTnC();	
			cpp.verifyOfferText(offer, customer);
			if (cpp.acceptTheOffer(driver, customer)) {
				String urltouse = baseUserUrl + customer.getOpco();	
				ep.initialiseChecks();
				CheckedScenarioScreenshot();
				if (ep.refresh(driver, urltouse, customer)) {
					log.info("Purchase Success");
					CheckedScenarioScreenshot();
				} else ErrorCollector.fail("Could not perform refresh checks correctly");
			} else ErrorCollector.fail("Could not Accept the offer");			
		} else ErrorCollector.fail("Could not select the offer");
			 		 	
	}
}
