package com.ithr.ppe.test.cucumber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ithr.ppe.test.commons.CommandExecutor;
import com.ithr.ppe.test.commons.DateStamp;
import com.ithr.ppe.test.cucumber.reporter.LoadReportProperties;
import com.ithr.ppe.test.cucumber.reporter.PPEConfiguration;
import com.ithr.ppe.test.cucumber.reporter.PPEReportBuilder;

public class App 
{
	
	private static String beginTime;
	private static String endTime;
	private static String propertyFile = "report.properties";
	private static boolean displayFeature = false;
	private static boolean createPDF = false;
	private static boolean sendEmail = false;
	private static String reportTitle = "";
	private static String reportBase = "";
	private static String jsonBase = "";
	private static String produceoverview = "";

	public static Logger log = Logger.getLogger(App.class);
    public static void main( String[] args )
    {
    	log.info( "Started Building ITHR Report" );   	
       	try {
    			loadProperties();
    	} catch (IOException e) {
    			log.error("cant load file " + e);
    	}
       	
    	log.info("Generate report? " + produceoverview );
    	
    	if (produceoverview.toLowerCase().contains("yes")) {
    		// TODO: mat need to have a sleep loop with a max timeout 
    		log.info( "Marcus is checking for cucumber.json files" );
    		// TODO - this is wrong - I just want to populate the report at the moment
    		DateStamp date = new DateStamp();;
    		endTime = date.getReportDateFormat();
    		log.info("End " + endTime);
      
    		//TODO: this should be a property in both halves
    		String result = CommandExecutor.findValidResults("reports/cucumber.json");
    		log.info("cucumber file is size : " + result);
    		Integer fred = new Integer(result);
    		if (fred > 0) {
    			produceLocalReport();
    		} else {
    			log.error("Could not find required json files");
    		}
    	}
    	log.info( "Finished Building ITHR Report" );
    }
    
    
    private static void produceLocalReport () {
    	log.info("I will produceLocalReport");
 	
		File reportOutputDirectory = new File(reportBase);    
		PPEConfiguration configuration = new PPEConfiguration(reportOutputDirectory, reportTitle);	
		
		
		String jenkinsBasePath = "";
		log.info("XX" + System.getenv("BUILD_NUMBER") + "XX");
		String tmp = System.getenv("BUILD_NUMBER");
		String buildNumber;
		if (tmp == null) {
			buildNumber = "100";
		}
		else {
			buildNumber = tmp;
		}
		
		boolean runWithJenkins = false;
		boolean parallelTesting = false;
		
		// optional configuration
		configuration.setParallelTesting(parallelTesting);
		configuration.setJenkinsBasePath(jenkinsBasePath);
		configuration.setRunWithJenkins(runWithJenkins);
		configuration.setBuildNumber(buildNumber);
		configuration.setDisplayFeature(displayFeature);
		configuration.setEndTime(endTime);
		
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add(jsonBase + "/cucumber.json");
		
		// TODO: Add a test to see if these files exist and if they do......
		//jsonFiles.add("target/reports/cucumber_rerun.json");
		//jsonFiles.add("target/reports/cucumber-usage.json");	
		
		// only get here if we dont crash!			
		PPEReportBuilder reportbuilder = new PPEReportBuilder(jsonFiles, configuration);
		reportbuilder.cleanArea();
		// first build normal reports 
		reportbuilder.generateReports();		
		// now add the special report 
		reportbuilder.buildOverview();
		
	
		
		if (createPDF) {
			reportbuilder.pdfOverview();
		}
		log.info("method to produce a report is done");
	}
    
    
    private static void loadProperties () throws IOException {
		LoadReportProperties.loadPropertyFile(propertyFile);	
    	
		displayFeature = LoadReportProperties.DISPLAY_FEATURE_TABLE;
		createPDF = LoadReportProperties.DO_PDF;
		sendEmail = LoadReportProperties.SEND_EMAIL;
		reportTitle = LoadReportProperties.REPORT_TITLE;   
		reportBase = LoadReportProperties.REPORT_BASE;
		jsonBase = LoadReportProperties.JSON_BASE;
		produceoverview = System.getProperty("report.run", "no");
	}
}
