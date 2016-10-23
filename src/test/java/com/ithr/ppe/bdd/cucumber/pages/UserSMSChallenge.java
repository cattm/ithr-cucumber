package com.ithr.ppe.bdd.cucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class UserSMSChallenge extends PageBase{

		
	  @FindBy(id="smsCode")
	  private WebElement smsCode;
	  
	  @FindBy(id="register")
	  WebElement registerButton;
	
	  public  UserSMSChallenge (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	  
	  public void clickRegisterButton() {
		  registerButton.click();
	  }
	  
	  public void setSMS(String code) {
		  smsCode.clear();
		  smsCode.sendKeys(code);
	  }
}
