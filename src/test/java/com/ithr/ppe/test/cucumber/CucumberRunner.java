package com.ithr.ppe.test.cucumber;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "html:reports/cucumber-html-report",
				   "json:reports/cucumber.json",
				   "junit:reports/cucumber-results.xml",
				  "pretty"},
		features = {"src/test/java/com/ithr/ppe/test/cucumber/features"},
//		tags = {"@purchase, @secpurchase, @checkit", "~@ignore"},
		tags = {"@checkit", "~@ignore"},
		glue = {"com.ithr.ppe.test.cucumber.steps"}
		)
public class CucumberRunner {

}

