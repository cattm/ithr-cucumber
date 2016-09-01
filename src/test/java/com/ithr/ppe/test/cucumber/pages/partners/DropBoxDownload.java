package com.ithr.ppe.test.cucumber.pages.partners;
/**
 * Implements the basic model of DropBox download Page
 * It extends the page model base 
 * It is not really a partner page as its internal to VF
 * 
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ithr.ppe.test.cucumber.pages.PageBase;

public class DropBoxDownload extends PageBase {
	public static Logger log = Logger.getLogger(DropBoxDownload.class);
	
	
	@FindBy(css=".open>p:nth-of-type(1)")
	WebElement contentText;
	
	// .open>p:nth-of-type(1) will get first para.....
	
	
	public DropBoxDownload(WebDriver driver) {
		  super(driver);
		  PageFactory.initElements(driver, this);	  
	}
	
	public String getDownlaodText() {
		return contentText.getText();
	}
	

}
