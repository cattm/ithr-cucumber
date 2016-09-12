package com.ithr.ppe.test.cucumber.steps.interfaces;

import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;

public interface IPartnerPurchase {
	
	public void initialiseChecks();
	
	public boolean selectPartnerOffer(Partners partner, UserEntertainment entpage);

	public boolean acceptTheOffer(WebDriver driver, Customer customer);
	
	public boolean verifyOfferText(BasicPartnerOffer offer, Customer customer );

	public boolean verifyNextStepsText(BasicPartnerOffer offer);
}
