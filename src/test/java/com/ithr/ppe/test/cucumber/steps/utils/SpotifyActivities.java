package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.cucumber.pages.partners.SpotifyLoginOrRegister;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifyRegistration;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifySuccess;
import com.ithr.ppe.test.cucumber.steps.PurchaseSpotifyOffersSteps;

public class SpotifyActivities {
	public static Logger log = Logger.getLogger(SpotifyActivities.class);
	
	
	public static boolean RegisterForSpotify (WebDriver driver, String opco, String usernametouse) throws Exception  {

		// Spotify choose to register
		SpotifyLoginOrRegister logorreg = new SpotifyLoginOrRegister(driver);
		logorreg.clickRegister();
  
		// TODO: note the German spotify has more check boxes to tick 
		// fill in registration form
		SpotifyRegistration regpage = new SpotifyRegistration(driver);
		regpage.setUser(usernametouse);
		regpage.setPassword("password");
		regpage.setEmail(usernametouse + "@ithr.com");
		regpage.setEmailConfirm(usernametouse + "@ithr.com");
		regpage.setDob("1960", "May", "23");
		regpage.setMale();
		
		switch (opco) {
		case "de" :
			// need to click more buttons on registration page
			break;
		default : break;
		}
		
		regpage.clickSubmit();
  
		// get success page and check (for piece of mind that we are on the correct page)
		SpotifySuccess spotsuccess = new SpotifySuccess(driver);
		spotsuccess.bodyLoaded(); // give the page a chance to load
		
		if (spotsuccess.getHello().contentEquals("hello world")) {
			spotsuccess.hitOk();
			return true;
		} else {
			// Well it all went wrong 
			log.error("could not see required success text - returning false");
			return false;
		}
	}
	
	public static boolean LoginToSpotify () {
		// TODO: fill in code for this activity
		return false;
	}
}
