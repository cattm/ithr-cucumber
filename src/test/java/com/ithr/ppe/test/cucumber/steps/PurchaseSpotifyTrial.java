package com.ithr.ppe.test.cucumber.steps;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.pages.UserSpotifyOffer;
import com.ithr.ppe.test.cucumber.steps.utils.AdminActivities;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.IdentityActivities;
import com.ithr.ppe.test.cucumber.steps.utils.SpotifyActivities;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
// TODO: This is a hack to check opportunities for reuse!

public class PurchaseSpotifyTrial extends StepBase{
	public static Logger log = Logger.getLogger(PurchaseSpotifyTrial.class);

	String userNameToUse;
	
	public PurchaseSpotifyTrial() {
		// return
	}
	@Before("@ignore")
	public void setUp() throws Exception {
		
		super.setUp();
		log.info("SetUp");
	}
	
	@After("@ignore")
	public void tearDown(Scenario scenario) {
		log.info("TearDown");
	
		super.tearDown(scenario);
	}
	
	private boolean PerformBasicSpotifyPurchase (String opco, String mypackage, String usergroup, String contained ) throws Exception {
		// for demo only
		// admin gets the msisdn
		// msisdnFromAdmin(WebDriver driver, String opco, String subscription, String usergroup);
		// login to ppe
		// loginToPPE (WebDriver driver, String msisdn , String pin, String url)
		// drive through accepting the offer - go to spotify using spotify activities
		// RegisterForSpotify (WebDriver driver, String opco, String usernametouse)
		// then check its ok
		// done
		 
		shortMsisdn = AdminActivities.msisdnFromAdmin(driver, opco, subscription, userGroup);		
		userNameToUse = SpotifyActivities.getSpotifyUser(driver, baseSpotifyHelper, opco);
		String url = baseUserUrl + opco;
		IdentityActivities.loginToPPE (driver, shortMsisdn , pinCode, url);
			
		driver.manage().window().setSize(new Dimension(600, 600));		
			  
		// Entertainment page - offer page directly or click on image icon to get text
		UserEntertainment entpage = new UserEntertainment(driver);
		entpage.bodyLoaded();
			 		  
			  // There should be available offers for THIS MSISDN -
			  // if there are no offers this is probably an error
			  // the manage subscriptions section should be empty "you have no subscriptions...."
			 
		if (entpage.checkOfferImage("spotify")) {
			entpage.ClickOfferImage("spotify");
				  
			UserSpotifyOffer spotifyoffer = new UserSpotifyOffer(driver);
			spotifyoffer.bodyLoaded();
			spotifyoffer.setTnC();
				  	
			String buttontext = jsonParse.getOffersOkButton();			
	    	String ucbuttontext = buttontext.toUpperCase();
	    	log.info("Button String is : " +  ucbuttontext);
	    	   
			spotifyoffer.clickAcceptOffer(buttontext);		
	
			// if this is true we are ok we have registered on spotify
			if (SpotifyActivities.RegisterForSpotify(driver, opco, userNameToUse)) {
			
				String confirmation = spotifyoffer.getSuccessText();
								
				driver.get(baseUserUrl + opco);
				entpage.bodyLoaded();
						
				String textfound = "";
				if (entpage.isSpotifySubscriptionTextPresent()) {		
			 		textfound = entpage.getSpotifySubscriptionText();
				}
		
	    		return textChecker.checkSpotifySubscibedText(textfound);
	    	}
		}
		return false;
	}
	
	@Given("^I am a \"([^\"]*)\" customer already purchased Spotify with:$")
	public void AlreadyPurchasedSpotify(String opco, DataTable params) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		log.info("Already Purchased Spotify");
		String thepackage = null;
	    String usergroup  = null;
	    String containedin = null;
		for (Map<String, String> map : params.asMaps(String.class, String.class)) {
	        thepackage = map.get("package");
	        usergroup = map.get("usergroup");
	        containedin = map.get("contained in");
	        log.info("we have :" + thepackage + " and " + usergroup + " and " + containedin);
		}
		CommandExecutor.testCmnd();
		log.info(System.getProperty("user.dir"));
		//PerformBasicSpotifyPurchase(opco, thepackage, usergroup, containedin);
	}

	@Given("^I a have deleted my user in spotify$")
	public void HaveDeletedSpotifyUser() throws Throwable {
		log.info("have deleted Spotify User");
	}

	@When("^I subscribe to spotify with a new msisdn$")
	public void useNewMsisdnToPurchaseSpotify() throws Throwable {
		log.info("New Subscription succeeds");
	}

	@Then("^I will purchase spotify trial$")
	public void Succeed() throws Throwable {
	
	}
}
