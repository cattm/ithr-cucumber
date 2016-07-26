package com.ithr.ppe.test.cucumber.pages;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpotifyHelper extends PageBase {
	
	public static Logger log = Logger.getLogger(SpotifyHelper.class);

	 @FindBy(tagName="pre")
	  private WebElement jsonToParse;
	

	 public SpotifyHelper(WebDriver driver) {
			super(driver);
			PageFactory.initElements(driver, this);
		}
	 
	 public String getUserName() {
		 String outcome = "";
		 String tmp = jsonToParse.getText();
		 JSONObject myobject = new JSONObject(tmp);
		 if (tmp.contains("error")) {
			 // it did not work - TODO: put in a more appropriate solution
			 log.error(tmp);
			 outcome = "ERRORUSERNAME";
		 } else {

			 String xx = myobject.getString("result");
			 log.debug(xx);
		 
			 String [] rs = xx.split("'|\\s");
			 if (rs[2].equals("created")) {
				 outcome = rs[1];
			 }
		 }
		 return outcome;
	 }

}
