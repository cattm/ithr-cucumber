package com.ithr.ppe.test.cucumber.pages;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminVerify extends PageBase {
/*
 * Something like this is expected outcome
 * {
  "msisdn" : "392765916300",
  "opco" : "it",
  "usergroup" : "4guser",
  "individualCreated" : true
}
 */
	
	public static Logger log = Logger.getLogger(AdminVerify.class);

	 @FindBy(tagName="pre")
	 private WebElement jsonToParse;
	 
	 public AdminVerify(WebDriver driver)  {
			super(driver);
			PageFactory.initElements(driver, this);
		}
	 
	 public boolean isIndividualCreated() {
		 Boolean outcome;
		 
		 String tmp = jsonToParse.getText();
		 JSONObject myobject = new JSONObject(tmp);
		
		 if (tmp.contains("error")) {
			 // it did not work - TODO: put in a more appropriate solution
			 log.error(tmp);
			 outcome = false;
		 } else {
			 outcome = myobject.getBoolean("individualCreated");		 			 
		 }
		 return outcome.booleanValue();
	 }
}
