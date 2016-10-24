package com.ithr.ppe.bdd.reporter.reporter;

import java.io.File;
import java.util.List;

import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.ReportParser;
import net.masterthought.cucumber.ReportResult;
import net.masterthought.cucumber.json.Feature;

import org.apache.log4j.Logger;

import com.ithr.ppe.bdd.commons.CommandExecutor;


public class PPEReportBuilder  extends ReportBuilder {
		public static Logger log = Logger.getLogger(PPEReportBuilder.class);
		
		private PPEConfiguration myconfiguration;
		private List<String> myjsonFiles;
		private final ReportParser myreportParser;

		public PPEReportBuilder(List<String> jsonFiles, PPEConfiguration configuration) {
			super(jsonFiles, configuration);
			myconfiguration = configuration;
			myjsonFiles = jsonFiles;
			myreportParser = new ReportParser(configuration);
			
		}
		
		public void buildOverview () {
			log.info("BUILDING THE OVERVIEW PAGE");
			
			ReportResult myreportResult;
			List<Feature> features = myreportParser.parseJsonFiles(myjsonFiles);
			if (features.isEmpty()) {
				log.error("NO Features to work with");
			} else {
				log.info(" Feature list to process has Size : " + features.size());
			}
			
	        myreportResult = new ReportResult(features);
	        String time = myreportResult.getBuildTime();	       
	        int count = myreportResult.getAllPassedFeatures();
	        log.info("Passed " + count + " and time is " + time);
	        
	        new OverviewPage(myreportResult, myconfiguration).generatePage();	       
			
		
		}
		
		public void pdfOverview() {
			log.info("PDF THE OVERVIEW PAGE");
			File reportdir = myconfiguration.getReportDirectory();
			String command1 = "./src/test/bin/pdfit.sh " + reportdir.getPath() + "/cucumber-html-reports/";
			String result = CommandExecutor.execCmd(command1, true);
			log.info("outcome of command1 is " + result);
		}
		
		public void cleanArea() {
			log.info("Clean the report Directory");
			/*
			File dirtoclean = myconfiguration.getReportDirectory();
			log.info("Going to clean " + dirtoclean.getPath());
			String command1 = "ls -l " + dirtoclean.getPath() +"/" + " | wc -l";
			String result = CommandExecutor.execCmd(command1, true);
			if (Integer.parseInt(result.trim()) > 1) {
				String command2 = "rm -R " + dirtoclean.getPath() +"/";
				result = CommandExecutor.execCmd(command2, true);
			}
			*/
		}
		
		

}
