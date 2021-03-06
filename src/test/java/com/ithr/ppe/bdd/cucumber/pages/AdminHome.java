package com.ithr.ppe.bdd.cucumber.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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
	  
	  @FindBy(css=".checkbox>label input[ng-model='dropbox']")
	  private WebElement withDropbox;
	  
	  @FindBy(css=".checkbox>label input[ng-model='randomDropboxUid']")
	  private WebElement randomUID;
	  
	  @FindBy(css="input[ng-model='dropboxUid']")
	  private WebElement UIDText;
	  
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
		  if (subscription.equals("No parent subscription")) {
			  dropdown.selectByValue("-1");
		  } else {
			  dropdown.selectByValue(subscription);
		  }
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
	  
	  public void setWithDropbox() {
		  withDropbox.click();
	  }
	  
	  public void clearWithRandonID() {
		  randomUID.click();
	  }
	  
	  public void setDropboxUID(String uid) {
		  UIDText.clear();
		  UIDText.sendKeys(uid);
	  }
	  
	  public String getSubscriptionCheckUrl() {
		  return subsriptionUrl.getText();
	  }
	  
}
