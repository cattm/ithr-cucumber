package com.ithr.ppe.bdd.base;

import java.util.List;

import com.ithr.ppe.bdd.commons.Partners;

public interface ICustomerBuilder {
	
	public void Build();
	
	public Customer getCustomer();

	public void addToPreOffers(final String offer);
	
	public void addToPostOffers(final String offer);
	
	public void loadOffers (List<String> preload, List<String> postload);
	
	public void updateBuild(String opco, Partners partner);
	
	public void appendToBuild (String Subscription, String usergroup);
	
	
}
