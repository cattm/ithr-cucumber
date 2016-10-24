package com.ithr.ppe.bdd.cucumber;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ithr.ppe.bdd.commons.CommandExecutor;
import com.ithr.ppe.bdd.commons.TestProperties;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "html:target/reports/cucumber-html-report",
				   "json:target/reports/cucumber.json",
				   "junit:target/reports/cucumber-results.xml",
				  "pretty:target/reports/cucumber-pretty.txt"},
		features = {"src/test/java/com/ithr/ppe/bdd/cucumber/features/"},
//		tags = {"@purchase, @secpurchase, @cancel", "~@ignore"},
		tags = {"@checkit", "~@ignore"},
		glue = {"com.ithr.ppe.bdd.cucumber.steps"}
		)
public class CucumberRunner {
	public static Logger log = Logger.getLogger(CucumberRunner.class);

@BeforeClass
	public static void runThis() {
		try {
			TestProperties.loadPropertyFile("test.properties");
		} catch (IOException e) {
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

