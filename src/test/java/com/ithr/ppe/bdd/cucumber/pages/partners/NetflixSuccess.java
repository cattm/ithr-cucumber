package com.ithr.ppe.bdd.cucumber.pages.partners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.bdd.cucumber.pages.PageBase;

public class NetflixSuccess extends PageBase {

	
	@FindBy(css="h2[data-reactid='11']")
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
