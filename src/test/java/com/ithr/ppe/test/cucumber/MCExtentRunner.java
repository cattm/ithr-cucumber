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
                    plugin = {"com.ithr.ppe.test.cucumber.ExtentCucumberFormatter"})


public class MCExtentRunner {

    @BeforeClass
    public static void setup() {
        ExtentCucumberFormatter.initiateExtentCucumberFormatter();
        ExtentCucumberFormatter.loadConfig(new File("src/main/resources/extent-config.xml"));

       // ExtentCucumberFormatter.addSystemInfo("Browser Name", "Firefox");
       // ExtentCucumberFormatter.addSystemInfo("Browser version", "v47.0.1");
        //ExtentCucumberFormatter.addSystemInfo("Selenium version", "v2.53.1");

        Map systemInfo = new HashMap();
        systemInfo.put("PPE Build", findSWVersion());
        systemInfo.put("Cucumber version", "v1.2.3");
        systemInfo.put("Extent Cucumber Reporter version", "v1.1.0");
        ExtentCucumberFormatter.addSystemInfo(systemInfo);
    }

    private static String findSWVersion () {
		// Temp Test colution
    	String sw = CommandExecutor.execCurlSoftwareVersion("https://dit.offers.vodafone.com/");
    	sw = StringUtils.replace(sw, "\n", " ");
    	sw = StringUtils.replace(sw, "\t", " ");
    	sw = StringUtils.replace(sw, "Manifest", "");
    	String softwareVersion = StringUtils.replace(sw, "date/time", "");
    	return softwareVersion;
	}
}
