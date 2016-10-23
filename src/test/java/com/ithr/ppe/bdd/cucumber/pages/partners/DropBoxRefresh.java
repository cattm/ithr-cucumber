package com.ithr.ppe.bdd.cucumber.pages.partners;
/**
 * Implements the basic model of DropBox RefreshPage
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

import com.ithr.ppe.bdd.cucumber.pages.PageBase;

public class DropBoxRefresh extends PageBase{
	public static Logger log = Logger.getLogger(DropBoxRefresh.class);
	
	
	@FindBy(css=".wrapper.section-heading")
	WebElement sectionHeading;
	
	
	@FindBy(css=".btn.event-btn.cf.btn--okButton")
	WebElement okButton;
	
	@FindBy(css="details-content")
	WebElement theDetails;
	

	public DropBoxRefresh(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	public String getHeading () {
		return sectionHeading.getText();
	}
	
	public String getOkText() {
		return okButton.getText();
	}
	
	public String getDetails() {
		return theDetails.getText();
	}
}
