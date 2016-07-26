package com.ithr.ppe.test.cucumber.pages.partners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;
import com.ithr.ppe.test.cucumber.steps.PurchaseSkyOffersSteps;

public class SpotifySuccess extends PageBase{
	public static Logger log = Logger.getLogger(SpotifySuccess.class);
	
	@FindBy(xpath="//button[2]")
	private WebElement ok;
	
	@FindBy(xpath="//div/p")
	private WebElement helloText;
	
	@FindBy(xpath="//h2")
	private WebElement vodafoneSpotify;
	
	 public  SpotifySuccess (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);
		  
	  }
	 
	 public void hitOk() {
		 ok.click();
	 }

	 public String getHello () {
		 String ret =  helloText.getText();
		 return ret;
	 }
	 public String getVodafone () {
		 String ret =  vodafoneSpotify.getText();	 
		 return ret;
	 }
	 
}
