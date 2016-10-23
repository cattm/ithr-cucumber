package com.ithr.ppe.bdd.cucumber.steps.interfaces;

import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.base.Customer;
import com.ithr.ppe.bdd.commons.Partners;
import com.ithr.ppe.bdd.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.bdd.cucumber.pages.UserEntertainment;

public interface IPartnerPurchase {
	
	public void initialiseChecks();
	
	public boolean selectPartnerOffer(Partners partner, UserEntertainment entpage);

	public boolean acceptTheOffer(WebDriver driver, Customer customer);
	
	public boolean verifyOfferText(BasicPartnerOffer offer, Customer customer );

	public boolean verifyNextStepsText(BasicPartnerOffer offer);
}
