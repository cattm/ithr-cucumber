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
	  // TODO: these are horrible - tidy them up
	  @FindBy(xpath="/html/body/div[2]/div/div[1]/div/form/div[1]/input")
	  private WebElement shortMsisdn;
	  
	  @FindBy(xpath="//div/form/div[2]/select")
	  private WebElement opcoSelect;
	  
	  @FindBy(xpath="//div/form/div[4]/select")
	  private WebElement parentSubscriptionSelect;
	  
	  // only visible once subscription chosen
	  @FindBy(xpath="//div/form/div[5]/select")
	  private WebElement userGroupSelect;
	  
	  // only visable when setup is complete
	  @FindBy(xpath="//div/div[2]/div/p/a")
	  private WebElement subsriptionUrl;
	  
	  public AdminHome (WebDriver driver) {
		  super (driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	  
	  public String getShortMSISDN() {
		  String mv = shortMsisdn.getAttribute("value");
		  log.info("MSISDN value is " + mv);
		
		  return mv;
		  
	  }
	  
	  public void setOpco (String opco) {
		  // if the opco is not gb then error for the moment only
		  if (!opco.equals("gb")) {
			  log.error("Something BAD has happened = wrong OPCO " + opco);
		  }
		  
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
	  
	  public String getSbuscriptionCheckUrl() {
		  return subsriptionUrl.getText();
	  }
	  
}
