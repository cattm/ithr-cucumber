package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class opcoTextChecker {
	public static Logger log = Logger.getLogger(opcoTextChecker.class);
	// TODO: RESOLVE all Hard Codes in this solution
	private final String offset = "partneroffers.88c34b82.";
	private final String file = "PPEWebapp-XX.properties";
	private String reference = "";
	private String opco = "";
	
	private static Properties prop = new Properties();
	
	private String formFileRef() {
		// actual file is "file" and "opco" and "path"
		String tmpopco = opco;
		String actFile = file.replace("XX", tmpopco.toUpperCase());
		log.info(reference + actFile);
		return  reference + actFile; 
	}
	public opcoTextChecker(String path, String opco) throws IOException {
		reference = path;
		this.opco = opco;
		LoadPropertyFile();
	}
	
	public void LoadPropertyFile () throws IOException {
	
			
		FileInputStream fis = new FileInputStream(formFileRef());
		prop.load(fis);
	
	}
	
	private String GetProperty(String search) {
		return prop.getProperty(search);
	}
	
	public boolean checkSubscriptionText(String onpage) {
		//find the property then compare it
		String tocompare = GetProperty(offset + "renderOffers.noText.subscription");
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}
	
	public boolean checkConfirmText(String onpage) {
		//TODO resolve correct string to parse
		//find the property then compare it
		// or is it completing.processMsg
		String tocompare = GetProperty(offset + "success.notificationMsg.offer");
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}

	public boolean checkSkySubscibedText(String onpage) {
		// TODO: this is probably the wrong string to check - and it should not be hardcoded
		String destr1 = "partneroffers.landing-hard.heading.skytv";
		String gbstr1 = "partneroffers.landing-hard.confirmation.heading.sky";
		String tocompare = "";
	
		switch (opco) {
		case "gb" :	tocompare=GetProperty(gbstr1);
					break;
		case "de" : tocompare=GetProperty(destr1);
					break;
		case "ie" :	tocompare=GetProperty(gbstr1);
					break;
		default: break;
		}
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}
	public boolean checkSpotifySubscibedText(String onpage) {
		// TODO: this is probably the wrong string to check - and it should not be hardcoded
		String destr1 = "partneroffers.landing-hard.heading.spotify";
		String gbstr1 = "partneroffers.landing-hard.confirmation.heading.spotify";
		String itstr1 = "partneroffers.landing-hard.heading.spotify";
		String tocompare = "";
		
		switch (opco) {
				case "gb" :	tocompare=GetProperty(gbstr1);
							break;
				case "de" : tocompare=GetProperty(destr1);
							break;
				case "ie" :	tocompare=GetProperty(gbstr1);
							break;
				case "it" :	tocompare=GetProperty(itstr1);
							break;
				default: break;
		}
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}

}
