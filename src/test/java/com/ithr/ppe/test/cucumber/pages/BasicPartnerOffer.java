package com.ithr.ppe.test.cucumber.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.commons.CommonConstants;


/**
 * Implements the basic model of a partner offer page
 * It extends the page model base 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
public class BasicPartnerOffer extends PageBase {
	public static Logger log = Logger.getLogger(BasicPartnerOffer.class);

	@FindBy(name="accept")
	private WebElement tnc;
	  		
	// Note: - this find will return a single item if the display is set to the correct size
	// if it is a full size browser it will return a list of items to iterate through
	//@FindBy(css="div.btn-jsx.cf.main-offer-btn")
	@FindBy(css="div [class='table-panel-top main-offer-content'] a[class='btn event-btn cf btn--okButton']")
	private WebElement acceptOffer;
	
	@FindBy(css="h4.offer-subtitle.bold-font")
	private WebElement theOffer;
	
	@FindBy(css=".main-offer-content div.details-content")
	private WebElement theOfferDetail;
	
	@FindBy(css=".label-with-link-jsx")
	WebElement theOfferTnC;
	
	/////////////////////////////
	
	@FindBy(css=".notification-jsx.pulse>p")
	private WebElement confirmationText;
	
	// TODO: When Matt has adjusted the page constructs sort the finders - this works - but is not good
	@FindBy(xpath="/html/body/div/div/article/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[3]")
	private WebElement whatHappensNext;
	
	// TODO: this is a poor find - PURPLE really!! you should be ashamed
	@FindBy(css="div.btn-jsx.cf.large-screens.purple a.btn.event-btn.cf.btn-tertiary")
	private WebElement additionalOffer;
	
	// TODO - Finder implementation
	private List <WebElement> managedSubs;
	
		
	public  BasicPartnerOffer (WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	// get the overall offer text
	public String getUserOffer() {
		   return theOffer.getText();
	}
	   
	// get the specifics of the offer
	public String getOfferDetail() {
		   return theOfferDetail.getText();
	}
	   
	// retunr the text associated with terms and conditions for checking
	public String getOfferTnC() {
		   return theOfferTnC.getText();
	}
	
	// confirmation of a successful offer
	public String getSuccessText() {
		   return confirmationText.getText();
	}

	// accept the terms and conditions	  
	public void setTnC() {
			tnc.click();
	}
	   
	// when the what happens next text is displayed this method will return the text for checking
	public String getHappensNextText() {
		   return whatHappensNext.getText();
	}
		
	public void clickAdditionalOffer () {
		   additionalOffer.click();
	}
	   
	// TODO: temporary method - until I sort out correct model to use to identify this button
	// And perform a check on the text being correct at the same time
	// this method returns two buttons - they both accept the offer!
	

	// accept the offer 
	public void clickAcceptOffer () {
	// we occasionally appear to get a race hazad on selecting this button
		for (int i = 0; i < 5 && !acceptOffer.isDisplayed(); i++) {
			try {
				Thread.sleep(CommonConstants.FAST);
			} catch (InterruptedException e) {
				log.info("Timer interrupted");
			}
			log.info("waiting for button to appear: " + i);
		}
		acceptOffer.click();
	}
	   
	// check the accept offer text
	public String getAcceptOfferText () {
		return acceptOffer.getText();
	}
	   	   
	
	public String getManagedSubscription(String check) {
		String foundtext = "";
		Iterator <WebElement> iterator = managedSubs.iterator();
		while (iterator.hasNext()) {
			WebElement element = iterator.next();
			String thetext = element.getText();
			log.debug("checking element :" + thetext);			
			if ( thetext.equalsIgnoreCase(check)) {
				return foundtext = thetext;				  
			}
		}
		return foundtext;
	}
}
