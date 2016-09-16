package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.DateStamp;
import com.ithr.ppe.test.cucumber.pages.SpotifyHelper;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifyLogin;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifyLoginOrRegister;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifyRegistration;
import com.ithr.ppe.test.cucumber.pages.partners.SpotifySuccess;
import com.ithr.ppe.test.cucumber.steps.interfaces.IExternalPartner;

public class SpotifyFacade implements IExternalPartner {
	public static Logger log = Logger.getLogger(SpotifyFacade.class);
	
	
		
	
	public boolean register(WebDriver driver, String opco, String usernametouse)   {

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
			// TODO: need to click more buttons on registration page
			break;
		default : break;
		}
		
		regpage.clickSubmit();
  
		// get success page and check (for piece of mind that we are on the correct page)
		SpotifySuccess spotsuccess = new SpotifySuccess(driver);
		if (!spotsuccess.bodyLoaded()) {
			 log.error("Spotify success Page body did not load in time");
		}
		
		
		// TODO: this text may change dependent upon country - so its not a good check
		// This needs correction
		// example ES - Something do Spotify
		if (true) {
		//if (spotsuccess.getHello().contentEquals("hello world")) {
			spotsuccess.hitOk();
			return true;
		} else {
			// Well it all went wrong 
			log.error("could not see required success text - returning false");
			return false;
		}
	}
	

	public boolean login (WebDriver driver, String opco, String usernametouse) {
		
		// Spotify choose to Login
		SpotifyLoginOrRegister logorreg = new SpotifyLoginOrRegister(driver);
		logorreg.clickLogin();
		
		
		SpotifyLogin login = new SpotifyLogin(driver);
		login.setUser(usernametouse);
		login.setPassword("password");
		login.clickSubmit();
		
		// TODO: move this test - its a common pattern of behaviour
		// get success page and check (for piece of mind that we are on the correct page)
		SpotifySuccess spotsuccess = new SpotifySuccess(driver);
		if (!spotsuccess.bodyLoaded()) {
			 log.error("Spotify Success Page body did not load in time");
		}
		
				
		if (spotsuccess.getHello().contentEquals("hello world")) {
			spotsuccess.hitOk();
			return true;
		} else {
			// Well it all went wrong 
			log.error("could not see required success text - returning false");
			return false;
		}
	
		
	}
	
	
	public String terminateUser(WebDriver driver, String baseurl, String opco, String username) {
		String urlString = baseurl + "?username=" + username + "&opco=" + opco + "&action=terminate";
		log.info(urlString);
		driver.get(urlString);
		SpotifyHelper spotpage = new SpotifyHelper(driver);
		
		return spotpage.getPage();
	}
	
	public String getUserStatus(WebDriver driver, String baseurl, String opco, String username) {
		String urlString = baseurl + "?username=" + username + "&opco=" + opco;
		log.info(urlString);
		driver.get(urlString);
		SpotifyHelper spotpage = new SpotifyHelper(driver);
		return spotpage.getPage();
	}
	
	
	public String getUser (WebDriver driver, String baseurl, String opco)  {
		DateStamp myds = new DateStamp();
		String rn = myds.getRanDateFormat();
		String urlString = baseurl + "?username=ithrtest" +  rn + "&opco=" + opco;
		log.info(urlString);
		driver.get(urlString);
		SpotifyHelper spotpage = new SpotifyHelper(driver);
		
		return spotpage.getUserName();
	}
	
	
	/*
	 * TODO: 02/08/2016
	 * Ion has modified the status helper so that
	 * https://dit.offers.vodafone.com/spotifyHelper?opco=xx&username=yy - creates account then gives status on subsequent calls
	 * action= terminate - will terminate offers
	 * use this!!! 
	 * https://dit.offers.vodafone.com/spotifyHelper?opco=gb&username=marcus127 creates an account
	 * https://dit.offers.vodafone.com/spotifyHelper?opco=gb&username=marcus127 called again will return status of customer account
	 * https://dit.offers.vodafone.com/spotifyHelper?opco=gb&username=marcus1278&action=terminate 
	 * will terminate on spotify. if there are a number of orders it will list them all
	 * These may fit here or in the command section?
	 */
	
}
