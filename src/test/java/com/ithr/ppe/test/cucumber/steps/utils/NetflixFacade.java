package com.ithr.ppe.test.cucumber.steps.utils;
/**
 * Implements the External Partner interface for Netflix
 * There are problems with this Partner and there are some dummy wait loops to get it running
 * We should be looking to remove these
 * 
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixLoginOrRegister;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixOffer;
import com.ithr.ppe.test.cucumber.pages.partners.NetflixSuccess;
import com.ithr.ppe.test.cucumber.steps.interfaces.IExternalPartner;


public class NetflixFacade implements IExternalPartner{
	
	public static Logger log = Logger.getLogger(NetflixFacade.class);


	public boolean register(WebDriver driver, String opco, String usernametouse) {
		// have had to slow this baby down - site is very flaky	
		log.info("Going to continue");
		try {
			Thread.sleep(CommonConstants.SLOW);
		} catch (InterruptedException e) {
			log.error("sleep got interrupted " + e);
		}
		
		NetflixOffer offer = new NetflixOffer(driver);
		if (!offer.bodyLoaded()) {
			 log.error("Offer Page body did not load in time");
		}
		
			
		offer.clickSubmit();
		try {
			Thread.sleep(CommonConstants.MEDIUM);
		} catch (InterruptedException e) {
			log.error("Interrupted sleep - remove this delay please " + e);
		}
		// TODO we have a timing issue here it works some times......
		// check the page loaded before we hit stuff
		log.info("Going to do username/password");
		NetflixLoginOrRegister logorreg = new NetflixLoginOrRegister(driver);
		if (!logorreg.bodyLoaded()) {
			 log.error("Neflix Registration Page body did not load in time");
		}
		
		
		// Some really dodgy code as a work around
		// try to set the params and then repeat if we get their JS error message
		logorreg.setEmail(usernametouse);
		logorreg.setPassword("passwordnf");		
		logorreg.clickContinue();
		if (logorreg.getErrorMsg() != null) {
			log.info("Repeating entry  ...." + logorreg.getErrorMsg()  + " Invalid Error Displayed");
			logorreg.setEmail(usernametouse);
			logorreg.setPassword("passwordnf");		
			logorreg.clickContinue();
		} else {
			log.info("No Netflix Register error message - Phew!");
		}
		
		log.info("Going to check success page for netflix");
		try {
			Thread.sleep(CommonConstants.FAST);
		} catch (InterruptedException e) {
			log.error("Interrupted sleep - remove this delay please " + e);
		}
		
		NetflixSuccess netsuccess = new NetflixSuccess(driver);
		if (!netsuccess.bodyLoaded()) {
			 log.error("Netflix success Page body did not load in time");
		}
		

		// TODO: this will be opco dependant - uk solution in place
		if (netsuccess.getNetflixSuccess().contentEquals("Your Netflix membership, which begins with a free trial, has begun.")) {
			return true;
		}
		return false;		
	}
	
	public boolean login (WebDriver driver, String opco, String usernametouse) {
		return false;
	}
	
	public String terminateUser(WebDriver driver, String baseurl, String opco, String username) {
		return "NOT IMPLEMENTED YET";

	}
	
	public String getUserStatus(WebDriver driver, String baseurl, String opco, String username) {
		return "NOT IMPLEMENTED YET";

	}
	
	public String getUser (WebDriver driver, String baseurl, String opco) {
		return "NOT IMPLEMENTED YET";

	}
}
