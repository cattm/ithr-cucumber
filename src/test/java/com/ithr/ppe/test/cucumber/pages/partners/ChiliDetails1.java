package com.ithr.ppe.test.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class ChiliDetails1 extends PageBase {
	public static Logger log = Logger.getLogger(ChiliDetails1.class);
	
	@FindBy(id="registerOK")
	WebElement carryOnButton;
	
	@FindBy(id="useremail")
	WebElement email;
	
	@FindBy(id="emailConfirm")
	WebElement emailConfirm;
	
	@FindBy(id="password")
	WebElement password;
			
	@FindBy(id="acceptCondition")
	WebElement acceptConditions;
	
	public ChiliDetails1(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	public void setEmail(String mainemail) {
		 email.clear();
		 email.sendKeys(mainemail);
	}
	
	public void setEmailConfirm(String email) {
		emailConfirm.clear();
		emailConfirm.sendKeys(email);
	}
	
	public void setPassword(String pw) {
		password.clear();
		password.sendKeys(pw);
	}
	
	public void clickTerms() {
		acceptConditions.click();
	}
	
	public void clickCarryOn() {
		carryOnButton.click();
	}

}
