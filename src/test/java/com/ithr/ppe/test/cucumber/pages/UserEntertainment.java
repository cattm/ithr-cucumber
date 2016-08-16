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
	  
	  @FindBy(css="p[class*='phone-number']")
	  private WebElement msisdn;
	  
	  @FindBy(css="p.nothing-to-display")
	  private WebElement subscriptionText;
	    
	  // TODO: - This is horrible
	  @FindBy(xpath="//article[@id='content__wrapper']/div[2]/div/div/div/div/div/div[2]/h3/span[2]") 
	  private WebElement manageSubscription; 
	  
	// TODO: - This is horrible
	  @FindBy(xpath="//article[@id='content__wrapper']/div[2]/h2")
	  private WebElement manage;
	  
	  public  UserEntertainment (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	
	  
	  public void clickOfferImage(String offer) throws InterruptedException {
		  //iterate through the list and click on it
		  Iterator <WebElement> iterator = offerIcons.iterator();
		  while (iterator.hasNext()) {
			  WebElement element = iterator.next();
			  String thetext = element.getAttribute("src");
			  log.info("checking element :" + thetext);
			  // The offer has to be in the page and displayed to be click-able
			  // TODO: write a safer model than this - it needs a serious refactor and should not be checking the image anyway
			  if ( thetext.contains(offer)) {
				  while (!element.isDisplayed()) {
					  Thread.sleep(SLOW);
					  log.info("Not displayed yet");
				  }
				  log.info("Click Element");
				  element.click();
				  return;
			  }
		  }
		  
	  }
	  
	  public boolean checkOfferImagePresent(String offer) {
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
	  
	  @SuppressWarnings("null")
	public List <String> getOffersPresent() {
		  Iterator <WebElement> iterator = offerIcons.iterator();
		  List <String> result = null;
		  
		  while (iterator.hasNext()) {
			  WebElement element = iterator.next();
			  String thetext = element.getAttribute("src");
			  log.debug("checking element :" + thetext);
			  if (!(thetext == "" || thetext == null) ) {
				  result.add(thetext);  
			  }	 
		  }
		  return result;
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
	  
	  
	  // TODO: see if we can make these VERY specific methods generic and sort the finders
	  public String getManageSubscriptionText() {
		  return manageSubscription.getText();
	  }
	  
	  public boolean isManageSubscriptionTextPresent() throws InterruptedException {
		return elementLoaded(By.xpath("//article[@id='content__wrapper']/div[2]/div/div/div/div/div/div[2]/h3/span[2]"));		
	  }
	  
}
