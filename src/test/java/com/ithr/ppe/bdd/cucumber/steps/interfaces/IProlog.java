package com.ithr.ppe.bdd.cucumber.steps.interfaces;
/**
 * Defines the interface for implementations of the required pre activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.base.Customer;
import com.ithr.ppe.bdd.commons.Partners;
import com.ithr.ppe.bdd.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.bdd.cucumber.pages.UserEntertainment;

public interface IProlog {

	public void createParser (String path, String filename);
	public void createChecker(String file, String opco);
	
	public String getPartnerUserName(WebDriver driver, String adminurl, Customer customer);
	public String getNewMsisdn(WebDriver driver, Customer customer);
	public boolean LoginOk(WebDriver driver, Customer customer, String pincode, String url);

	public boolean verifyOffers(UserEntertainment entpage, Customer customer);

}
