package com.ithr.ppe.test.base;

import static org.junit.Assert.fail;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.ithr.ppe.test.commons.TestProperties;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class StepBase {
	public static String propertyFile;
	protected WebDriver driver;
	protected String baseAdminUrl;
	protected String baseUserUrl;
	protected String testReferenceDir;
	protected String refDir;
	protected StringBuffer verificationErrors = new StringBuffer();
	
	public static Logger log = Logger.getLogger(StepBase.class);
	  
	public StepBase(String propertyfile) {
	  	StepBase.propertyFile=propertyfile;
	}
	
	private void loadProperties () throws IOException {
		  TestProperties.loadPropertyFile(propertyFile);
      	
		  baseAdminUrl = TestProperties.ADMIN_BASEURL;
		  baseUserUrl = TestProperties.USER_BASEURL;
		  testReferenceDir = TestProperties.TEST_REFDIR;
		  refDir = TestProperties.TEST_REFDIR;
	}

	protected void setUp() throws Exception {	
    	loadProperties();	     
    	// TODO: may need to make this static
	    driver = new FirefoxDriver();	      
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	    driver.get(baseAdminUrl);
	}
	
	protected void tearDown(Scenario scenario) throws Exception {
		try {
			if (scenario.isFailed()) {
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				  scenario.embed(screenshot, "image/png");
			}
		} finally {
			  driver.quit();
			  
			  String verificationErrorString = verificationErrors.toString();
			  if (!"".equals(verificationErrorString)) {
			     fail(verificationErrorString);
			  }
		}		
	}
}// end class
