package com.ithr.ppe.bdd.cucumber.pages;

import org.apache.log4j.Logger;
import org.json.JSONArray;
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
} *
or 
  "msisdn" : "399715018441",
  "opco" : "it",
  "usergroup" : "4guser",
  "individualCreated" : false,
  "partner" : "dropbox",
  "dropboxUid" : "597844980"
}

or something like when modified
{
  "msisdn" : "499749777907",
  "opco" : "de",
  "usergroup" : "ug_ppe_redplus",
  "individualCreated" : false,
  "msisdnUpdated" : true
}
 */
	

	
	public static Logger log = Logger.getLogger(AdminVerify.class);

	 @FindBy(tagName="pre")
	 private WebElement jsonToParse;
	 
	 public AdminVerify(WebDriver driver)  {
			super(driver);
			PageFactory.initElements(driver, this);
		}
	 
	 public boolean isInGroup(String group) {
		 Boolean outcome;
		 
		 String tmp = jsonToParse.getText();
		 JSONObject myobject = new JSONObject(tmp);
		 log.info(tmp);
		 outcome = group.contentEquals(myobject.getString("usergroup"));		 			 
		 
		 return outcome.booleanValue();
	 }
	 
	 /*
	  *   "opco" : "gb",
	  *     "partner" : "sky",
	  *      "message" : [ "Searching all active subscriptions for msisdn: 449535237861' for partner='sky'", "Cancelled 1 subscription" ]
	  */
	 public boolean isRemoved(String partnertoverify) {		 
		 String tmp = jsonToParse.getText();
		 JSONObject myobject = new JSONObject(tmp);
		 log.debug(tmp);
		 
		 //String opco = myobject.getString("opco");
		 String partner = myobject.getString("partner").toLowerCase();
		 //message is actually in an JSON array 
		 JSONArray myarray = myobject.getJSONArray("message");
		 String message = myarray.getString(1);
		 
		 log.info("And the result message is: " + message);
		 if (partnertoverify.toLowerCase().contentEquals(partner)) {
			 if (message.contains("No subscriptions found")) return false;
		 } else return false;
		 
		 return true;
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
	 
	 // Added for dropbox
	 public boolean isDropBoxIndividualCreated(String uid) {
		 
		 Boolean created = false;
		 String partner = "dropbox";
		 
		 String tmp = jsonToParse.getText();
		 JSONObject myobject = new JSONObject(tmp);
		
		 if (tmp.contains("error")) {
			 // it did not work - TODO: put in a more appropriate solution
			 log.error(tmp);
			 created = false;
		 } else {
			 created = myobject.getBoolean("individualCreated");		 			 
		 }
		 
	     if (created) {
	    	 if (partner.equals(myobject.getString("partner"))) {
	    		 if (uid.equals(myobject.getString("dropboxUid"))) {
	    			 log.info("dropbox UID user created ");
	    			 return true;
	    		 }
	    	 }
	     }		 
		 return false;
	 }
}
