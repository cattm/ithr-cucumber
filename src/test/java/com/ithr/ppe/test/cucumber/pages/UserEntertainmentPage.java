package com.ithr.ppe.test.cucumber.pages;



import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.ithr.ppe.test.cucumber.steps.GbSkyOfferSteps;

// There will be offers and subscriptions
// initially there will be no subscriptions - we hope! And this we should check
public class UserEntertainmentPage extends PageBase{
	 public static Logger log = Logger.getLogger(UserEntertainmentPage.class);
	
		
	  @FindBy(css="div.panel-inner-full.fl img.logo")
	  private List<WebElement> offerIcons;
	  
	  @FindBy(xpath="/html/body/div/div/header/div[2]/p")
	  private WebElement msisdn;
	  
	  @FindBy(css="div.wrapper.subscriptions-wrapper")
	  private WebElement subscriptionText;
	  
	  public  UserEntertainmentPage (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	
	  
	  public void ClickOfferImage(String offer) {
		  //iterate through the list and click on it
		  Iterator <WebElement> iterator = offerIcons.iterator();
		  while (iterator.hasNext()) {
			  WebElement element = iterator.next();
			  String thetext = element.getAttribute("src");
			  log.debug("checking element :" + thetext);
			  if ( thetext.endsWith(offer)) {
				  log.debug("Clicking offer");
				  element.click();	  
			  }
		  }
		  
	  }
	  
	  public String getMSISDN () {
		  return msisdn.getText();
		  
	  }
	  
	  public String getSubscriptionText() {
		  return subscriptionText.getText();
	  }
	  
}
