package com.ithr.ppe.test.cucumber.steps;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.cucumber.pages.AdminHome;
import com.ithr.ppe.test.cucumber.pages.UserEntertainmentPage;
import com.ithr.ppe.test.cucumber.pages.UserMSISDNEntry;
import com.ithr.ppe.test.cucumber.pages.UserSMSChallenge;
import com.ithr.ppe.test.cucumber.pages.UserSkyOffer;
import com.ithr.ppe.test.cucumber.steps.utils.JsonParser;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PurchaseGBOffersSteps extends StepBase {
	public static Logger log = Logger.getLogger(PurchaseGBOffersSteps.class);
	
	// TODO: need to pick this up from somewhere else
	private static String propertyFile = "test.properties";
	private String fileToCheck =  "";
	private String opco = "gb";
	private String shortMsisdn;
	private String subscription;
	private String userGroup;
	private String checkUrl;
	private JsonParser jsonParse; 	
	private Boolean refFileValid = false;
	private Boolean userGroupValid = false;
	public PurchaseGBOffersSteps() {
		super(propertyFile);
	}
	

	@Before("@xpurchase")
	public void setUp() throws Exception {
	    System.out.println("PurchaseGBOffersSteps SetUp");
		super.setUp();
	}
	
	@After("@xpurchase")
	public void tearDown(Scenario scenario) throws Exception {
		super.tearDown(scenario);
	}
	
	@Given("^I am a GB customer with ([^\"]*)$")
	public void CustomerWithPackage(String mypackage) throws Throwable {
	   log.info("Given: I am a GB customer with " + mypackage);
	   this.subscription = mypackage;
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

			  driver.get(baseUserUrl);
			  
			  // Entry page - MSISDN and PIN challenge
			  UserMSISDNEntry msisdnentry = new UserMSISDNEntry(driver);
			  msisdnentry.elementLoaded(By.id("nextButton"));
			  //msisdnentry.waitClickable(By.id("nextButton"));
			  msisdnentry.setShortMobile(shortMsisdn);
			  msisdnentry.clickNextButton();
			  log.info("Have Set Mobile Number");
			  // SMS Challenge - pin
			  UserSMSChallenge smschallenge = new UserSMSChallenge(driver);
			  smschallenge.setSMS("0000");
			  log.info("Have Set Pin");
			  smschallenge.clickRegistertButton();
			  
			  /////////////////////////////////////////////////////////
			  // might be a short wait here....while new page loads...
			  driver.manage().window().setSize(new Dimension(600, 600));
		
			  
			  // Entertainment page - offer page directly or click on image icon to get text
			  UserEntertainmentPage entpage = new UserEntertainmentPage(driver);
			  entpage.bodyLoaded();
			  
			  
			  // There should be available offers for THIS MSISDN -
			  // the manage subscriptions section should be empty "you have no subscriptions...."
			  Assert.assertTrue(entpage.getSubscriptionText().equals("You have no subscriptions. Please take a look at the available offers."));
			  log.info("selecting offer");
			  
			  // at this point if there is no reference file then we should not try to select offer
			  // because it is probably not there!
			  // TODO: need a better Click than this - then we can make it a general model
			  if (entpage.checkOfferImage("sky.png")) {
				  entpage.ClickOfferImage("sky.png");

			  
				  UserSkyOffer skyoffer = new UserSkyOffer(driver);
				  // TODO A check here o time
				  skyoffer.bodyLoaded();
				  skyoffer.setTnC();
			  
				  String displayoffer = skyoffer.getUserOffer();
				  if (refFileValid) {
					  jsonParse = new JsonParser(refDir + fileToCheck);
					  String title = jsonParse.getOffersTitle();		  
					  Assert.assertTrue(displayoffer.equals(title));
				  }
			  }
			  else {
				  log.info("NO VALID OFFER");
			  }
		}
		catch(Exception e){
			log.info("caught Exception: " + e);
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
			    log.info(ucbuttontext);
			    UserSkyOffer skyoffer = new UserSkyOffer(driver);
				skyoffer.clickAcceptOffer(buttontext);
			  
			  
			  
				// check the page displayed
				String confirmation = skyoffer.getSuccessText();
				log.info(confirmation);
				Assert.assertTrue(confirmation.equals("Your entertainment selection has been confirmed"));
			  
			  
				String happens = skyoffer.getHappensNextText();
				String myhappens = StringUtils.replace(happens, "\n", " ");
				log.info("happens next from page: " + myhappens);
			  
			  
				String checkhappens = jsonParse.getSubscribeSuccessText();
				checkhappens = jsonParse.stripHTML(checkhappens);
				log.info("happens next from file: " + checkhappens);
				// Assert check the text and this will do for now
				Assert.assertTrue(myhappens.equals(checkhappens));
			  
			  
				  // then finally there is another page which we can get by clicking additional offer
				  skyoffer.clickAdditionalOffer();
			  
				  // hold this one just for now on last page
				  Thread.sleep(5000);
			  
			}catch(Exception e){
				log.info("caught Exception: " + e);
				Assert.fail(); //To fail test in case of any element identification failure		
			}
		}
	}
} // end class
