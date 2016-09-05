package com.ithr.ppe.test.base;

import java.util.List;

import org.apache.log4j.Logger;

import com.ithr.ppe.test.commons.Partners;
/*
**
* Implements the base model for a Customer
* if will hold the required objects that a customer will use on their journey
*
* @author Marcus Catt (marcus.catt@ithrconsulting.com
*/
import com.ithr.ppe.test.cucumber.steps.PurchaseOfferSteps;


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
	
	private boolean offersValid = false;
	
	
	public Customer () {
		// blank constructor
	}
	
	public String getOpco () {
		return this.opco;
	}
	
	public void setOpco (String opco) {
		this.opco = opco;
	}
	
	String getSubscription () {
		return this.subscription;
	}
	
	public void setSubscription (String subscription) {
		this.subscription = subscription;
	}
	
	String getUserGroup () {
		return this.userGroup;
	}
	
	public void setUserGroup (String usergroup) {
		this.userGroup = usergroup;
	}
	
	String getUserName () {
		return this.userName;
	}
	
	public void setUserName (String username) {
		this.userName = username;
	}
	
	String getCountryCode () {
		return this.shortMsisdn;
	}
	
	public void setCountryCode (String msisdn) {
		this.shortMsisdn = msisdn;
	}
	
	String getMsisdn () {
		return this.shortMsisdn;
	}
	
	public void setMsisdn (String msisdn) {
		this.shortMsisdn = msisdn;
	}
	
	String getEmail () {
		return this.email;
	}
	
	public void setEmail (String email) {
		this.email = email;
	}
	
	Partners getPartner () {
		return this.myPartner;
	}
	
	public void setPartner (Partners partner) {
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
	
	public void cleanOffers () {
		this.offers = null;
		offersValid = false;
	}
	
	
}

