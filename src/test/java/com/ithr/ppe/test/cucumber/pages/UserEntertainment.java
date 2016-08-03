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

// There will be offers and subscriptions
// initially there will be no subscriptions - we hope! And this we should check
public class UserEntertainment extends PageBase{
	 public static Logger log = Logger.getLogger(UserEntertainment.class);
	
		
	  @FindBy(css="div.panel-inner-full.fl img.logo")
	  private List<WebElement> offerIcons;
	  
	  @FindBy(xpath="/html/body/div/div/header/div[2]/p")
	  private WebElement msisdn;
	  
	  // TODO: experiments for phantomJS
	  //@FindBy(css="div.wrapper.subscriptions-wrapper")
	  //@FindBy(xpath="//*[@id='content__wrapper']/div[2]/div/div/p")
	  @FindBy(css="p.nothing-to-display")
	  private WebElement subscriptionText;
	   
	  @FindBy(xpath="//article[@id='content__wrapper']/div[2]/div/div/div/div/div/div[2]/h3/span[2]") 
	  private WebElement manageSubscription; 
	  
	  @FindBy(xpath="//article[@id='content__wrapper']/div[2]/h2")
	  private WebElement manage;
	  
	  public  UserEntertainment (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	
	  
	  public void ClickOfferImage(String offer) {
		  //iterate through the list and click on it
		  Iterator <WebElement> iterator = offerIcons.iterator();
		  while (iterator.hasNext()) {
			  WebElement element = iterator.next();
			  String thetext = element.getAttribute("src");
			  log.info("checking element :" + thetext);
			  if ( thetext.contains(offer)) {
				  log.info("Clicking offer");
				  element.click();
				  return;
			  }
		  }
		  
	  }
	  
	  public boolean checkOfferImage(String offer) {
		  Iterator <WebElement> iterator = offerIcons.iterator();
		  Boolean valid = false;
		  while (iterator.hasNext()) {
			  WebElement element = iterator.next();
			  String thetext = element.getAttribute("src");
			  log.debug("checking element :" + thetext);
			  if ( thetext.contains(offer)) {
				  log.info("Offer is present");
				  valid = true;	  
			  }
		  }
		  return valid;
	  }
	  public String getMSISDN () {
		  return msisdn.getText();
		  
	  }
	  
	  public String getSubscriptionText() {
		  return subscriptionText.getText();
	  }
	  
	  public String getManageText() {
		  return manage.getText();
	  }
	  
	  public String getSkySubscriptionText() {
		  return manageSubscription.getText();
	  }
	  
	  public String getSpotifySubscriptionText() {
		  return manageSubscription.getText();
	  }
	  public boolean isSpotifySubscriptionTextPresent() throws InterruptedException {	  
		return elementLoaded(By.xpath("//article[@id='content__wrapper']/div[2]/div/div/div/div/div/div[2]/h3/span[2]"));		
	  }
	  
}
