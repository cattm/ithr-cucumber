package com.ithr.ppe.test.cucumber.pages;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class BasicPartnerCancel extends PageBase {
	public static Logger log = Logger.getLogger(BasicPartnerCancel.class);


	/* This doesnt work but I think it should
	@FindBys({ @FindBy(css="div[class='table-panel-top additional-content']"), 
			   @FindBy(css="div[class='top-availability availability-jsx cf']"),
			   @FindBy(css="div[class='btn-jsx cf main-offer-btn']"),
			   @FindBy(css="a[class='btn event-btn cf btn--okButton']")
	         })	
	*/
	private WebElement cancel1;

	
	@FindBy(css="h4.offer-subtitle.bold-font")
	private WebElement theOffer;
	
	@FindBy(css=".main-offer-content div.details-content")
	private WebElement theOfferDetail;
	
	@FindBy(css=".notification-jsx.pulse>p")
	private WebElement confirmationText;
	
	public  BasicPartnerCancel (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	private WebElement buttonFinder(String tofind) {
		return driver.findElement(By.linkText(tofind));		
	}
	public void clickCancel(String checkstring) {
		buttonFinder(checkstring).click();	
	}
	
	public String getSuccessText() {
		   return confirmationText.getText();
	}
	
	public String getCancelOffer() {
		   return theOffer.getText();
	}
	   
	// get the specifics of the offer
	public String getCancelDetail() {
		   return theOfferDetail.getText();
	}
}

