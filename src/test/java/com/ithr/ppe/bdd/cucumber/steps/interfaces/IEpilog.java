package com.ithr.ppe.bdd.cucumber.steps.interfaces;
/**
 * Defines the interface for implementations of the required post activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
import org.openqa.selenium.WebDriver;

import com.ithr.ppe.bdd.base.Customer;
import com.ithr.ppe.bdd.commons.Partners;

/**
 * Defines the interface for implementations of the required post activities 
 *
 * @author Marcus Catt (marcus.catt@ithrconsulting.com
 */
public interface IEpilog {
	public void initialiseChecks();
	public boolean refresh(WebDriver driver, String baseopcourl, Customer customer);
}
