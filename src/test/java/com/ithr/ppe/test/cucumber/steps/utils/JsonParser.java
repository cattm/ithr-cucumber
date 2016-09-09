package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.ithr.ppe.test.base.StepBase;
import com.ithr.ppe.test.commons.CommandExecutor;
// TODO: Review try/catch blocks ensure we fit in with general strategy

public class JsonParser {
	private static JsonParser instance = null;
	
	private String fileToParse = "";
	private JSONObject jsonObject;
	public static Logger log = Logger.getLogger(JsonParser.class);
	
	protected JsonParser () {
		
	}
	
	public static JsonParser getInstance() {
		if (instance == null) {
	         instance = new JsonParser();
	      }
	      return instance;
	}
	
	public void initialise(String path, String partialref) {
		fileToParse = findActualFile(path, partialref);
		jsonObject = getStartPoint();
	}
	
	public void initialise (String file) {
		fileToParse = file;
		jsonObject = getStartPoint();	
	}
	
	// TODO: this method is implemented in 2 places - sort it out please
	private String findActualFile (String path, String fileref) {
		// assumes full path to file and first part of filename
		// assumes the file will be of the form "path/Blah blah v2.0.json" and we are given "path/Blah blah"
		String compare = fileref + " v";
		String check = CommandExecutor.execFindExactJsonFile(path, compare);
		return check;
	}
	
	public String stripHTML(String withhtml) {
		return Jsoup.parse(withhtml).text();
	}
	
	public static String readFile(String filename) {
	    String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	private JSONObject getStartPoint () {
		 String jsontextfile = fileToParse;
		 log.info("checking file : " + jsontextfile);
		 String jsondata = readFile(jsontextfile);
		 // search for BOM and replace if required
		 String nobomjson = jsondata.replace("\uFEFF", "");
		 return new JSONObject(nobomjson);
	}
	
	
	public String getHeading() {
		String heading = (String) jsonObject.get("heading").toString();
		return heading;
		
	}
	private JSONObject navigateToPurchase(JSONObject object) {
		JSONObject outcome = null;
		try {                 
            JSONObject pages = object.getJSONObject("pages");        
            outcome =  pages.getJSONObject("purchase");
                 
        } catch (Exception e) {
        	log.error("Navigate to Purchase has failed for this file" + e);
            e.printStackTrace();
        }
		return outcome;
	}
	
	private JSONObject navigateToDetails(JSONObject object) {
		JSONObject outcome = null;
		try {                 
            JSONObject pages = object.getJSONObject("pages");        
            JSONObject purchase =  pages.getJSONObject("purchase");
            outcome = purchase.getJSONObject("details");         
        } catch (Exception e) {
        	log.error("Navigate to Details has failed for this file" + e);
            e.printStackTrace();
        }
		return outcome;
	}
	
	private JSONObject navigateToCancel(JSONObject object) {
		JSONObject outcome = null;
		try {                 
            JSONObject pages = object.getJSONObject("pages");        
            outcome =  pages.getJSONObject("cancellation");
                 
        } catch (Exception e) {
        	log.error("Navigate to Cancel has failed for this file" + e);
            e.printStackTrace();
        }
		return outcome;
	}
	
	private JSONObject navigateToCancelDetails(JSONObject object) {
		JSONObject outcome = null;
		try {                 
            JSONObject pages = object.getJSONObject("pages");        
            JSONObject purchase =  pages.getJSONObject("cancellation");
            outcome = purchase.getJSONObject("details");         
        } catch (Exception e) {
        	log.error("Navigate to Cancel Details has failed for this file" + e);
            e.printStackTrace();
        }
		return outcome;
	}
	
	public String getOffersTitle () {
		String title = "";	
        JSONObject details = navigateToDetails(jsonObject);
        title = details.getString("title").toString();
        log.info("Purchase:Details:Title: " +  title);       
        return title;
	}
	public String getOffersText () {

		String details = "";			 
		JSONObject detail = navigateToDetails(jsonObject);         
		details = detail.getString("text").toString();
		log.debug("Purchase:Details:Text: " +  details);           
      
		return details;
	}

	public String getOffersTnCText() {
		String details = "";			 
		JSONObject detail = navigateToDetails(jsonObject);         
		JSONObject checkbox = detail.getJSONObject("checkbox");
		details = checkbox.getString("label").toString();
		log.debug("Purchase:Details:checkbox:label: " +  details);           
		
		return details;
	}
	
	public String getOffersOkButton () {

		String label = "";			 
		JSONObject detail = navigateToDetails(jsonObject);         
		JSONObject okbutton = detail.getJSONObject("okButton");
		label = okbutton.getString("label").toString();
		log.debug("OK Button String is : " +  label);               
		return label;
	}
	public String getSubscribeSuccessTitle() {
		String title = "";
		JSONObject subscription = navigateToPurchase(jsonObject);         
		JSONObject success = subscription.getJSONObject("success");
		title = success.getString("What happens next?").toString();
		log.debug("Success Title : " +  title);   
		return title;
	}
	
	public String getSubscribeSuccessText() {
		String text = "";
		JSONObject subscription = navigateToPurchase(jsonObject);         
		JSONObject success = subscription.getJSONObject("success");
		text = success.getString("text").toString();
		log.debug("Success Text : " +  text);
		return text;
	  
	}

	public String getCancelOkButton() {
		String label = "";			 
		JSONObject detail = navigateToCancelDetails(jsonObject);         
		JSONObject okbutton = detail.getJSONObject("okButton");
		label = okbutton.getString("label").toString();
		log.debug("OK Button String is : " +  label);               
		return label;
	}
	
	public String getCancelTitle () {
		String title = "";	
        JSONObject details = navigateToCancelDetails(jsonObject);
        title = details.getString("title").toString();
        log.debug("Cancel:Details:Title: " +  title);       
        return title;
	}
	
	public String getCancelText () {

		String details = "";			 
		JSONObject detail = navigateToCancelDetails(jsonObject);         
		details = detail.getString("text").toString();
		log.debug("Cancel:Details:Text: " +  details);           
      
		return details;
	}
	
}
