package com.ithr.ppe.test.cucumber.steps.interfaces;
/**
 * Defines the interface for implementations of the required post activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.test.base.Customer;
import com.ithr.ppe.test.commons.Partners;

/**
 * Defines the interface for implementations of the required post activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
public interface IEpilog {
	public void initialiseChecks();
	public boolean refresh(WebDriver driver, String baseopcourl, Customer customer);
}
