package com.ithr.ppe.test.cucumber.pages;

/**
 * Implements the Page Object Model of the Entertainment page
 * This page will only provide methods to interact with the page
 * It will not perform any tests 
 * It expects exceptions to be handled by the calling method
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.commons.CommonConstants;

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
	  
	  @FindBy(css="div[class*='wrapper purchased__wrapper']")
	  private WebElement managedOffers;
	  
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
					  Thread.sleep(CommonConstants.SLOW);
					  log.info("Not displayed yet");
				  }
				  log.info("Click Element " + thetext);
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
	  
	  public boolean clickOfferToManage(String searchingfor) {
		  String tofind = "div [class*='" + searchingfor + "']"; 
		  log.info("offer to manage - finding " + tofind);
		  WebElement offer;
		  try {
			  offer = managedOffers.findElement(By.cssSelector(tofind));
		
		  } catch (NoSuchElementException en) {
			  log.info( "Cannot find an offer to manage for " + searchingfor + en);
			  return false;
		  }
		  
		  try {
			  WebElement toclick = offer.findElement(By.cssSelector("img[class='logo']"));
			  toclick.click();
			  log.info("click");
		  }	catch (NoSuchElementException en) {
			  log.info( "Cannot find correct item to click" + en);
			  return false;
		  }  

		  return true;
	  }
	  public boolean clickWithinSinglePartnerList(String partnerstring, String offerstring) {
		  // NOT TESTED
		  // assumes more than one offer from same partner
		  // we build an offer string to search for
		  // then we find the image?
		  // string is something like deezer de-deezer-standalone-PK_DeezerFamily999 offer-wrapper
		  // obviously wont find the element if the string is wrong!!!!
		  String tofind = "div [class='" + partnerstring.toLowerCase() + " " + offerstring + " offer-wrapper']";
		  WebElement we = driver.findElement(By.cssSelector(tofind));
		  we.findElement(By.cssSelector("div.panel-inner-full.fl img.logo")).click();
		  return true;
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
	  
	  public boolean isMyOfferPresent(String tofind) throws InterruptedException {
		  return elementLoaded(By.cssSelector(tofind));
	  
	  }
}
