package com.ithr.ppe.test.cucumber.steps.interfaces;
/**
 * Defines the interface for implementations of the required post activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.commons.Partners;
import com.ithr.ppe.test.cucumber.pages.UserEntertainment;

/**
 * Defines the interface for implementations of the required post activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
public interface IEpilog {
	public boolean refreshPPE(WebDriver driver, String baseopcourl, Partners partner);

}
