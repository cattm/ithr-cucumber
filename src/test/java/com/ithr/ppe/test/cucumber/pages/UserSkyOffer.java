package com.ithr.ppe.test.cucumber.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.steps.GbSkyOfferSteps;

public class UserSkyOffer extends PageBase{
	public static Logger log = Logger.getLogger(UserSkyOffer.class);
	
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
	
	// TODO: 
	// make this more efficient - dont want to parse a list - use findbys? multiple finby or xpath - yuck
	// //html/body/div/div/article/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[3]
	// //html/body/div/div/article/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[3]
	//html body.gb.js div#content.cf div#layout-wrapper.success.layout-jsx.app article#content__wrapper.gb-sky-hardbundle.sky.offer-jsx.success-jsx.cf div.wrapper.always-show.cf.can-be-closed div#gb-sky-hardbundle.offer-details-jsx.can-be-closed.offer__module.success__page div.offer-panel.rounded.cf.showing div.panel-inner-pad div.all-panels.cf div.table-panel-top.main-offer-content div.details-content	
	//html body.gb.js div#content.cf div#layout-wrapper.success.layout-jsx.app article#content__wrapper.gb-sky-hardbundle.sky.offer-jsx.success-jsx.cf div.wrapper.always-show.cf.can-be-closed div#gb-sky-hardbundle.offer-details-jsx.can-be-closed.offer__module.success__page div.offer-panel.rounded.cf.showing div.panel-inner-pad div.all-panels.cf div.table-panel-top.main-offer-content	
	//html body.gb.js div#content.cf div#layout-wrapper.success.layout-jsx.app article#content__wrapper.gb-sky-hardbundle.sky.offer-jsx.success-jsx.cf div.wrapper.always-show.cf.can-be-closed div#gb-sky-hardbundle.offer-details-jsx.can-be-closed.offer__module.success__page div.offer-panel.rounded.cf.showing div.panel-inner-pad div.all-panels.cf div.table-panel-top.main-offer-content div.details-content p.content_heading
	//html body.gb.js div#content.cf div#layout-wrapper.success.layout-jsx.app article#content__wrapper.gb-sky-hardbundle.sky.offer-jsx.success-jsx.cf div.wrapper.always-show.cf.can-be-closed div#gb-sky-hardbundle.offer-details-jsx.can-be-closed.offer__module.success__page div.offer-panel.rounded.cf.showing div.panel-inner-pad div.all-panels.cf div.table-panel-top.main-offer-content div.details-content p
	//@FindBy(css="div#gb-sky-standalone.offer-details-jsx.can-be-closed.offer__module.success__page div.offer-panel.rounded.cf.showing div.panel-inner-pad div.all-panels.cf div.table-panel-top.main-offer-content div.details-content")
	//@FindBy(css="div#gb-sky-hardbundle.offer-details-jsx.can-be-closed.offer__module.success__page div.offer-panel.rounded.cf.showing div.panel-inner-pad div.all-panels.cf div.table-panel-top.main-offer-content div.details-content")
	@FindBy(xpath="/html/body/div/div/article/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[3]")
	private WebElement whatHappensNext;
	
	// this is a crap find - PURPLE really!
	@FindBy(css="div.btn-jsx.cf.large-screens.purple a.btn.event-btn.cf.btn-tertiary")
	private WebElement additionalOffer;
	
	// odder confirm 
	// css div.table-panel-top.main-offer-content div.notification-jsx.pulse p
	// list of 3 - div.table-panel-top.main-offer-content div.details-content
	
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
			log.debug("checking element :" + thetext);
			
			if ( thetext.equalsIgnoreCase(buttontext)) {
				  log.debug("Clicking Accept");
				  element.click();	  
			 }
		}
   }
		
}
