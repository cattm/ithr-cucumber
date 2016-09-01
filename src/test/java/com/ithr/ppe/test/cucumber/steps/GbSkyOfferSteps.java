package com.ithr.ppe.test.cucumber.steps;
/**
 * Original prototype model for the Step implementation 
 * No longer really valid
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.cucumber.pages.AdminHome;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
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

public class GbSkyOfferSteps extends StepBase {
	public static Logger log = Logger.getLogger(GbSkyOfferSteps.class);
	
	
	private String fileToCheck =  "";
	// examples are:
	 // = "Sky bolton v1.json";
	 // = "Sky hardbundle switchable v1.json";
	 // = "Sky standalone v1.json";
	 // = "Sky hardbundle v1.json"
	
	private String shortMsisdn;
	private String opco;
	private String subscription;
	private String userGroup;
	private String checkUrl;
	private JsonParser jsonParse; 
	
	public GbSkyOfferSteps() {
		super();	
	}
	
	@Before("@xdemo")
	public void setUp(Scenario scenario) throws Exception {	
		System.out.println("GbSkyOfferSteps SetUp");
		super.setUp(scenario);
		driver.get(baseAdminUrl);
	}
	
	@After("@xdemo")
	public void tearDown() {
		super.tearDown();
	}

		
    @Given("^I am a \"([^\"]*)\" Customer with a valid msisdn$")
	public void customerWithValidMSISDN(String opco) throws Throwable {
	  log.info("Given :");
	  
	  this.opco = opco;
	}

	@And("^have \"([^\"]*)\" subscription$")
	public void tariffSbscription(String subscription) throws Throwable {
		  log.info("AND :");
		  this.subscription = subscription;
		  
	}

	@And("^am in \"([^\"]*)\" usergroup$")
	public void inUserGroup(String ugroup) throws Throwable {
		  log.info("AND :");
		  this.userGroup = ugroup;
		  AdminHome adminhome = new AdminHome(driver);
		  adminhome.setOpco(opco);
		  adminhome.setSubscription(subscription);
		  adminhome.setUserGroup(userGroup);
		  shortMsisdn = adminhome.getShortMSISDN();
		  log.info("MSISN is : " + shortMsisdn);
		  // can put a check in hear to check the contents of the subscription
		  checkUrl = adminhome.getSubscriptionCheckUrl();		
		  driver.get(checkUrl);
		  //Thread.sleep(5000);
	}

	@Then("^I will see a sky tv off containing text from \"([^\"]*)\"$")
	public void checkOffer(String reffile) throws Throwable {
		fileToCheck = reffile;
		try {
		  log.info("THEN :");
		 
		  //start a private window??
		  //FirefoxProfile firefoxProfile = new FirefoxProfile();    
		  //firefoxProfile.setPreference("browser.private.browsing.autostart",true);
		  //driver = new FirefoxDriver();	      
	     
		  driver.get(baseUserUrl + opco);
		  
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
		  smschallenge.clickRegisterButton();
		  
		  /////////////////////////////////////////////////////////
		  // might be a short wait here....while new page loads...
		  driver.manage().window().setSize(new Dimension(600, 600));
		  //Thread.sleep(5000);
		  
		  // Entertainment page - offer page directly or click on image icon to get text
		  UserEntertainment entpage = new UserEntertainment(driver);
		  entpage.bodyLoaded();
		  //Thread.sleep(5000);
		  
		  // There should be avaialable offers for THIS MSISDN -
		  // the manage subscriptions section should be empty "you have no subscriptions...."
		  Assert.assertTrue(entpage.getSubscriptionText().equals("You have no subscriptions. Please take a look at the available offers."));
		  
		  // TODO: need a better Click than this - then we can make it a general model
		  entpage.clickOfferImage("sky.png");
		  //Thread.sleep(5000);
		  
		  UserSkyOffer skyoffer = new UserSkyOffer(driver);
		  skyoffer.bodyLoaded();
		  skyoffer.setTnC();
		  
		  // check ALL the offer text is correct
		  
		  String displayoffer = skyoffer.getUserOffer();
		  JsonParser jsonParser = JsonParser.getInstance();
		  jsonParser.initialise(refDir + opco + "/" + fileToCheck);
		  String title = jsonParse.getOffersTitle();		  
		  Assert.assertTrue(displayoffer.equals(title));
		  
		  // now check the text from the offers list
		  String offerlist = jsonParse.getOffersText();
		  // need to strip the html to just leave the text - or do we?
		  offerlist = jsonParse.stripHTML(offerlist);
		  log.info("Offer from file: " + offerlist);
		  
		  // interestingly the formatting appears to add a \n into the mix that isn't visible in firebug!
		  String offerdetail = skyoffer.getOfferDetail();
		  String myoffer = StringUtils.replace(offerdetail, "\n", " ");
		  log.info("Offer on page: " + myoffer);
		  Assert.assertTrue(myoffer.equals(offerlist));  
	   }
		  
		
	   catch(Exception e){
				log.info("caught Exception: " + e);
				Assert.fail(); //To fail test in case of any element identification failure		
	   }
	}
	
	@And("^I will be able to accept the offer$")
	public void i_will_be_able_to_accept_the_offer() throws Throwable {
		try {
		  log.info("AND :");
		  // need to accept the offer based on the text!
		  String buttontext = jsonParse.getOffersOkButton();
		  // the button is all upper case!
		  String ucbuttontext = buttontext.toUpperCase();
		  log.info(ucbuttontext);
		  UserSkyOffer skyoffer = new UserSkyOffer(driver);
		  skyoffer.clickAcceptOffer();
		  //Thread.sleep(5000);
		  
		  // check the page displayed
		  String confirmation = skyoffer.getSuccessText();
		  log.info(confirmation);
		  Assert.assertTrue(confirmation.equals("Your entertainment selection has been confirmed"));
		  String checkhappens = jsonParse.getSubscribeSuccessText();
		  checkhappens = jsonParse.stripHTML(checkhappens);
		  log.info("happens next from file: " + checkhappens);
		  
		  // TODO: this is a hardcoded sky check right now
		  String happens = skyoffer.getHappensNextText();
		  String myhappens = StringUtils.replace(happens, "\n", " ");
		  log.info("happens next from page: " + myhappens);
		  // Assert check the text and this will do for now
		  Assert.assertTrue(myhappens.equals(checkhappens));  
		  
		  // then finally there is another page which we can get by clicking additional offer
		  skyoffer.clickAdditionalOffer();
		  Thread.sleep(5000);
	    }

		catch(Exception e){
				log.info("caught Exception: " + e);
				Assert.fail(); //To fail test in case of any element identification failure		
	    }
	}

}
