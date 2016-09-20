package com.ithr.ppe.test.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class BildWelcome extends PageBase {
		public static Logger log = Logger.getLogger(BildWelcome.class);
				
		@FindBy(id="title-red")
		WebElement welcomeText;
			
		public BildWelcome(WebDriver driver) {
			  super(driver);
			  PageFactory.initElements(driver, this);	  
		}
		
		public String getWelcomeText() {
			return welcomeText.getText();
		}
}
