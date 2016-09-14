package com.ithr.ppe.test.cucumber;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

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
        systemInfo.put("PPE Build", "ppe_r16-09-1_rc15 : 20160913-0853");
        systemInfo.put("Cucumber version", "v1.2.3");
        systemInfo.put("Extent Cucumber Reporter version", "v1.1.0");
        ExtentCucumberFormatter.addSystemInfo(systemInfo);
    }

}
