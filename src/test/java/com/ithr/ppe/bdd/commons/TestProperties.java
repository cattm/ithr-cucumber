package com.ithr.ppe.bdd.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestProperties {
	public static String TEST_PROPERTY_BASE = "src/test/resources/";
	public static String USER_BASEURL;
	public static String ADMIN_BASEURL;
	public static String TEST_REFDIR;
	public static String LOGGERFILE;
	public static String SPOTIFYBASE;
	public static String PINCODE;
	public static String DRIVER;
	public static boolean DO_ASSERTCHECKS;
	public static String DO_SCREENSHOTS;
	private static boolean loaded = false;
	
	private static Properties prop = new Properties();
	public static Logger log = Logger.getLogger(TestProperties.class); 
	 
	 public static void loadPropertyFile( String filename) throws IOException {
		 // TODO: Fix this hard wired offset in fis should just be resources/properties
              
         if (loaded == false) {
        	 ///Users/marcus/Documents/ithr/cucumber only needed if running direct from cucumber file otherwise relative path works
        	 FileInputStream fis = new FileInputStream( TEST_PROPERTY_BASE + filename);
             prop.load(fis);
        	 loadProperties();
 
        	 loaded = true;
         }         
	 }
 
	 private static void loadProperties() {
         USER_BASEURL = prop.getProperty("dit.userbase");
         ADMIN_BASEURL = prop.getProperty("dit.adminbase");
         SPOTIFYBASE = prop.getProperty("dit.spotifybase");
         PINCODE = prop.getProperty("dit.pintouse");
         TEST_REFDIR = prop.getProperty("test.testrefdir");
         LOGGERFILE = prop.getProperty("test.logger");
         DRIVER = prop.getProperty("test.driver");
         DO_ASSERTCHECKS = Boolean.parseBoolean(prop.getProperty("test.doasserts"));
         DO_SCREENSHOTS = prop.getProperty("test.allimages");
	 }

	
}
