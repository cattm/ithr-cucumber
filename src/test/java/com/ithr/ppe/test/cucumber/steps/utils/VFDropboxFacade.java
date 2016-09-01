package com.ithr.ppe.test.cucumber.steps.utils;
/**
 * Implements the Dropbox purchase model
 * 
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.cucumber.pages.partners.DropBoxDownload;
import com.ithr.ppe.test.cucumber.pages.partners.DropBoxSuccess;

public class VFDropboxFacade implements IVFPartner {
	
	public static Logger log = Logger.getLogger(VFDropboxFacade.class);

	
	public boolean PurchaseOffer(WebDriver driver) {
				DropBoxSuccess dbs = new DropBoxSuccess(driver);
		try {
			dbs.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading success page" + e);
		}
		
		String nt = dbs.getNotificicationText();
		//log.info("Notification test: " + nt);
		// because this is a VF page - include a check of content
		//replace "\n" and strip html	
		log.info("details: " + StringUtils.replace(dbs.getDetailsText(), "\n", "") );
		//TODO: cant do checking yet
		//ErrorCollector.verifyEquals(StringUtils.replace(dbs.getDetailsText(), "\n", ""), parser.stripHTML(parser.getSubscribeSuccessText()),"Dropbox success page Text is not correct");
				
		dbs.clickContinue();		
		return true;
	}
	
	public boolean MoveToDownload(WebDriver driver) {
		DropBoxDownload dbl = new DropBoxDownload(driver);
		try {
			dbl.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading success page" + e);
		}
		String dltext = dbl.getDownlaodText();
		log.info("Download text is: " + dltext);
		return true;
	}
}