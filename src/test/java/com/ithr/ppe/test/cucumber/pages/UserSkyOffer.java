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
	  	
	
	// Note - this find will return a single item if the display is set to the correct size
	// if it is a full size browser it will return a list of items to iterate through
	//@FindBy(css="div.btn-jsx.cf.main-offer-btn")
	@FindBy(css="div [class='table-panel-top main-offer-content'] a[class='btn event-btn cf btn--okButton']")
	private WebElement acceptOffer;
	
	@FindBy(css="h4.offer-subtitle.bold-font")
	private WebElement theoffer;
	
	@FindBy(css="div.table-panel-top.main-offer-content div.details-content")
	private WebElement thedetail;
	
	@FindBy(css="div.table-panel-top.main-offer-content div.notification-jsx.pulse")
	private WebElement confirmationText;
	
	// TODO: When Matt has adjusted the page constructs sort the finders
	// These are horrible
	@FindBy(xpath="/html/body/div/div/article/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[3]")
	private WebElement whatHappensNext;
	
	// TODO: this is a crap find - PURPLE really!! you should be ashamed
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
   
   // TODO: temporary method - until I sort out correct model to use to identify this button
   // And perform a check on the text being correct at the same time
   // this method returns two buttons - they both accept the offer!
   public void clickAcceptOffer () {
	   //div [class="table-panel-top main-offer-content"] a[class="btn event-btn cf btn--okButton"]
	   acceptOffer.click();

   }
   
   public String getAcceptOfferText () {
	   return acceptOffer.getText();
   }
   /* TODO: remove
   public boolean clickAcceptOffer(String buttontext) {
		Iterator <WebElement> iterator = acceptOffer.iterator();
		while (iterator.hasNext()) {
			WebElement element = iterator.next();
			String thetext = element.getText();
			log.info("checking element :" + thetext);
			
			if ( thetext.equalsIgnoreCase(buttontext)) {
				  log.info("Clicking Accept");
				  element.click();	  
				  return true;
			 }
		}
		return false;
   }
   */
   public String getSkyManagedSubscription(String check) {
	   String foundtext = "";
	   Iterator <WebElement> iterator = managedSubs.iterator();
		while (iterator.hasNext()) {
			WebElement element = iterator.next();
			String thetext = element.getText();
			log.debug("checking element :" + thetext);
			
			if ( thetext.equalsIgnoreCase(check)) {
				  return foundtext = thetext;				  
			 }
		}
		return foundtext;
   }
		
}
