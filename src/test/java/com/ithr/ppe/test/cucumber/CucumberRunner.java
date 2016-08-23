package com.ithr.ppe.test.cucumber;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "html:reports/cucumber-html-report",
				   "json:reports/cucumber.json", 
				  "pretty"},
//		features = {"src/test/java/com/ithr/ppe/test/cucumber/features"},
				features = {"/Users/Marcus/Documents/ithr/features"},
		tags = {"@netflixpurchase, @nowtvpurchase, @skypurchase, @spotifypurchase", "~@ignore"},
		glue = {"com.ithr.ppe.test.cucumber.steps"}
		)
public class CucumberRunner {

}

