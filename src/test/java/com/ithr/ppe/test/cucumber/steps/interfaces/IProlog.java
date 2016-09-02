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

	public void createJsonParserFromFile (String path, String filename);
	public void createCheckerToUse(String file, String opco);
	public String getPartnerUserName(WebDriver driver, String adminurl, String opco, Partners partner);
	public boolean verifyOffersAvailableText(UserEntertainment entpage);
	public boolean verifyPrePurchaseOffers(UserEntertainment entpage);
}
