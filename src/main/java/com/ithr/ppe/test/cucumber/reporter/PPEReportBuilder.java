package com.ithr.ppe.test.cucumber.reporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.ReportParser;
import net.masterthought.cucumber.ReportResult;
import net.masterthought.cucumber.json.Feature;

import org.apache.log4j.Logger;


public class PPEReportBuilder  extends ReportBuilder {
		public static Logger log = Logger.getLogger(PPEReportBuilder.class);
		private static String sr = "";
		private static String se = "";
		private static boolean errored = false;
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
			
			// we need a velocity page as template
			// This is not straight forward - This is a nested list 
			// can we grab the whole resource section???
			// either as a remote set of objects or copy.......?
			
			// we need some data
			/*
			 * Total Tests
			 * Total Steps
			 * Time - Total; Start;End
			 * Feature View; Step view (nice pictures)
			 * Pass percentage (features and Steps)
			 * Config style stuff; SW Version; Java; Jenkins etc
			 */
			ReportResult myreportResult;
			List<Feature> features = myreportParser.parseJsonFiles(myjsonFiles);
			if (features.isEmpty()) {System.out.println("NO Features to work with");
			} else {
				log.info(" Feature list to process has Size : " + features.size());
			}
			
	        myreportResult = new ReportResult(features);
	        String time = myreportResult.getBuildTime();
	        int count = myreportResult.getAllPassedFeatures();
	        log.info("AHAH Passed " + count + " and time is " + time);
	        new OverviewPage(myreportResult, myconfiguration).generatePage();
		       
			
		
		}
		
		public void pdfOverview() {
			log.info("PDF THE OVERVIEW PAGE");
			//there is a utility to do this
			/*
			 * date=`date +%Y%m%d_%H%M%S`
			 * cd reports/site/cucumber-reports
			 * st=`wkhtmltopdf $flist cucumer-report-${date}.pdf`
			 * exit 0
			 */
			// And I have a command executor class
		}
		
		private static String execCmd(String command, boolean anderror)
	    {
		     String cmdOutput = "";
	         try {
	                Process p = Runtime.getRuntime().exec(command);

	                BufferedReader stdInput = new BufferedReader(new
	                     InputStreamReader(p.getInputStream()));

	                BufferedReader stdError = new BufferedReader(new
	                     InputStreamReader(p.getErrorStream()));

	                log.debug("Here is the standard output of the command:\n");
	                while ((sr = stdInput.readLine()) != null) {
	                    log.debug(sr);
	                    cmdOutput += "\n" + sr;
	                }

	                // read any errors from the attempted command
	                if (anderror) {
	                	log.error("Here is the standard error of the command (if any):\n");
	                	while ((se = stdError.readLine()) != null) {
	                		log.error(se);
	                	}
	                }

	                return cmdOutput;
	            }
	            catch (IOException e) {
	                log.error("exception happened reading file: ");
	                e.printStackTrace();
	                errored = true;
	                return "";
	            }
	    }
		

}
