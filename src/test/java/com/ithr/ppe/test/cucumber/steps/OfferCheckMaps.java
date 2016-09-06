package com.ithr.ppe.test.cucumber.steps;
/**
 * Implements the cucumber steps required to load up maps of expected valid offers pre and post 
 * the execution of scenarios - expected to be used as background load information
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.ithr.ppe.test.base.Offers;
import com.ithr.ppe.test.base.StepBase;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class OfferCheckMaps {
	public static Logger log = Logger.getLogger(OfferCheckMaps.class);
	private ArrayList<Map<String, String>> startOfferList = new ArrayList<Map<String,String>>();
	private boolean startListLoaded = false;
	private ArrayList<Map<String, String>> endOfferList = new ArrayList<Map<String,String>>();
	private boolean endListLoaded = false;
	private String opco = "";
	
	
	public void printStartMap () {
		Iterator <Map<String,String>> iterator = startOfferList.iterator();
		while (iterator.hasNext()) {
			Map<String,String> element = iterator.next();
			log.info("FROM MAP:  Package is " + element.get("package"));
			log.info("FROM MAP:  usergroup is " + element.get("usergroup"));	
			log.info("FROM MAP:  offers are " + element.get("shouldsee"));	
			String offer = element.get("shouldsee");
			if (offer.contains(",")) {
				String [] tmp = offer.split(",");
				for (int i = 0; i < tmp.length; i++) {
					log.info("offer " + tmp[i]);
				}
			}
		}	
	}
	
	public void printEndMap () {
		Iterator <Map<String,String>> iterator = endOfferList.iterator();
		while (iterator.hasNext()) {
			Map<String,String> element = iterator.next();
			log.info("FROM MAP:  Package is " + element.get("package"));
			log.info("FROM MAP:  usergroup is " + element.get("usergroup"));	
			log.info("FROM MAP:  offers are " + element.get("nowsee"));	
			String offer = element.get("nowsee");
			if (offer.contains(",")) {
				String [] tmp = offer.split(",");
				for (int i = 0; i < tmp.length; i++) {
					log.info("offer " + tmp[i]);
				}
			}
		}	
	}
	
	public String getStartingOffersFor(String subscription, String usergroup) {
		String offer = "None Found";
		boolean found = false;
		Iterator <Map<String,String>> iterator = startOfferList.iterator();
		while (iterator.hasNext() && !found) {
			Map<String,String> element = iterator.next();
			if ( subscription.equals(element.get("package")) && 
				 usergroup.equals(element.get("usergroup")) ) {
				offer = element.get("shouldsee");	
				found = true;
			}		
		}
		return offer;
	}
	
	public String getEndingOffersFor(String subscription, String usergroup) {
		String offer = "None found";
		boolean found = false;
		Iterator <Map<String,String>> iterator = endOfferList.iterator();
		while (iterator.hasNext() && !found) {
			Map<String,String> element = iterator.next();
			if ( subscription.equals(element.get("package")) && 
				 usergroup.equals(element.get("usergroup")) ) {
				offer = element.get("nowsee");	
				found = true;
			}		
		}
		return offer;
	}
	
	@Given("^I am a \"([^\"]*)\" customer with:$")
	public void LoadInitialOffersForThisMsisdn(String opco, List<Map<String, String>> offerlistmap) throws Throwable {
		//   | package     | usergroup | shouldsee           |
		//   | PK_4GTariff | 4glarge   | netflix, nowtv, sky |
		this.opco = opco;		
		
		for (Map<String, String> mymap : offerlistmap) {
			log.info("map is : " + mymap.toString() );
			log.info("package is : " + mymap.get("package"));
			log.info("usergroup is : " + mymap.get("usergroup"));
			log.info("shouldsee is : " + mymap.get("shouldsee"));
			startOfferList.add(mymap);
		}
	    log.info("Loading Initial Offers for this MSISDN");
	   
	    printStartMap();
	    log.info("And we search : " + getStartingOffersFor("PK_4GTariff", "4glarge"));
	
	}

	@When("^I have finished I will have:$")
	public void LoadCompletedPurchaseOfferForThisMsisdn(List<Map<String, String>> mylistmap) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		
		/*
		   | package     | usergroup | nowsee  |
		      | PK_4GTariff | 4glarge   | netflix |
		      | PK_4GTariff | 4glarge   | sky     |
		*/
		for (Map<String, String> mymap : mylistmap) {
			log.info("map is : " + mymap.toString() );
			log.info("package is : " + mymap.get("package"));
			log.info("usergroup is : " + mymap.get("usergroup"));
			log.info("nowsee is : " + mymap.get("nowsee"));
			endOfferList.add(mymap);
		}
		printEndMap();
		log.info("Loading Completed Purchase Offers for this MSISDN");
	}
	
	
}
