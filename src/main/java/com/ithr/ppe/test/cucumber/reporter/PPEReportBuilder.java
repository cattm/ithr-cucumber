package com.ithr.ppe.test.cucumber.reporter;

import java.util.List;

import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.ReportParser;
import net.masterthought.cucumber.ReportResult;
import net.masterthought.cucumber.json.Feature;

import org.apache.log4j.Logger;


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
			//there is a utility to do this - which I am not sure works
			// build a code solution instead
			/*
			 * date=`date +%Y%m%d_%H%M%S`
			 * cd reports/site/cucumber-reports
			 * st=`wkhtmltopdf $flist cucumer-report-${date}.pdf`
			 * exit 0
			 */
			// And I have a command executor class
			
		}
		
		

}
