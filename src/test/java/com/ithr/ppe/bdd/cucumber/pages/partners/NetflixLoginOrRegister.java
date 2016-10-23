package com.ithr.ppe.bdd.cucumber.pages.partners;
/**
 * Implements the Page Model for the Netflix login or register function
 * There are some issue with this page which have resulted in some rather ugly fixes 
 * the page seems to be unclear about if it has loaded or not
 * Added a wait loop on the submit button 
 * overload values in the text entry boxes - stops an error being generated
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ithr.ppe.bdd.commons.CommonConstants;
import com.ithr.ppe.bdd.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.bdd.cucumber.pages.PageBase;

public class NetflixLoginOrRegister extends PageBase {
	public static Logger log = Logger.getLogger(NetflixLoginOrRegister.class);
	// input[name="email"]
	// input[name="password"]
	// button[id="CC-startPaid"]

	@FindBy(name="email")
	WebElement userEmail;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(id="CC-startPaid")
	WebElement carryOn;
	
	@FindBy(css="div[class='input-message error']")
	WebElement errorText;
	
	public  NetflixLoginOrRegister (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	}
	
	public void setEmail(String email) {
		  userEmail.clear();
		  userEmail.sendKeys(email);
	}
	 
	public void setPassword(String pw) {
		  password.clear();
		  password.sendKeys(pw);
	}
	
	public String getErrorMsg() {
		String result = null;	
		WebElement tmp = waitVisible(errorText);
		if (tmp != null) result = errorText.getText();
		return result;
	}
	
	public void clickContinue() {	
		// we occasionally appear to get a race hazad on selecting this button
				for (int i = 0; i < 5 && !carryOn.isDisplayed(); i++) {
					try {
						Thread.sleep(CommonConstants.FAST);
					} catch (InterruptedException e) {
						log.error("Timer interrupted");
					}
					log.info("waiting for button to appear: " + i);
				}
		log.info("clickContinue NOW");
		carryOn.click();
	}
}
