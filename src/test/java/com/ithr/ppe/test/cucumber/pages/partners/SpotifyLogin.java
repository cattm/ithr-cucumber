package com.ithr.ppe.test.cucumber.pages.partners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class SpotifyLogin extends PageBase {

	@FindBy(id="login-username")
	WebElement userName;
	
	@FindBy(id="login-password")
	WebElement password;
	
	@FindBy(css=".btn.btn-sm.btn-block.btn-green.ng-binding")
	WebElement submit;
	
	public  SpotifyLogin (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	}
		
	public void setUser(String user) {
		  userName.clear();
		  userName.sendKeys(user);
	}
	 
	public void setPassword(String user) {
		  password.clear();
		  password.sendKeys(user);
	}
	
	public void clickSubmit() {	
		submit.click();
	}	
		
}
