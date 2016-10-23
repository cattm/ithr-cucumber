package com.ithr.ppe.bdd.base;

import java.util.List;

import org.apache.log4j.Logger;

import com.ithr.ppe.bdd.commons.Partners;
/*
**
* Implements the base model for a Customer
* if will hold the required objects that a customer will use on their journey
*
* @author Marcus Catt (marcus.catt@ithrconsulting.com
*/
import com.ithr.ppe.bdd.cucumber.steps.PurchaseOfferSteps;


public class Customer {
	public static Logger log = Logger.getLogger(Customer.class);

	private String opco = "gb"; //default
	private String subscription = "";
	private String userGroup = "";
	private String shortMsisdn = "";
	private String countryCode = "";
	private Partners myPartner = null;
	private String userName = "";
	private String email = "";
	private List<String> offers = null;
	private List<String> postOffers = null;
	
	private boolean offersValid = false;
	private boolean postOffersValid = false;
	
	
	public Customer () {
		// blank constructor
	}

	private void setCountryCodeFromOpco (String opco) {
		switch (opco.toLowerCase()) {
		case "al" : break;
		case "de" : countryCode = "49"; break;
		case "es" : countryCode = "34"; break;
		case "gb" : countryCode = "44"; break;
		case "gr" : break;
		case "ie" : countryCode = "353"; break;
		case "is" : break;
		case "it" : countryCode = "39"; break;
		case "nl" : countryCode = "31"; break;
		case "nz" : countryCode = "64"; break;
		case "pt" : countryCode = "351"; break;
		case "ro" :
		case "tr" :
		case "za" :
		default : countryCode = "666"; break;
		
		}
	}
	public void printCustomer () {
		log.info("opco is " + opco);
		log.info("subscription is " + subscription);
		log.info("usergroup is    " + userGroup);
		log.info("short msisdn is " + shortMsisdn);
		log.info("country code is " + countryCode);
		log.info("partner is      " + myPartner.toString());
		log.info("username is     " + userName);
		log.info("email is        " + email);
	}
	
	public String getOpco () {
		return this.opco;
	}
	
	public void setOpco (String opco) {
		log.info(opco);
		this.opco = opco;
		setCountryCodeFromOpco(opco);
	}
	
	public String getSubscription () {
		return this.subscription;
	}
	
	public void setSubscription (String subscription) {
		log.info(subscription);
		this.subscription = subscription;
	}
	
	public String getUserGroup () {
		return this.userGroup;
	}
	
	public void setUserGroup (String usergroup) {
		log.info(usergroup);
		this.userGroup = usergroup;
	}
	
	public String getUserName () {
		return this.userName;
	}
	
	public void setUserName (String username) {
		log.info(username);
		this.userName = username;
	}
	
	public String getCountryCode () {
		return this.countryCode;
	}
	
	public void setCountryCode (String code) {
		log.info(code);
		this.countryCode = code;
	}
	
	public String getMsisdn () {
		return this.shortMsisdn;
	}
	
	public void setMsisdn (String msisdn) {
		log.info(msisdn);
		this.shortMsisdn = msisdn;
	}
	
	public String getEmail () {
		return this.email;
	}
	
	public void setEmail (String email) {
		log.info(email);
		this.email = email;
	}
	
	public Partners getPartner () {
		return this.myPartner;
	}
	
	public void setPartner (Partners partner) {
		log.info(partner.toString());
		this.myPartner = partner;
	}
	
	public List<String> getOffers () {
		if (offersValid) {
			return this.offers;
		}
		return null;
	}
	
	public void setOffers (List<String> offers) {
		this.offers = offers;
		offersValid = true;
	}
	
	public void addOffer (String offer) {
		this.offers.add(offer);
	}
	
	public void setPostOffers (List<String> offers) {
		this.postOffers = offers;
		postOffersValid = true;
	}
	
	public List<String> getPostOffers () {
		if (postOffersValid) {
			return this.postOffers;
		}
		return null;
	}
	
	public void addPostOffer (String offer) {
		this.postOffers.add(offer);
	}
	
	public void cleanOffers () {
		this.offers = null;
		this.postOffers = null;
		offersValid = false;
		postOffersValid = false;
	}
	
	
}

