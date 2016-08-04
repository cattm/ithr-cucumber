package com.ithr.ppe.test.cucumber.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ithr.ppe.test.cucumber.steps.GbSkyOfferSteps;

public class AdminHome extends PageBase{
	  
	  public static Logger log = Logger.getLogger(AdminHome.class);
	
	  @FindBy(css="input[ng-model='manualMsisdn']")
	  private WebElement shortMsisdn;
	  
	
	  @FindBy(css="select[ng-model='opco']")
	  private WebElement opcoSelect;
	  
	  
	  @FindBy(css="select[ng-model='parentSubscription']")
	  private WebElement parentSubscriptionSelect;
	  
	  // only visible once subscription chosen
	  @FindBy(css="select[ng-model='usergroup']")
	  private WebElement userGroupSelect;
	  
	  // only visable when setup is complete
	  @FindBy(xpath="//div/div[2]/div/p/a")
	  private WebElement subsriptionUrl;
	  
	  @FindBy(css="input[ng-model='noUsergroup']")
	  private WebElement noUserGroup;
	  
	  private String opco;
	  
	  public AdminHome (WebDriver driver) {
		  super (driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	  
	  public String getShortMSISDN() {
		  String mv = shortMsisdn.getAttribute("value");
		  log.debug("MSISDN value is " + mv);
		
		  return mv;
		  
	  }
	  
	  public void setOpco (String opco) {
		  // 
		  if (opco.equals("")) {
			  log.error("Something BAD has happened = No OPCO " + opco);
		  }
		  this.opco=opco;
			
		  Select dropdown = new Select(opcoSelect);
		  dropdown.selectByValue(opco);
	  }
	  
	  public void setSubscription (String subscription) {
		  if (subscription.equals("")) {
			  log.error("Something BAD has happened No subscription ");
		  }
		  
		  Select dropdown = new Select(parentSubscriptionSelect);
		  dropdown.selectByValue(subscription);
	  }
	  
	  public void setUserGroup (String usergroup) {
		  // if the opco is not gb then error
		  if (usergroup.equals("")) {
			  log.error("Something BAD has happened No usergroup ");
		  }
		  
		  Select dropdown = new Select(userGroupSelect);
		  dropdown.selectByValue(usergroup);
	  }
	  
	  public void setNoUserGroup() {
		  noUserGroup.click();
	  }
	  
	  public String getSubscriptionCheckUrl() {
		  return subsriptionUrl.getText();
	  }
	  
}
