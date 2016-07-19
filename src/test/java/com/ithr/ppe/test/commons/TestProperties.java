package com.ithr.ppe.test.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestProperties {
	public static String USER_BASEURL;
	public static String ADMIN_BASEURL;
	public static String TEST_REFDIR;
	public static String LOGGERFILE;
	public static boolean DO_ASSERTCHECKS;
	private static boolean loaded = false;
	
	private static Properties prop = new Properties();
	public static Logger log = Logger.getLogger(TestProperties.class); 
	 
	 public static void loadPropertyFile( String filename) throws IOException {
		 // TODO: Fix this hard wired offset in fis should just be resources/properties
              
         if (loaded == false) {
        	 FileInputStream fis = new FileInputStream( "/Users/marcus/Documents/ithr/cucumber/resources/properties/" + filename);
             prop.load(fis);
        	 loadProperties();
        	 System.out.println("Loading Log4j properties " + TestProperties.LOGGERFILE);
   		     PropertyConfigurator.configure(TestProperties.LOGGERFILE);
        	 loaded = true;
         }         
	 }
 
	 private static void loadProperties() {
         USER_BASEURL = prop.getProperty("userbase");
         ADMIN_BASEURL = prop.getProperty("adminbase");
         TEST_REFDIR = prop.getProperty("testrefdir");
         LOGGERFILE = prop.getProperty("logger");
         DO_ASSERTCHECKS = Boolean.parseBoolean(prop.getProperty("doasserts"));
	 }

	
}
