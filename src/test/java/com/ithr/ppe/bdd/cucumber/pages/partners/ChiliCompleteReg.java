package com.ithr.ppe.bdd.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.bdd.cucumber.pages.PageBase;

public class ChiliCompleteReg extends PageBase {
	public static Logger log = Logger.getLogger(ChiliCompleteReg.class);
	
	@FindBy(linkText= "ATTIVA")
	//@FindBy(css="div a[href='/registration']")
	WebElement registerButton;
	
	@FindBy(id="first-item")
	WebElement someButton;
			
	public ChiliCompleteReg(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	public void clickContinue() {
		registerButton.click();
	}
	
	public void clickSomeButton() {
		someButton.click();
	}
	

}
