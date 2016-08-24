package com.ithr.ppe.test.cucumber.pages.partners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class NetflixLoginOrRegister extends PageBase {

	// input[name="email"]
	// input[name="password"]
	// button[id="CC-startPaid"]

	@FindBy(name="email")
	WebElement userEmail;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(id="CC-startPaid")
	WebElement carryOn;
	
	public  NetflixLoginOrRegister (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	}
	
	public void setEmail(String email) {
		  userEmail.clear();
		  userEmail.sendKeys(email);
	}
	 
	public void setPassword(String pw) {
		  password.clear();
		  password.sendKeys(pw);
	}
	
	public void clickContinue() {	
		carryOn.click();
	}
}
