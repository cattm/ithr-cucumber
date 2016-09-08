package com.ithr.ppe.test.cucumber.steps.interfaces;
/**
 * Defines the interface for implementations of the required pre activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;

public interface IProlog {

	public void createParser (String path, String filename);
	public void createChecker(String file, String opco);
	
	public String getPartnerUserName(WebDriver driver, String adminurl, String opco, Partners partner);
	public String getNewMsisdn(WebDriver driver, String opco, String subscription, String usergroup, Partners partner);
	public boolean LoginOk(WebDriver driver, String opco, Partners partner, String msisdn, String pincode, String url);

	public boolean verifyOffersAvailableText(UserEntertainment entpage);
	public boolean verifyPrePurchaseOffers(UserEntertainment entpage);
}
