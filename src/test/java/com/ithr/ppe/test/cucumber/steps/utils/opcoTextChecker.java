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
	
	public boolean checkProcessMsg(String onpage) {
		String tocompare = GetProperty(offset + "completing.processMsg");
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}

	public boolean checkCompleteMsg(String onpage) {
		String tocompare = GetProperty(offset + "success.notificationMsg.offer");
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}
	
	public boolean checkSkySubscibedText(String onpage) {
		// TODO: this is Almost the wrong string to check - and it should not be hardcoded
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
		// TODO: this is Almost certainly the wrong string to check - and it should not be hardcoded
		String esstr1 = "TODO: ";
		String destr1 = "partneroffers.landing-hard.heading.spotify";
		String gbstr1 = "partneroffers.29f6b87a-e124-4026-a4e9-ae7b5bd1e223.home.heading";
		String itstr1 = "partneroffers.landing-hard.heading.spotify";
		String tocompare = "";
		
		switch (opco) {
				case "es" : tocompare=GetProperty(esstr1);
							break;
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

	public boolean checkNowTVSubscribedText(String onpage) {
		//TODO: find another way
		String gbstr1 = "partneroffers.landing-hard.confirmation.heading.nowtv ";
		String tocompare = "";
		switch (opco) {

		case "gb" :	tocompare=GetProperty(gbstr1);
					break;
	
		default: break;
		}
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}
	
	public boolean checkNetflixSubscribedText(String onpage) {
		//TODO: find another way
		String gbstr1 = "partneroffers.bdbb8003-24be-474a-b440-06c176f1764f.home.heading";
		
		String tocompare = "";
		switch (opco) {

		case "gb" :	tocompare=GetProperty(gbstr1);
					break;
	
		default: break;
		}
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}
	
	/*
	 * Examples :
	 * 
partneroffers.13106694-63f9-4b5d-8b70-1ba879ebadab.home.heading=Sky Sports Mobile TV
partneroffers.29f6b87a-e124-4026-a4e9-ae7b5bd1e223.home.heading=Spotify Premium
partneroffers.2dc23b84-9179-4f56-8b00-9f0ba58fc7b1.home.heading=Sky Sports Mobile TV
partneroffers.930139c0-0011-41ad-8c93-d1031f3a0078.home.heading=NOW TV Entertainment
partneroffers.bdbb8003-24be-474a-b440-06c176f1764f.home.heading=Netflix
partneroffers.d7f251ca-da2e-45a8-9088-1b7fa9e547eb.home.heading=Sky Sports Mobile TV
partneroffers.e3471bbb-7a7b-4b03-a1ea-16b0fb394255.home.heading=Spotify Premium
	 *
	 */
}
