package com.ithr.ppe.test.base;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.ithr.ppe.test.commons.TestProperties;
import com.ithr.ppe.test.cucumber.pages.AdminHome;
import com.ithr.ppe.test.cucumber.pages.AdminVerify;
import com.ithr.ppe.test.cucumber.pages.UserMSISDNEntry;
import com.ithr.ppe.test.cucumber.pages.UserSMSChallenge;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.JsonParser;
import com.ithr.ppe.test.cucumber.steps.utils.opcoTextChecker;

import cucumber.api.Scenario;

public class StepBase {
	public static String propertyFile = "test.properties";
	protected WebDriver driver;
	// from properties
	protected String baseAdminUrl;
	protected String baseUserUrl;
	protected String baseSpotifyHelper;
	protected String testReferenceDir;
	protected String refDir;
	protected Boolean checkAsserts;
	protected String pinCode;
	
	//required
	protected String opco = "gb"; //default
	protected String subscription;
	protected String userGroup;
	protected String checkUrl;
	protected String shortMsisdn;
	
	//checks
	protected opcoTextChecker textChecker = null;
	protected String fileToCheck =  "";
	protected Boolean refFileValid = false;
	protected JsonParser jsonParse; 
	
	public static Logger log = Logger.getLogger(StepBase.class);
		  
	public StepBase() {
	  	return;
	}
	
	private void loadProperties () throws IOException {
		  TestProperties.loadPropertyFile(propertyFile);
      	
		  baseAdminUrl = TestProperties.ADMIN_BASEURL;
		  baseUserUrl = TestProperties.USER_BASEURL;
		  baseSpotifyHelper = TestProperties.SPOTIFYBASE;
		  testReferenceDir = TestProperties.TEST_REFDIR;
		  refDir = TestProperties.TEST_REFDIR + "offers/";
		  checkAsserts = TestProperties.DO_ASSERTCHECKS;
		  pinCode = TestProperties.PINCODE;
	}

	protected void GetDebugScreenShot(String line) throws IOException {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// TODO: sort the damn hard coding path out
		FileUtils.copyFile(scrFile, new File("/Users/marcus/Documents/ithr/cucumber/target/testScreenShot_" + line + ".jpg"));
	}
	
	protected void loadDriver() {
		FirefoxProfile firefoxProfile = new FirefoxProfile();    
		firefoxProfile.setPreference("browser.private.browsing.autostart",true);
		driver = new FirefoxDriver();	      
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	protected void setUp() throws Exception {	
    	loadProperties();
    	log.info("Setting up Driver");
	    loadDriver();
	    driver.get(baseAdminUrl);
	}
	
	protected void tearDown(Scenario scenario) {
		try {
			if (scenario.isFailed()) {
				log.error("Scenario has failed - taking screenshot to embed in report");
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				  scenario.embed(screenshot, "image/png");
			}
		} finally {
			  driver.quit();			  
			  if (ErrorCollector.failedVerification()) {
				  log.error("There are verification Errors");
				  fail(ErrorCollector.getVerificationFailures());
			  }
			 
		}		
	}
	
}// end class
