package com.ithr.ppe.test.cucumber.pages.partners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;

/**
 * Implements the Page Object Model for Netflix marketing offer page - just looking to hit submit
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
public class NetflixOffer extends PageBase {

	@FindBy(css="button[type='submit']")
	WebElement submit;
	
	
	public  NetflixOffer (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	}
	
	public void clickSubmit() {	
		submit.click();
	}

	// probably a success page
	// Your Netflix membership, which begins with a free trial, has begun.
	// h2[data-reactid="12"]
}
