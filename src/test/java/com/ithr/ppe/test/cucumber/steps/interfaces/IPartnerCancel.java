package com.ithr.ppe.test.cucumber.steps.interfaces;

import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerCancel;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;

public interface IPartnerCancel {
	public void initialiseChecks();
	
	public boolean selectPartnerToCancel(String definedby, UserEntertainment entpage);

	public boolean cancelTheOffer(WebDriver driver, Customer customer);
	
	public boolean verifyCancelText(BasicPartnerCancel cancel, Customer customer );

	public boolean verifySuccessText(WebDriver driver);

}
