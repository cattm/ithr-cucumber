package com.ithr.ppe.test.cucumber.pages.partners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class NetflixSuccess extends PageBase {

	// probably a success page
	// Your Netflix membership, which begins with a free trial, has begun.
	// h2[data-reactid="12"]
	
	@FindBy(css="h2[data-reactid='12']")
	WebElement checkText;
	
	
	
	public NetflixSuccess (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	}
	
	public String getNetflixSuccess () {
		 String ret =  checkText.getText();	 
		 return ret;
	}
	 
}
