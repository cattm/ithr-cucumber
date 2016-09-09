package com.ithr.ppe.test.base;

import java.util.List;

import com.ithr.ppe.test.commons.Partners;
// TODO: build the customer and allow everyone to use this instead of some many different params

public interface ICustomerBuilder {
	
	public void Build();
	
	public Customer getCustomer();

	public void addToPreOffers(final String offer);
	
	public void addToPostOffers(final String offer);
	
	public void loadOffers (List<String> preload, List<String> postload);
	
	public void updateBuild(String opco, Partners partner);
	
	public void appendToBuild (String Subscription, String usergroup);
	
	
}
