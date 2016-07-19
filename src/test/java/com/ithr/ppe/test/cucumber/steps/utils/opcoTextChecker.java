package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ithr.ppe.test.cucumber.steps.PurchaseSkyOffersSteps;

public class opcoTextChecker {
	public static Logger log = Logger.getLogger(opcoTextChecker.class);
	// TODO: couple of hard codes for now
	String offset = "partneroffers.88c34b82.";
	String file = "PPEWebapp-XX.properties";
	String reference = "";
	String opco = "";
	String result = "";
	FileInputStream inputStream;
	private static Properties prop = new Properties();
	
    // TODO move these so the are read in from a file once we know how many we need to do 
	// they actually come from a file in ppe-parent/web/src/main/resources
	// named like PPEWebapp-GB.properties
	// so we locate the correct key and grabe the text
	// UK : partneroffers.88c34b82.renderOffers.noText.subscription=You have no subscriptions. Please take a look at the available offers.
    // DE : partneroffers.88c34b82.renderOffers.noText.subscription=Du hast noch kein aktives Entertainment-Paket
	

	private String formFileRef() {
		// actual file is "file" and "opco" and "path"
		String tmpopco = opco.toUpperCase();
		String actFile = file.replace("XX", tmpopco);
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
		//find the property then compare it
		String tocompare = GetProperty(offset + "success.notificationMsg.offer");
		log.info("Reference Text is:        " + tocompare);
		return onpage.equals(tocompare);
	}
	
	/*
	 *	
	final String gbSubscriptionText = "You have no subscriptions. Please take a look at the available offers.";
    final String deSubscriptionText = "Du hast noch kein aktives Angebot gebucht";
    final String ieSubscriptionText = "You have no subscriptions. Please take a look at the available offers.";
    
    final String gbConfirmText = "Your entertainment selection has been confirmed";
    final String deConfirmText = "Danke für Deine Buchung!";
    //final String deConfirmText = "Genieße jetzt Vodafone MobileTV mit dem Sky Fußball-Bundesliga-Paket";
    final String ieConfirmText = "Your entertainment selection has been confirmed";
   
    
	public boolean checkSubscriptionText(String opco, String onpage) {
	
		
		switch (opco) {
			case "gb" :	return onpage.equals(gbSubscriptionText);
			case "de" : return onpage.equals(deSubscriptionText);
			case "ie" :	return onpage.equals(ieSubscriptionText);
			default: return false;
		}		
	}
	
	public boolean checkConfirmText(String opco, String onpage) {
		
		switch (opco) {
			case "gb" :	return onpage.equals(gbConfirmText);
			case "de" : return onpage.equals(deConfirmText);
			case "ie" :	return onpage.equals(ieConfirmText);
			default: return false;
		}		
	}
	*/
}
