package com.ithr.ppe.test.cucumber.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.steps.GbSkyOfferSteps;

public class UserSkyOffer extends PageBase{
	public static Logger log = Logger.getLogger(UserSkyOffer.class);
	
	// 26/7/2016 - DIT stopped working  
	//29/7/2016 changed back
	// identifier changed - This is not Good
	//@FindBy(css="div.table-panel-top.main-offer-content div.checkbox-wrap-jsx div.form-el-wrap input.checkbox-jsx")
	@FindBy(name="accept")
	private WebElement tnc;
	  	
	@FindBy(css="div.btn-jsx.cf.main-offer-btn")
	private List <WebElement> acceptOffer;
	
	@FindBy(css="h4.offer-subtitle.bold-font")
	private WebElement theoffer;
	
	@FindBy(css="div.table-panel-top.main-offer-content div.details-content")
	private WebElement thedetail;
	
	@FindBy(css="div.table-panel-top.main-offer-content div.notification-jsx.pulse")
	private WebElement confirmationText;
	
	// TODO: make this more intelligent
	@FindBy(xpath="/html/body/div/div/article/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[3]")
	private WebElement whatHappensNext;
	
	// TODO: this is a crap find - PURPLE really!
	@FindBy(css="div.btn-jsx.cf.large-screens.purple a.btn.event-btn.cf.btn-tertiary")
	private WebElement additionalOffer;
	
	
	private List <WebElement> managedSubs;
	
	public  UserSkyOffer (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
// get the text
   public String getUserOffer() {
	   return theoffer.getText();
   }
   
   public String getOfferDetail() {
	   return thedetail.getText();
   }
   
   public String getSuccessText() {
	   return confirmationText.getText();
   }

	  
   public void setTnC() {
		tnc.click();
   }
   
   public String getHappensNextText() {
	   return whatHappensNext.getText();
   }
	
   public void clickAdditionalOffer () {
	   additionalOffer.click();
   }
   
   public void clickAcceptOffer(String buttontext) {
		Iterator <WebElement> iterator = acceptOffer.iterator();
		while (iterator.hasNext()) {
			WebElement element = iterator.next();
			String thetext = element.getText();
			log.info("checking element :" + thetext);
			
			if ( thetext.equalsIgnoreCase(buttontext)) {
				  log.info("Clicking Accept");
				  element.click();	  
			 }
		}
   }
   
   public String getSkyManagedSubscription(String check) {
	   String foundtext = "";
	   Iterator <WebElement> iterator = managedSubs.iterator();
		while (iterator.hasNext()) {
			WebElement element = iterator.next();
			String thetext = element.getText();
			log.debug("checking element :" + thetext);
			
			if ( thetext.equalsIgnoreCase(check)) {
				  foundtext = thetext;				  
			 }
		}
		return foundtext;
   }
		
}
