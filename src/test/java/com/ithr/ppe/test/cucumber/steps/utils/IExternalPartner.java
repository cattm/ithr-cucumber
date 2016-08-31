package com.ithr.ppe.test.cucumber.steps.utils;

import org.openqa.selenium.WebDriver;


public interface IExternalPartner {
	public boolean register(WebDriver driver, String opco, String usernametouse);
	public boolean login (WebDriver driver, String opco, String usernametouse);
	public String terminateUser(WebDriver driver, String baseurl, String opco, String username);
	public String getUserStatus(WebDriver driver, String baseurl, String opco, String username);
	public String getUser (WebDriver driver, String baseurl, String opco);	
}
