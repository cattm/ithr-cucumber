package com.ithr.ppe.bdd.cucumber.steps.utils;
/**
 * Implements the Dropbox purchase model
 * 
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.commons.CommonConstants;
import com.ithr.ppe.bdd.cucumber.pages.partners.DropBoxDownload;
import com.ithr.ppe.bdd.cucumber.pages.partners.DropBoxSuccess;
import com.ithr.ppe.bdd.cucumber.steps.interfaces.IVFPartner;

public class VFDropboxFacade implements IVFPartner {
	
	public static Logger log = Logger.getLogger(VFDropboxFacade.class);

	
	public boolean PurchaseOffer(WebDriver driver) {
		DropBoxSuccess dbs = new DropBoxSuccess(driver);
		if (!dbs.bodyLoaded()) {
			log.error("Dropbox Success Page body did not load in time");
		}
				
		
		String nt = dbs.getNotificicationText();	
		log.info("Details: " + StringUtils.replace(dbs.getDetailsText(), "\n", " ") );
		JsonParser parser = JsonParser.getInstance();
		ErrorCollector.verifyEquals(StringUtils.replace(dbs.getDetailsText(), "\n", " "), parser.stripHTML(parser.getSubscribeSuccessText()),"Dropbox success page Text is not correct");
				
		dbs.clickContinue();		
		return true;
	}
	
	public boolean MoveToDownload(WebDriver driver) {
		DropBoxDownload dbl = new DropBoxDownload(driver);
		if (!dbl.bodyLoaded()) {
			 log.error("Dropbox download Page body did not load in time");
		}
		
		String dltext = dbl.getDownlaodText();
		log.info("Download text is: " + dltext);
		return true;
	}
}