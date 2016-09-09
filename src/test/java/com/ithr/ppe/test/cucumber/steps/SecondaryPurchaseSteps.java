package com.ithr.ppe.test.cucumber.steps;



import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.base.ICustomerBuilder;
import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.base.VFCustomerBuilder;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.interfaces.IEpilog;
import com.ithr.ppe.test.cucumber.steps.interfaces.IPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.interfaces.IProlog;
import com.ithr.ppe.test.cucumber.steps.utils.CommonEpilog;
import com.ithr.ppe.test.cucumber.steps.utils.CommonPartnerPurchase;
import com.ithr.ppe.test.cucumber.steps.utils.CommonProlog;

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
		bd.updateBuild(opco, Partners.valueOf(partner.toUpperCase()));		    	   
		bd.appendToBuild(sub, group);
		customer = bd.getCustomer();	
		pl.createChecker(testReferenceDir, opco);
		String roughpath = refDir + customer.getOpco() + "/";
		pl.createParser(roughpath, file);
		
		customer.setUserName(pl.getPartnerUserName(driver, baseAdminUrl, customer));
		customer.setMsisdn(pl.getNewMsisdn(driver, customer));
		pl.LoginOk(driver, customer, pinCode, baseUserUrl);
		
		return true;
	}
	
	private boolean performInitialPurchase() {
		cpp.initialiseChecks();
		UserEntertainment entpage = new UserEntertainment(driver);
		cpp.selectPartnerOffer(customer.getPartner(), entpage);
		cpp.acceptTheOffer(driver, customer);
		return true;		
	}
	
	private boolean checkInitialPurchaseOutcome() {
		String urltouse = baseUserUrl + customer.getOpco();	
		ep.initialiseChecks();
		ep.refresh(driver, urltouse, customer);
		return true;
	}
	
	@Given("^I am a \"([^\"]*)\" customer Who Initially purchases \"([^\"]*)\" offer:$")
	public void aCustomerIntialPurchase(String opco, String partner, List<Map<String, String>> details) throws Throwable {
		log.info("aCustomerInitialPurchase");
		boolean prologok = performInitialProlog(opco, partner, details);
		boolean purchaseok = performInitialPurchase();
		boolean postcheckok = checkInitialPurchaseOutcome();
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
		// just check we can see the required offer
		// if not fail the test
	}

	@Then("^my offer details are ([^\"]*)$")
	public void myOfferDetailsAreIn(String containedin) throws Throwable {
		log.info("myOfferDetailsAreIn " + containedin);
		// find the new definitions file
		// if not there - fail the test
		
	}

	@And("^I will purchase the secondary offer$")
	public void iWillPurchaseTheOffer() throws Throwable {
		log.info("IWillPurchaseTheOffer");
		// perform the purchase with validation and post steps
		// any problem at any stage fail the test	
	}
}
