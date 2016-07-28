package com.ithr.ppe.test.cucumber.steps;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ithr.ppe.test.base.StepBase;

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
	
	private void PerformBasicSpotifyPurchase (String opco, String mypackage, String usergroup, String contained ) throws Exception {
		// for demo only
		// admin gets the msisdn
		// login to ppe
		// drive through accepting the offer - go to spotify using spotify activities
		// then check its ok
		// done
		
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
		
		PerformBasicSpotifyPurchase(opco, thepackage, usergroup, containedin);
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
