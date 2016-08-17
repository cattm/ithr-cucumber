package com.ithr.ppe.test.cucumber.steps.utils;

import org.openqa.selenium.WebDriver;


public interface PartnerInterface {
	public boolean registerPartner(WebDriver driver, String opco, String usernametouse);
	public boolean loginToPartner (WebDriver driver, String opco, String usernametouse);
	public String terminatePartnerUser(WebDriver driver, String baseurl, String opco, String username);
	public String getPartnerUserStatus(WebDriver driver, String baseurl, String opco, String username);
	public String getPartnerUser (WebDriver driver, String baseurl, String opco);	
}
