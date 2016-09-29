package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerCancel;
import com.ithr.ppe.test.cucumber.pages.BasicPartnerOffer;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;
import com.ithr.ppe.test.cucumber.steps.SecondaryCustomerSteps;
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

	
	public boolean cancelTheOffer(WebDriver driver, Customer customer) {

		BasicPartnerCancel cancel = new BasicPartnerCancel(driver);
		cancel.bodyLoaded();
		// check cancel text
		verifyCancelText(cancel, customer);
		
		log.info("going to cancel");
		// TODO: grab this text from somewhere in a check file of some description - this is stolen from Sky
		cancel.clickCancel("STOP AT THE END OF INCLUSIVE PERIOD");		
		// and then
		cancel.clickCancel("CONFIRM STOP");
				
		return verifyConfirm(cancel);

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
		UserEntertainment entpage = new UserEntertainment(driver);
		if (!entpage.bodyLoaded()) {
			 log.error("Entertainement Page body did not load in time");
		}
		
		// open the correct offer and check text
		
		return true;
		
		/*
		 * refresh should see additional offer go away
		and this offer remain under manage subs
		This is like ep.refresh but ever so slightly different
	 
	    <div id="gb-sky-hardbundle" class="offer-details-jsx can-be-closed purchased__module success__page">
	    :
	    :
		<div class="details-content">
		<p>You have been successfully unsubscribed from Sky Sports Mobile pack 1 from the end of your inclusive subscription</p>
		</div>
		
		 */
	}

	@Override
	public void initialiseChecks() {
		parser = JsonParser.getInstance();
		checker = opcoTextChecker.getInstance();
	}

}
