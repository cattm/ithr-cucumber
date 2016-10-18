package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;

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
		//String compare = fileref + " v";
		String compare = fileref;
		String check = CommandExecutor.execFindExactJsonFile(path, compare);
		return check;
	}
	
	public String stripHTML(String withhtml) {
		return Jsoup.parse(withhtml).body().text();
	}
	
    public String toUTF (String source) {
    	try { 		
    		byte[] utf8Bytes = source.getBytes("UTF8");
    	    String text = new String(utf8Bytes,"UTF8");
    	    return text;
    	}
    	catch (UnsupportedEncodingException e) {
    	    e.printStackTrace();
    	}
    	return "XX";
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
	        br.close();
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
	
	private JSONObject navigateToCancelDetails2(JSONObject object) {
		JSONObject outcome = null;
		try {                 
            JSONObject pages = object.getJSONObject("pages");        
            JSONObject purchase =  pages.getJSONObject("cancellation");
            outcome = purchase.getJSONObject("details2");         
        } catch (Exception e) {
        	log.error("Navigate to Cancel Details2 has failed for this file" + e);
            e.printStackTrace();
        }
		return outcome;
	}
	
	private JSONObject navigateToCancelSuccess(JSONObject object) {
		JSONObject outcome = null;
		try {                 
            JSONObject pages = object.getJSONObject("pages");        
            JSONObject purchase =  pages.getJSONObject("cancellation");
            outcome = purchase.getJSONObject("success");         
        } catch (Exception e) {
        	log.error("Navigate to Cancel Success has failed for this file" + e);
            e.printStackTrace();
        }
		return outcome;
	}
	public String getOffersTitle () {
		String title = "";	
		try {
			JSONObject details = navigateToDetails(jsonObject);
			title = details.getString("title").toString();
			log.info("Purchase:Details:Title: " +  title);
		} catch (Exception e) {
			log.error("No Title in JSON " + e);
			title = "No Title in JSON";
		}
        return title;
	}
	public String getOffersText () {

		String details = "";		
		try {
			JSONObject detail = navigateToDetails(jsonObject);         
			details = detail.getString("text").toString();		         
		} catch (Exception e) {
			log.error("No Details in JSON " + e);
			details = "No Details in JSON";
		}
		log.debug("Purchase:Details:Text: " +  details);  
		return details;
	}

	//TODO: Extend the try blocks to every function
	public String getOffersTnCText() {
		String details = "";		
		try {
		    JSONObject detail = navigateToDetails(jsonObject);         
		    JSONObject checkbox = detail.getJSONObject("checkbox");
		    details = checkbox.getString("label").toString();          
		}catch (Exception e) {
			log.error("No TNC Text in JSON " + e);
			details = "No TNC Text in JSON";
		}
		log.debug("Purchase:Details:checkbox:label: " +  details);    
		return details;
	}
	
	public String getOffersOkButton () {

		String label = "";			 
		try {
		    JSONObject detail = navigateToDetails(jsonObject);         
		    JSONObject okbutton = detail.getJSONObject("okButton");
		    label = okbutton.getString("label").toString();
		} catch (Exception e) {
			log.error("No Offers Ok Button text in JSON " + e);
			label = "No Offers Ok Button text in JSON";
		}
		
		log.debug("OK Button String is : " +  label);               
		return label;
	}
	

	public String getSubscribeSuccessTitle() {
		String title = "";
		try {
		   JSONObject subscription = navigateToPurchase(jsonObject);         
		   JSONObject success = subscription.getJSONObject("success");
		   title = success.getString("What happens next?").toString();
		}catch (Exception e) {
			log.error("No Subscription success title in JSON " + e);
			title = "No Subscription success title in JSON";
		}
		
		log.debug("Success Title : " +  title);   
		return title;
	}
	
	public String getSubscribeSuccessText() {
		String text = "";
		try {
		    JSONObject subscription = navigateToPurchase(jsonObject);         
		    JSONObject success = subscription.getJSONObject("success");
		    text = success.getString("text").toString();
		}catch (Exception e) {
			log.error("No Subscription success text in JSON " + e);
			text = "No Subscription success text in JSON";
		}
		log.debug("Success Text : " +  text);
		return text;
	  
	}

	public String getCancelOkButton() {
		String label = "";
		try {
		    JSONObject detail = navigateToCancelDetails(jsonObject);         
		    JSONObject okbutton = detail.getJSONObject("okButton");
		    label = okbutton.getString("label").toString();
		} catch (Exception e) {
			log.error("No Cancel Ok Text in JSON " + e);
			label = "No Cancel Ok Text in JSON";
		}
		
		log.debug("OK Button String is : " +  label);               
		return label;
	}
	
	public String getCancelConfirmButton() {
		String label = "";
		try {
		    JSONObject detail = navigateToCancelDetails2(jsonObject);         
		    JSONObject okbutton = detail.getJSONObject("okButton");
		    label = okbutton.getString("label").toString();
		} catch (Exception e) {
			log.error("No Cancel Ok Text in JSON " + e);
			label = "No Cancel Ok Text in JSON";
		}
		
		log.debug("OK Button String is : " +  label);               
		return label;
	}
	public String getCancelTitle () {
		String title = "";	
        try {
		JSONObject details = navigateToCancelDetails(jsonObject);
        title = details.getString("title").toString();
        }catch (Exception e) {
			log.error("No Cancel Title Text in JSON " + e);
			title = "No Cancel Title Text in JSON";
		}
        
        log.debug("Cancel:Details:Title: " +  title);       
        return title;
	}
	
	public String getCancelText () {

		String details = "";			 
		try {
		JSONObject detail = navigateToCancelDetails(jsonObject);         
		details = detail.getString("text").toString();
		} catch (Exception e) {
			log.error("No Cancel Text in JSON " + e);
			details = "No Cancel Text in JSON";
		}
		
		log.debug("Cancel:Details:Text: " +  details);           
      
		return details;
	}
	
	public String getCancelSuccessText() {
		String details = "";			 
		try {
		JSONObject detail = navigateToCancelSuccess(jsonObject);         
		details = detail.getString("text").toString();
		} catch (Exception e) {
			log.error("No Cancel Success Text in JSON " + e);
			details = "No Cancel Success Text in JSON";
		}
		
		log.debug("Cancel:Success:Text: " +  details);           
      
		return details;
	}
	
}
