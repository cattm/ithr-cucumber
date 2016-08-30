package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.CommonConstants;
import com.ithr.ppe.test.cucumber.pages.partners.DropBoxDownload;
import com.ithr.ppe.test.cucumber.pages.partners.DropBoxSuccess;

public class VFDropboxActivities implements VFPartnerInterface {

	/*
	page has:
	    DropboxSuccess

		Continue button - .btn.event-btn.cf.btn--customButton
		Notifcation text   - div [class='notification-jsx  pulse']
		And some 25 GB di spazio su Dropbox  - .details-content

        DropBoxDownload
        
		final page has:
		div [class=‘open’]
		returns  (4 paras)
		<p>Hai gi&agrave; installato Vodafone Backup+?</p>
		<p>Scarica gratuitamente Vodafone Backup+ per tenere i contenuti del tuo smartphone sempre al sicuro</p>
    */
	
	public static Logger log = Logger.getLogger(VFDropboxActivities.class);

	public boolean PurchaseOffer(WebDriver driver, String opco) {
		
		DropBoxSuccess dbs = new DropBoxSuccess(driver);
		
		// TODO: just click first time through  - add checks later on
		try {
			dbs.bodyLoaded();
		} catch (InterruptedException e) {
			log.error("Interrupted exception while loading success page" + e);
		}
		
		String nt = dbs.getNotificicationText();
		log.info("Notification test: " + nt);
		String dt = dbs.getDetailsText();
		log.info("Details text : " + dt);
		dbs.clickContinue();
	
		try {
			Thread.sleep(CommonConstants.SLOW);
		} catch (InterruptedException e1) {
			log.info("TODO: Remove this test delay code ");
		}
		
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