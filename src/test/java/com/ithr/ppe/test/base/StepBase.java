package com.ithr.ppe.test.base;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
//import org.openqa.selenium.remote.DesiredCapabilities;





import com.ithr.ppe.test.commons.DateStamp;
import com.ithr.ppe.test.commons.TestProperties;
import com.ithr.ppe.test.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.test.cucumber.steps.utils.JsonParser;
import com.ithr.ppe.test.cucumber.steps.utils.opcoTextChecker;

import cucumber.api.Scenario;

public class StepBase {
	public static String propertyFile = "test.properties";
	protected WebDriver driver;
	protected Scenario scenario;
	protected String browser;
	// from properties
	protected String 	baseAdminUrl;
	protected String 	baseUserUrl;
	protected String 	baseSpotifyHelper;
	protected String 	testReferenceDir;
	protected String 	refDir;
	protected Boolean 	checkAsserts;
	protected String 	pinCode;
	
	//required
	protected String opco = "gb"; //default
	protected String subscription;
	protected String userGroup;
	protected String shortMsisdn;
	
	//checks
	protected opcoTextChecker 	textChecker = null;
	protected String 			fileToCheck =  "";
	protected Boolean 			refFileValid = false;
	protected JsonParser 		jsonParse; 
	
	public static Logger log = Logger.getLogger(StepBase.class);
		  
	public StepBase() {
	  	return;
	}
	
	private void loadProperties () throws IOException {
		  TestProperties.loadPropertyFile(propertyFile);
      	
		  baseAdminUrl = TestProperties.ADMIN_BASEURL;
		  baseUserUrl = TestProperties.USER_BASEURL;
		  baseSpotifyHelper = TestProperties.SPOTIFYBASE;
		  //testReferenceDir = TestProperties.TEST_REFDIR;
		  //refDir = TestProperties.TEST_REFDIR + "offers/";
		  checkAsserts = TestProperties.DO_ASSERTCHECKS;
		  pinCode = TestProperties.PINCODE;
		  //browserModel = TestProperties.DRIVER;
	}

	protected void GetDebugScreenShot(String reference)  {
		DateStamp mydate = new DateStamp();	
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String location = "reports/screenshots/" + mydate.getFileDayFormat() + "/" + mydate.getFileTimeFormat() + "_"+ reference + ".jpg";
		log.info("Storing picture to : " + location);
		try {
			FileUtils.copyFile(scrFile, new File(location));
		} catch (IOException e) {
			log.error("Cannot create File " + location);
		}
	}
	
	protected void ScenarioScreenshot () {
		final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		  scenario.embed(screenshot, "image/png");
	}
	
	protected void ReportStack(StackTraceElement element)  {
		// I want to report the most useful information 
		// based on the stacktrace element provided
		Integer linenumber = element.getLineNumber();
		String line = linenumber.toString(); 
		String methodn = element.getMethodName();
		String classn = element.getClassName();
		
		// make sure we capture the screen in an image and dump in a directory or the test report
		if (!checkAsserts) {
			GetDebugScreenShot(classn + "_"+ methodn + "_" + line);
		} else {
			ScenarioScreenshot ();
		}		
	}
	
	protected void ReportStack(String idstring)  {		
		// make sure we capture the screen in an image and dump in a directory or the test report
		if (!checkAsserts) {
			GetDebugScreenShot(idstring);
		} else {
			ScenarioScreenshot ();
		}
	}
	
	protected void loadMCPJSDriver () {
		log.info("loadMCJSDriver");
		String pathtophantom = System.getProperty("phantom.path","/Users/marcus/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
		File file = new File(pathtophantom);				
        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());		
        driver = new PhantomJSDriver();       
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	protected void loadJenkinsPJSDriver () {
		log.info("loadJenkinsPJSDriver");
		driver = new PhantomJSDriver();       
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	protected void loadFFDriver() {
		log.info("loadFFDriver");
		FirefoxProfile firefoxProfile = new FirefoxProfile();    
		firefoxProfile.setPreference("browser.private.browsing.autostart",true);
		driver = new FirefoxDriver();	      
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	protected void selectDriver () {
    	switch (browser) {
    	case "firefox"   : 	loadFFDriver();
    						break;
    	case "phantomjs" :  loadJenkinsPJSDriver();
    						break;
    	case "mcphantom" :  loadMCPJSDriver();
    						break;
    	default : loadFFDriver();
    			  break;
  
    	}
	}
	protected void getNewDriver () {
		if (driver != null) {
			driver.quit();
		}
		selectDriver();
	}
	
	protected void setUp(Scenario scenario) throws Exception {	
		this.scenario = scenario;
		// load the required properties from file
    	loadProperties();
    	
    	// we get some properties from the command line
    	// driver
    	// location of reference files for checking
    	log.info("Setting up Driver");
    	browser = System.getProperty("test.driver", "firefox");
    	selectDriver(); 
    	  	
    	testReferenceDir = System.getProperty("test.testrefdir", TestProperties.TEST_REFDIR);
    	refDir = testReferenceDir + "offers/";	
    	log.info("set up location of JSON files - " + testReferenceDir);
	}
	
	protected void tearDown() {
		try {
			if (scenario.isFailed()) {
				log.error("Scenario has failed - taking screenshot to embed in report");
				ScenarioScreenshot();
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
