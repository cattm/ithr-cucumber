package com.ithr.ppe.test.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class ChiliRegister extends PageBase {
	public static Logger log = Logger.getLogger(ChiliRegister.class);
	
	/*
	 * <div class="wrapper-btn margin-bottom60px">
	 * <a class="action-dos margin-top30px ng-scope" translate="" ui-sref="register" href="/registration">Registrati</a>
	 * </div>
	 */
	@FindBy(xpath="(//a[contains(text(),'Registrati')])[3]")
	WebElement registerButton;
	
	@FindBy(id="AcceptButton")
	WebElement acceptCookieButton;
	
	@FindBy(id="first-item")
	WebElement someButton;
			
	public ChiliRegister(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	public void clickContinue() {
		registerButton.click();
	}
	
	public void acceptCookies() {
		WebElement toclick = waitClickable(acceptCookieButton);
		if (toclick != null) {
			log.info("accepting cookies");
			toclick.click();
		}
		else log.info("No cookie accpet located ");
		
	}
	
	public void clickSomeButton() {
		someButton.click();
	}
	
}

