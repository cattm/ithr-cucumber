package com.ithr.ppe.bdd.base;

/**
 * Implements the base model for all Step implementations
 * It will be common 
 * It sets up the driver/screen/shots and commons variables and behaviors needed by all tests
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
//import org.openqa.selenium.remote.DesiredCapabilities;











import com.ithr.ppe.bdd.commons.CommandExecutor;
import com.ithr.ppe.bdd.commons.DateStamp;
import com.ithr.ppe.bdd.commons.TestProperties;
import com.ithr.ppe.bdd.cucumber.steps.utils.ErrorCollector;
import com.ithr.ppe.bdd.cucumber.steps.utils.JsonParser;
import com.ithr.ppe.bdd.cucumber.steps.utils.TandCRequired;
import com.ithr.ppe.bdd.cucumber.steps.utils.opcoTextChecker;

import cucumber.api.Scenario;

public class StepBase {
	public static String propertyFile = "test.properties";
	protected WebDriver driver;
	protected Scenario scenario;
	protected String browser;
	protected String softwareVersion;
	protected String ditEnv;

	// do we need TnC?
	protected TandCRequired needTandC = null;
	// from properties
	protected String 	baseAdminUrl;
	protected String 	baseUserUrl;
	protected String 	basePartnerHelper;
	protected String 	testReferenceDir;
	protected String 	refDir;
	protected String 	pinCode;
	protected Boolean 	embedAllImages;
	protected Boolean	doDebugImages;
	
	
	public static Logger log = Logger.getLogger(StepBase.class);
		  
	public StepBase() {
	  	return;
	}
	
	private void loadProperties () throws IOException {
		  TestProperties.loadPropertyFile(propertyFile);
      	
		  baseAdminUrl = TestProperties.ADMIN_BASEURL;
		  baseUserUrl = TestProperties.USER_BASEURL;
		  basePartnerHelper = TestProperties.SPOTIFYBASE;
		  pinCode = TestProperties.PINCODE;
		  
		  log.info("checking Asserts is : " + TestProperties.DO_ASSERTCHECKS);	 
		  ErrorCollector.setVerify(TestProperties.DO_ASSERTCHECKS);		 
	}

	protected void GetDebugScreenShot(String reference)  {
		if (doDebugImages) {
			DateStamp mydate = new DateStamp();	
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String location = "target/reports/screenshots/" + mydate.getFileDayFormat() + "/" + mydate.getFileTimeFormat() + "_"+ reference + ".jpg";
			log.info("Storing picture to : " + location);
			try {
				FileUtils.copyFile(scrFile, new File(location));
			} catch (IOException e) {
				log.error("Cannot create File " + location);
			}
		}
	}
	
	// embeds under all conditions
	protected void ScenarioScreenshot () {
		final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.embed(screenshot, "image/png");
	}
	
	//embeds only if we enable ALL images
	protected void CheckedScenarioScreenshot () {
		if (embedAllImages) {
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
	}
	
	// reports to scenario or an image file if we are checking Asserts and want to identify be stack
	// best use is within a catch block
	protected void ReportStack(StackTraceElement element)  {
		// I want to report the most useful information 
		// based on the stacktrace element provided
		Integer linenumber = element.getLineNumber();
		String line = linenumber.toString(); 
		String methodn = element.getMethodName();
		String classn = element.getClassName();
		
		GetDebugScreenShot(classn + "_"+ methodn + "_" + line);
		ScenarioScreenshot ();
			
	}
	
	// reprot to scenario or file probable from exception catcher
	protected void ReportScreen(String idstring)  {		

		GetDebugScreenShot(idstring);
		ScenarioScreenshot ();
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
	
	private void findSWVersion () {
		// get the SW version for reporting
    	String sw = CommandExecutor.execCurlSoftwareVersion(baseUserUrl);
    	sw = StringUtils.replace(sw, "\n", " ");
    	sw = StringUtils.replace(sw, "\t", " ");
    	sw = StringUtils.replace(sw, "Manifest", "");
    	softwareVersion = StringUtils.replace(sw, "date/time", "");
    	log.info("Software under test is: " + softwareVersion);
	}
	
	protected void setUp(Scenario scenario) throws Exception {	
		this.scenario = scenario;
    	loadProperties();
    	
    	log.info("Setting up Driver");
    	browser = System.getProperty("test.driver", TestProperties.DRIVER);
    	selectDriver(); 
    	  	
    	testReferenceDir = System.getProperty("test.testrefdir", TestProperties.TEST_REFDIR);
    	refDir = testReferenceDir + "offers/";	
    	log.info("set up location of property files - " + testReferenceDir);
    	
    	log.info("Checking usage of DIT or DIT2");
    	ditEnv = System.getProperty("test.environment", "DIT");
    	if (ditEnv.equalsIgnoreCase("DIT2")){
    		// replace dit with dit2 in env urls strings.
    		baseAdminUrl = baseAdminUrl.replace("dit", "dit2");
    		baseUserUrl = baseUserUrl.replace("dit", "dit2");;
    		basePartnerHelper = basePartnerHelper.replace("dit", "dit2");	
    	}
     
    	
    	// TODO: make a property in itself?
    	needTandC = TandCRequired.getInstance();
    	needTandC.loadPropertyFile( TestProperties.TEST_PROPERTY_BASE+ "tandc.properties");
 
    	
    	// set up the image reporting/saving
    	String dopictures = System.getProperty("test.allimages", TestProperties.DO_SCREENSHOTS);
    	embedAllImages = dopictures.matches("true");
    	
    	String dodebug = System.getProperty("test.debugimages", "false");
    	doDebugImages = dodebug.matches("true");
   
    	//findSWVersion();
    	
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
				  log.error("There are verification Errors to review");
				  //Fail the scenario as the individual text errors constitute a fail overall
				  // even though the scenario probable completed ok
				  fail(ErrorCollector.getVerificationFailures());
			  }
			 
		}		
	}
	
}// end class
