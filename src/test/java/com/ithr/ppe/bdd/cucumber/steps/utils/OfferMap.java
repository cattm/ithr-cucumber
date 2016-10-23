package com.ithr.ppe.bdd.cucumber.steps.utils;
/**
 * Implements the class to hold the pre and post offers 
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ithr.ppe.bdd.cucumber.steps.OfferCheckMaps;

public class OfferMap {
	private static OfferMap instance = null;
	private static final Exception NotFoundException = new Exception("Search item not found");
	public static Logger log = Logger.getLogger(OfferMap.class);
	private ArrayList<Map<String, String>> startOfferList = new ArrayList<Map<String,String>>();
	private boolean startListLoaded = false;
	private ArrayList<Map<String, String>> endOfferList = new ArrayList<Map<String,String>>();
	private boolean endListLoaded = false;
	
	protected OfferMap () {
	
	}
	
	public static OfferMap getInstance () {
		if (instance == null) {
	         instance = new OfferMap();
	      }
	      return instance;
	}
	
	public void empty () {
		startOfferList.clear();
		endOfferList.clear();
		endListLoaded = false;
		startListLoaded = false;
	}
	
	public void printStartMap () {
		Iterator <Map<String,String>> iterator = startOfferList.iterator();
		while (iterator.hasNext()) {
			Map<String,String> element = iterator.next();
			log.info("FROM MAP:  Package is " + element.get("package"));
			log.info("FROM MAP:  usergroup is " + element.get("usergroup"));	
			log.info("FROM MAP:  offers are " + element.get("ishouldsee"));	
			String offer = element.get("ishouldsee");
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
			log.info("FROM MAP:  offers are " + element.get("iwillnowsee"));	
			String offer = element.get("iwillnowsee");
			if (offer.contains(",")) {
				String [] tmp = offer.split(",");
				for (int i = 0; i < tmp.length; i++) {
					log.info("offer " + tmp[i]);
				}
			}
		}	
	}
	
	public String getStartingOffersFor(String subscription, String usergroup) throws Exception {
		String offer = "None Found";
		boolean found = false;
		Iterator <Map<String,String>> iterator = startOfferList.iterator();
		while (iterator.hasNext() && !found) {
			Map<String,String> element = iterator.next();
			if ( subscription.equals(element.get("package")) && 
				 usergroup.equals(element.get("usergroup")) ) {
				offer = element.get("ishouldsee");	
				found = true;
			}		
		}
		log.info("Offer found is " + offer);
		if (!found) throw NotFoundException; 
		return offer;
	}
	
	public String getEndingOffersFor(String subscription, String usergroup) throws Exception {
		String offer = "None found";
		boolean found = false;
		Iterator <Map<String,String>> iterator = endOfferList.iterator();
		while (iterator.hasNext() && !found) {
			Map<String,String> element = iterator.next();
			if ( subscription.equals(element.get("package")) && 
				 usergroup.equals(element.get("usergroup")) ) {
				offer = element.get("iwillnowsee");	
				found = true;
			}		
		}
		log.info("Offer found is " + offer);
		if (!found) throw NotFoundException; 
		return offer;
	}
	
	public String [] SplitOffers(String offer) {
		String [] tmp = null;
		if (offer.contains(", ")) {
			tmp = offer.split(", ");
			for (int i = 0; i < tmp.length; i++) {
				log.info("offer " + tmp[i]);
			}
		} else {
			tmp = new String [1];
			tmp[0] = offer;
		}
		return tmp;
	}
	
	public void loadStartMap(List<Map<String, String>> offerlistmap) {
		for (Map<String, String> mymap : offerlistmap) {
			log.debug("map is : " + mymap.toString() );
			log.debug("package is : " + mymap.get("package"));
			log.debug("usergroup is : " + mymap.get("usergroup"));
			log.debug("shouldsee is : " + mymap.get("ishouldsee"));
			startOfferList.add(mymap);
		}
		startListLoaded = true;
	}
	
	public void loadEndMap(List<Map<String, String>> mylistmap) {
		for (Map<String, String> mymap : mylistmap) {
			log.debug("map is : " + mymap.toString() );
			log.debug("package is : " + mymap.get("package"));
			log.debug("usergroup is : " + mymap.get("usergroup"));
			log.debug("nowsee is : " + mymap.get("iwillnowsee"));
			endOfferList.add(mymap);
		}
		endListLoaded = true;
	}

	public boolean getStartListLoaded() {
		return startListLoaded;

	}
	public boolean getEndListLoaded() {
		return endListLoaded;

	}
	
}
