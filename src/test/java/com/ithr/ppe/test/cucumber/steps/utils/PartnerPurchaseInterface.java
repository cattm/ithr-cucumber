package com.ithr.ppe.test.cucumber.steps.utils;

import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;

public interface PartnerPurchaseInterface {
	public void SetAssertCheck();
	
	public void LocateJsonParseFile(String path, String filename);
	
	public void DefineCheckerToUse(String file, String opco);
	
	public String GetPartnerUserName(WebDriver driver, String adminurl, String opco);

	public boolean AcceptTheOffer(WebDriver driver, String opco, Partners partner);

	public boolean RefreshPPE(WebDriver driver, String baseopcourl);

	public boolean VerifyOfferText(BasicPartnerOffer offer );

	public boolean VerifyAvailableOffersText(UserEntertainment entpage);

	public boolean VerifyNextStepsText(BasicPartnerOffer offer);
}
