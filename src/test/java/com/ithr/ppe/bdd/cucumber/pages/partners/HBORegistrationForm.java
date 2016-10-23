package com.ithr.ppe.bdd.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.bdd.cucumber.pages.PageBase;

public class HBORegistrationForm extends PageBase {
	public static Logger log = Logger.getLogger(HBORegistrationForm.class);
			
    @FindBy(id="input-firstName")
	WebElement firstName;
	
	@FindBy(id="input-lastName")
	WebElement lastName;
					
	@FindBy(id="input-email")
	WebElement userEmail;
	
	@FindBy(id="input-password")
	WebElement password;

	@FindBy(id="input-passwordConfirmation")
	WebElement passwordConfirm;
	
	@FindBy(css=".btn.event-btn.cf.btn--registerButton")
	WebElement register;
	
	public HBORegistrationForm(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void setFirstName(String first) {
		  firstName.clear();
		  firstName.sendKeys(first);
	}
	
	public void setLastName(String last) {
		  lastName.clear();
		  lastName.sendKeys(last);
	}
	
	public void setEmail(String email) {
		userEmail.clear();
		userEmail.sendKeys(email);
	}
	
	public void setPassword(String pw) {
		  password.clear();
		  password.sendKeys(pw);
	}
	
	public void setConfirmPassword(String pw) {
		passwordConfirm.clear();
		passwordConfirm.sendKeys(pw);
	}
	
	public void clickRegister() {
		register.click();
	}
}
