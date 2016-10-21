package com.ithr.ppe.test.cucumber;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.commons.TestProperties;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "html:target/reports/cucumber-html-report",
				   "json:target/reports/cucumber.json",
				   "junit:target/reports/cucumber-results.xml",
				  "pretty:target/reports/cucumber-pretty.txt"},
		features = {"src/test/java/com/ithr/ppe/test/cucumber/features"},
		tags = {"@purchase, @secpurchase, @cancel", "~@ignore"},
//		tags = {"@checkit", "~@ignore"},
		glue = {"com.ithr.ppe.test.cucumber.steps"}
		)
public class CucumberRunner {
	public static Logger log = Logger.getLogger(CucumberRunner.class);

@BeforeClass
	public static void runThis() {
		try {
			TestProperties.loadPropertyFile("test.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String baseUserUrl = TestProperties.USER_BASEURL;

		// get the SW version for reporting
		String sw = CommandExecutor.execCurlSoftwareVersion(baseUserUrl);
		sw = StringUtils.replace(sw, "\n", " ");
		sw = StringUtils.replace(sw, "\t", " ");
		sw = StringUtils.replace(sw, "Manifest", "");
		String softwareVersion = StringUtils.replace(sw, "date/time", "");
		log.info("***************************************************************************************");
		log.info("Software under test is: " + softwareVersion);
		log.info("***************************************************************************************");

	}
}

