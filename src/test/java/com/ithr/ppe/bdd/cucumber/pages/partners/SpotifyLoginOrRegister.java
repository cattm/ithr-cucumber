package com.ithr.ppe.bdd.cucumber.pages.partners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.bdd.cucumber.pages.PageBase;

public class SpotifyLoginOrRegister extends PageBase{

	  @FindBy(xpath="/html/body/div/div[2]/div/div[3]/div[2]/div/a")
	  WebElement registerButton;
	  
	  @FindBy(xpath="/html/body/div/div[2]/div/div[2]/div")
	  WebElement loginButton;
	
	  public  SpotifyLoginOrRegister (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	  
	  public void clickRegister() {
		  registerButton.click();
	  }

	  public void clickLogin() {
		  loginButton.click();
		  
	  }
}
