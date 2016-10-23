package com.ithr.ppe.bdd.reporter.reporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadReportProperties {
	public static String REPORT_PROPERTY_BASE = "src/test/resources/";
	public static boolean DISPLAY_FEATURE_TABLE;
	public static boolean SEND_EMAIL;
	public static boolean DO_PDF;
	public static String REPORT_TITLE;
	public static String REPORT_BASE;
	public static String JSON_BASE;
	
	private static boolean loaded = false;
	
	
	private static Properties prop = new Properties();
	public static Logger log = Logger.getLogger(LoadReportProperties.class); 
	 
	 public static void loadPropertyFile( String filename) throws IOException {
		 // TODO: Fix this hard wired offset in fis should just be resources/properties
              
         if (loaded == false) {
        	 FileInputStream fis = new FileInputStream( REPORT_PROPERTY_BASE + filename);
             prop.load(fis);
        	 loadProperties();
        	 loaded = true;
         }         
	 }
	 
	 private static void loadProperties() {     
         DISPLAY_FEATURE_TABLE = Boolean.parseBoolean(prop.getProperty("report.display.features"));
         SEND_EMAIL = Boolean.parseBoolean(prop.getProperty("report.send.email"));
         DO_PDF = Boolean.parseBoolean(prop.getProperty("report.generate.pdf"));
         REPORT_TITLE = prop.getProperty("report.project.title");
         REPORT_BASE = prop.getProperty("report.generate.pathbase");
         JSON_BASE = prop.getProperty("report.json.path");
	 }

}
