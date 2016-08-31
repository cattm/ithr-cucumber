package com.ithr.ppe.test.cucumber.steps.utils;

import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;

public interface IPartnerPurchase {

	
	public void locateJsonParseFile(String path, String filename);
	
	public void defineCheckerToUse(String file, String opco);
	
	public String getPartnerUserName(WebDriver driver, String adminurl, String opco, Partners partner);
	
	public boolean validatePrePurchaseOffers(UserEntertainment entpage);
	
	public boolean validatePostPurchaseOffers(UserEntertainment entpage);
	
	public boolean selectPartnerOffer(Partners partner, UserEntertainment entpage);

	public boolean acceptTheOffer(WebDriver driver, String opco, Partners partner);

	public boolean refreshPPE(WebDriver driver, String baseopcourl);

	public boolean verifyOfferText(BasicPartnerOffer offer );

	public boolean verifyAvailableOffersText(UserEntertainment entpage);

	public boolean verifyNextStepsText(BasicPartnerOffer offer);
}
