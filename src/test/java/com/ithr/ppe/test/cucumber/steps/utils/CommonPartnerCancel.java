package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.commons.TestProperties;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerCancel;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.interfaces.IPartnerCancel;

public class CommonPartnerCancel implements IPartnerCancel {
	public static Logger log = Logger.getLogger(CommonPartnerCancel.class);
	private static JsonParser parser = null;
	private static opcoTextChecker checker = null;

	public boolean selectPartnerToCancel(String definedby, UserEntertainment entpage) {
		log.info("looking to cancel " + definedby);
		entpage.clickOfferToManage(definedby);
		return true;
	}

	
	private boolean cancelInER(WebDriver driver, Customer customer) {
		//need long msisdn
		//. need urlbase from properties
		String msisdn = customer.getCountryCode() + customer.getMsisdn();
		String urlbase = TestProperties.USER_BASEURL;
		return AdminFacade.deletePartnerSubscriptionsInER(driver, urlbase, customer.getOpco(), customer.getPartner().toString(), msisdn);
	}
	public boolean cancelTheOffer(WebDriver driver, Customer customer) {

		BasicPartnerCancel cancel = new BasicPartnerCancel(driver);
		cancel.bodyLoaded();
		// check cancel text
		verifyCancelText(cancel, customer);
		
		log.info("going to cancel");
		cancel.clickCancel(parser.getCancelOkButton());
		
		// and then more details
		// TODO: there is text to verify before we confirm	
		cancel.clickCancel(parser.getCancelConfirmButton());
		
		// TODO: And then there is more text to check and confirm		
		if (verifyConfirm(cancel)) {
			log.info("Cancel Text is OK");
			// TODO: And after ER there will be different text again?
			return cancelInER(driver, customer);
		} else {
			log.info("Cancel confirm text check was not a good outcome");
			return false;
		}

	}
	
	private boolean verifyConfirm(BasicPartnerCancel cancel) {
		String outcome = cancel.getSuccessText();
		log.info("Success text is     : " + outcome);
		return checker.checkConfirmCancelText(outcome);
	}
	
	public boolean verifyCancelText(BasicPartnerCancel cancel, Customer customer ) {	
		log.info("TEST: Verify the Cancel Offer");	
		String textstripped = parser.stripHTML(parser.getCancelTitle());	
		ErrorCollector.verifyEquals(cancel.getCancelOffer(),textstripped, "The Cancel title is incorrect");
		
		// check the text bullets from text
		String crstripped = StringUtils.replace(cancel.getCancelDetail(), "\n", " ");
		String ntextstripped = parser.stripHTML(parser.getCancelText());
		ErrorCollector.verifyEquals(crstripped, ntextstripped,"The Cancel detail is incorrect");
		
		return true;		    
	}

	public boolean verifySuccessText(WebDriver driver) { 
		// TODO: this is a final test and is really an epilog test in my opinion consider redesign
		BasicPartnerCancel page = new BasicPartnerCancel(driver);
		if (!page.bodyLoaded()) {
			 log.error("Cancel Page body did not load in time");
		}
		// So thats getSuccessText as well
		// this offer has been scheduled for cancellation at the end of your current billing cycle.
		// partneroffers.88c34b82.success.notificationMsg.subscription
		// String notestripped = StringUtils.replace(page.getSuccessText(), "\n", " ");
		// boolean checked = checker.checkConfirmCancelText(notestripped);	
		// ErrorCollector.verifyTrue(checked,"Confirm Message noe correct");
		
		String chtextstripped = parser.stripHTML(parser.getCancelSuccessText());
		String pgstripped = StringUtils.replace(page.getCancelDetail(), "\n", " ");
		ErrorCollector.verifyEquals(pgstripped, chtextstripped,"The Cancel succes detail is incorrect");
		
		return true;
		
		
	}

	@Override
	public void initialiseChecks() {
		parser = JsonParser.getInstance();
		checker = opcoTextChecker.getInstance();
	}

}
