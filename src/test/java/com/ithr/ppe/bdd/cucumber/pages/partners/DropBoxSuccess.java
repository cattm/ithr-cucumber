package com.ithr.ppe.bdd.cucumber.pages.partners;
/**
 * Implements the basic model of DropBox success Page
 * It extends the page model base 
 * It is not really a partner page as its internal to VF
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.bdd.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.bdd.cucumber.pages.PageBase;


public class DropBoxSuccess extends PageBase {
	public static Logger log = Logger.getLogger(DropBoxSuccess.class);
	
	@FindBy(css=".btn.event-btn.cf.btn--customButton")
	WebElement continueButton;
	
	@FindBy(css="div [class='notification-jsx  pulse']")
	WebElement notificationText;
	
	@FindBy(css=".details-content")
	WebElement detailsText;
	 
	public DropBoxSuccess(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	public void clickContinue() {
		continueButton.click();
	}
	
	public String getNotificicationText() {
		return notificationText.getText();
	}
	
	public String getDetailsText() {
		return detailsText.getText();
	}
}
