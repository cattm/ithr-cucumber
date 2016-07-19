package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.ithr.ppe.test.base.StepBase;

public class JsonParser {
	private String fileToParse = "";
	private JSONObject jsonObject;
	public static Logger log = Logger.getLogger(JsonParser.class);
	
	public JsonParser (String file) {
		fileToParse = file;
		jsonObject = getStartPoint();
		
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
		 log.debug("checking file : " + jsontextfile);
		 String jsonData = readFile(jsontextfile);
		 return new JSONObject(jsonData);
	}
	
	private JSONObject navigateToPurchase(JSONObject object) {
		JSONObject outcome = null;
		try {                 
            JSONObject pages = object.getJSONObject("pages");        
            outcome =  pages.getJSONObject("purchase");
                 
        } catch (Exception e) {
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
            e.printStackTrace();
        }
		return outcome;
	}
	
	public String getOffersTitle () {
		String title = "";	
        JSONObject details = navigateToDetails(jsonObject);
        title = details.getString("title").toString();
        log.debug("Purchase:Details:Title: " +  title);       
        return title;
	}
	public String getOffersText () {

		String details = "";			 
		JSONObject detail = navigateToDetails(jsonObject);         
		details = detail.getString("text").toString();
		log.debug("Purchase:Details:Text: " +  details);           
      
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

}
