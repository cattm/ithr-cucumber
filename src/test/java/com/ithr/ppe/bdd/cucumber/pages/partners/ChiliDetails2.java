package com.ithr.ppe.bdd.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ithr.ppe.bdd.cucumber.pages.PageBase;

public class ChiliDetails2  extends PageBase {
	public static Logger log = Logger.getLogger(ChiliDetails2.class);
	
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
	
	@FindBy(id="registerKo")
	WebElement carryOnButton;
	
	
	public ChiliDetails2(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
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

	 public void clickCarryOn() {
			carryOnButton.click();
	}	
}
