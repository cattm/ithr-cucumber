package com.ithr.ppe.test.cucumber.pages.partners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class SpotifyRegistration extends PageBase{

	  @FindBy(id="register-username")
	  private WebElement userName;
	
	  @FindBy(id="register-password")
	  private WebElement passWord;
	  
	  @FindBy(id="register-email")
	  private WebElement mainemail;
	
	  @FindBy(id="register-confirm-email")
	  private WebElement emailConfirm;
	  
	  @FindBy(id="register-dob-month")
	  private WebElement dobMonth;
	
	  @FindBy(id="register-dob-day")
	  private WebElement dobDay;
	  
	  @FindBy(id="register-dob-year")
	  private WebElement dobYear;
	  
	  @FindBy(id="register-male")
	  private WebElement sexMale;
	  
	  @FindBy(id="register-femail")
	  private WebElement sexFemale;
	  
	  @FindBy(id="register-button-email-submit")
	  private WebElement submit;


	  public  SpotifyRegistration (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	  
	  public void setUser(String user) {
		  userName.clear();
		  userName.sendKeys(user);
	  }

	  
	  public void setPassword(String password) {
		  passWord.clear();
		  passWord.sendKeys(password);
	  }
	  
	  public void setEmail(String email) {
		  mainemail.clear();
		  mainemail.sendKeys(email);
	  }
	  
	  public void setEmailConfirm(String email) {
		  emailConfirm.clear();
		  emailConfirm.sendKeys(email);
	  }
	  
	  public void clickSubmit() {
		  submit.click();
	  }
	  
	  public void setMale () {
		  sexMale.click();
		  
	  }
	  
	  public void setFemale () {
		  sexFemale.click();
	  }
	  
	  public void setDob (String year, String month, String day) {
		  // month Jan = '01'; feb = '02' <value> etc
		  String val = "";
		  switch (month) {
		  	case "jan" : val = "01";
		  	case "feb" : val = "02";
		  	default : val = "12";
		  }
		  Select dropdown = new Select(dobMonth);
		  dropdown.selectByValue(val);
		  
		  dobYear.clear();
		  dobYear.sendKeys(year);
		  
		  dobDay.clear();
		  dobDay.sendKeys(day);
		  		  
	  }

	  
}
