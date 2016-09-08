package com.ithr.ppe.test.cucumber.steps;
/**
 * Implements the cucumber steps required to load up maps of expected valid offers pre and post 
 * the execution of scenarios - expected to be used as background load information
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ithr.ppe.test.cucumber.steps.utils.OfferMap;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class OfferCheckMaps {
	public static Logger log = Logger.getLogger(OfferCheckMaps.class);
	private String opco = "";
	private OfferMap om = OfferMap.getInstance();
	

	@Given("^I am a \"([^\"]*)\" customer with:$")
	public void LoadInitialOffersForThisMsisdn(String opco, List<Map<String, String>> offerlistmap) throws Throwable {
		this.opco = opco;		
		log.info("Given I am a " + opco + " customer with offers");
		om.loadStartMap(offerlistmap);
	    log.info("Finished Loading Initial Offers for this opco");	   
	    om.printStartMap();
	
	}

	@When("^I have finished I will have:$")
	public void LoadCompletedPurchaseOfferForThisMsisdn(List<Map<String, String>> mylistmap) throws Throwable {
		log.info("when I have finished I will have different offers");
		om.loadEndMap(mylistmap);
		log.info("Loading Completed Purchase Offers for this opco");
		om.printEndMap();

	}
	
	
}
