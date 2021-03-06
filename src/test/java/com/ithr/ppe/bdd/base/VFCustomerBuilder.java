package com.ithr.ppe.bdd.base;

import java.util.List;

import org.apache.log4j.Logger;

import com.ithr.ppe.bdd.commons.Partners;

public class VFCustomerBuilder implements ICustomerBuilder {
	public static Logger log = Logger.getLogger(VFCustomerBuilder.class);
	

    // dummyconstants
	private static final String SUBSCRIPTION = "PK_4GTariff";
	private static final String USERGROUP =  "4gsmall";
	private static final String OPCO = "gb";
	private static final String MSISDN = "7801510596";
	private static final String OPCOOFFSET = "44";
	private static final Partners PARTNER = Partners.NOPARTNER;
	private static final String EMAIL = "testday@ithr.com";
	private static final String USERNAME = "testday2018";
	
	private Customer customer;
	
	public VFCustomerBuilder () {
		this.customer = new Customer();
	}
	
	public VFCustomerBuilder(Customer tocopy) {
		this.customer = tocopy;
	}
	
	public Customer getCustomer() { 
		return this.customer; 
	}
	
	public void Build() { 
		customer.setOpco(OPCO);
		customer.setPartner(PARTNER);		
		customer.setSubscription(SUBSCRIPTION);
		customer.setUserGroup(USERGROUP);
		customer.setMsisdn(MSISDN);
		customer.setCountryCode(OPCOOFFSET);
		customer.setEmail(EMAIL);
		customer.setUserName(USERNAME);
	}
	
	public void addToPreOffers(final String offer) {
		customer.addOffer(offer);
	}
	
	public void addToPostOffers(final String offer) {
		customer.addPostOffer(offer);
	}
	
	public void loadOffers (List<String> preload, List<String> postload) {
		customer.setOffers(preload);
		customer.setPostOffers(postload);
	}
	
	public void updateBuild(String opco, Partners partner) {
		customer.setOpco(opco);
		customer.setPartner(partner);
	}
	
	public void appendToBuild (String Subscription, String usergroup) {
		customer.setSubscription(Subscription);
		customer.setUserGroup(usergroup);
	}
	
	
	
}
