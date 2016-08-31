package com.ithr.ppe.test.base;

import com.ithr.ppe.test.commons.Partners;

public class VFCustomerBuilder implements ICustomerBuilder {
    // dummyconstants
	private static final String SUBSCRIPTION = "PK_4GTariff";
	private static final String USERGROUP =  "4gsmall";
	private static final String OPCO = "gb";
	private static final String MSISDN = "7801510596";
	private static final Partners PARTNER = Partners.SKY;
	private static final String EMAIL = "testday@ithr.com";
	private static final String USERNAME = "testday2018";
	
	private Customer customer;
	
	public VFCustomerBuilder () {
		this.customer = new Customer();
	}
	
	public VFCustomerBuilder(Customer tocopy) {
		this.customer = tocopy;
	}
	
	public void Build() { 
		customer.setOpco(OPCO);
		customer.setPartner(PARTNER);		
		customer.setSubscription(SUBSCRIPTION);
		customer.setUserGroup(USERGROUP);
		customer.setMsisdn(MSISDN);
		customer.setEmail(EMAIL);
		customer.setUserName(USERNAME);
	}
	
	public void startBuild(String opco, Partners partner) {
		customer.setOpco(opco);
		customer.setPartner(partner);
	}
	
	public void appendToBuild (String Subscription, String usergroup) {
		customer.setSubscription(Subscription);
		customer.setUserGroup(usergroup);
	}
	
	
	public Customer getCustomer() { 
		return this.customer; 
	}
	
	
	
}
