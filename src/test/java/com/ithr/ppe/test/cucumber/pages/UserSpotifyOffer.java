package com.ithr.ppe.test.cucumber.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserSpotifyOffer extends PageBase{
	
	public static Logger log = Logger.getLogger(UserSpotifyOffer.class);
	
	// 26/7/2016 - DIT stopped working  
	// identifier changed - This is not Good
	@FindBy(css="div.table-panel-top.main-offer-content div.checkbox-wrap-jsx div.form-el-wrap input.checkbox-jsx")	
	//@FindBy(name="accept")
	private WebElement tnc;
	
	@FindBy(css="div.btn-jsx.cf.main-offer-btn")
	private List <WebElement> acceptOffer;
	
	@FindBy(css="h4.offer-subtitle.bold-font")
	private WebElement theoffer;
	
	@FindBy(css="div.table-panel-top.main-offer-content div.details-content")
	private WebElement thedetail;
	
	@FindBy(css="div.table-panel-top.main-offer-content div.notification-jsx.pulse")
	private WebElement confirmationText;
	
	@FindBy(xpath="/html/body/div/div/article/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[3]")
	private WebElement whatHappensNext;
	
	public UserSpotifyOffer(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	 public void setTnC() {
			tnc.click();
	   }

	public String getUserOffer() {
		return theoffer.getText();
	}
	   
	public String getOfferDetail() {
		return thedetail.getText();
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

	public String getSuccessText() {
		   return confirmationText.getText();
	}

	public String getHappensNextText() {
		   return whatHappensNext.getText();
	}
}