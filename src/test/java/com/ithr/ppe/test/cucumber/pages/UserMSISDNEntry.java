package com.ithr.ppe.test.cucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// TODO: we should check the text flag, copyright and other messages probably as well?

public class UserMSISDNEntry extends PageBase{
	 
	
	  @FindBy(id="subscriberNumber")
	  private WebElement shortMobile;
	  
	  @FindBy(id="nextButton")
	  WebElement nextButton;
	
	  public  UserMSISDNEntry (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	  
	  public void clickNextButton() {
		  nextButton.click();
	  }
	  
	  public void setShortMobile(String mobile) {
		  shortMobile.clear();
		  shortMobile.sendKeys(mobile);	  
	  }
}
