package com.ithr.ppe.test.cucumber;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ithr.ppe.test.commons.CommandExecutor;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(	features = {"/Users/marcus/Documents/ithr/features"},
					tags = {"@purchase, @checkit", "~@ignore"},
					glue = {"com.ithr.ppe.test.cucumber.steps"},
                    plugin = {"com.ithr.ppe.test.cucumber.ExtentCucumberFormatter", 
							"junit:reports/cucumber-results.xml",
							"json:reports/cucumber.json", 
		  					"pretty"})


public class MCExtentRunner {

    @BeforeClass
    public static void setup() {
    	String reportoffset = "reports/site/extent/testreport.html";
        ExtentCucumberFormatter.initiateExtentCucumberFormatter(new File(reportoffset), true);
        ExtentCucumberFormatter.loadConfig(new File("src/main/resources/extent-config.xml"));
        Map systemInfo = new HashMap();
        systemInfo.put("PPE Build", findSWVersion());
        ExtentCucumberFormatter.addSystemInfo(systemInfo);
    }

    private static String findSWVersion () {
		// Temp Test colution
    	String sw = CommandExecutor.execCurlSoftwareVersion("https://dit.offers.vodafone.com/");
    	//sw = StringUtils.replace(sw, "\n", " ");
    	sw = StringUtils.replace(sw, "\t", " ");
    	sw = StringUtils.replace(sw, "Manifest", "");
    	String softwareVersion = StringUtils.replace(sw, "date/time", "");
    	return softwareVersion;
	}
}
