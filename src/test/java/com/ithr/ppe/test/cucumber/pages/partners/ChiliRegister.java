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
	
	@FindBy(css=".wrapper-btn.margin-bottom60px")
	WebElement registerButton;
	
	@FindBy(id="registerKo")
	WebElement carryOnButton;
		
	@FindBy(id="useremail")
	WebElement email;
	
	@FindBy(id="emailConfirm")
	WebElement emailConfirm;
	
	@FindBy(id="password")
	WebElement password;
			
	@FindBy(id="acceptCondition")
	WebElement acceptConditions;
	
	@FindBy(id="name")
	WebElement firstName;
	
	@FindBy(id="surname")
	WebElement surName;
	
	@FindBy(id="birthdateDay")
	WebElement day;
	
	@FindBy(id="birthdateMonth")
	WebElement month;
	
	@FindBy(id="birthdateYear")
	WebElement year;

			
	public ChiliRegister(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	public void clickContinue() {
		registerButton.click();
	}
	
	public void clickCarryOn() {
		carryOnButton.click();
	}

	public void setEmail(String mainemail) {
		 email.clear();
		 email.sendKeys(mainemail);
	}
	
	public void setEmailConfirm(String email) {
		emailConfirm.clear();
		emailConfirm.sendKeys(email);
	}
	
	public void setPassword(String pw) {
		password.clear();
		password.sendKeys(pw);
	}
	
	public void clickTerms() {
		acceptConditions.click();
	}
	
	public void setFirstName(String name) {
		firstName.clear();
		firstName.sendKeys(name);
	}

	public void setLastName (String name) {
		surName.clear();
		surName.sendKeys(name);
	}
	
	 public void setDob () {
		 
		 String val = "1970";
		  Select dropdown = new Select(this.year);
		  dropdown.selectByValue(val);
		  
		  val = "number:3";
		  dropdown = new Select(this.month);
		  dropdown.selectByValue(val);
		  
		  val = "number:21";
		  dropdown = new Select(this.day);
		  dropdown.selectByValue(val);
	 }

	
	
}

