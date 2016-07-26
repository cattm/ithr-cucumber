package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.ithr.ppe.test.cucumber.steps.PurchaseSpotifyOffersSteps;

public class ErrorCollector {
	public static Logger log = Logger.getLogger(ErrorCollector.class);
	private static StringBuffer verificationErrors = new StringBuffer();
	
	public static void assertTrue(boolean condition) {
    	Assert.assertTrue(condition);
    }
    
    public static void assertTrue(boolean condition, String message) {
    	Assert.assertTrue(message, condition);
    }
    
    public static void assertFalse(boolean condition) {
    	Assert.assertFalse(condition);
    }
    
    public static void assertFalse(boolean condition, String message) {
    	Assert.assertFalse(message, condition);
    }
    
    public static void assertEquals(boolean actual, boolean expected) {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object actual, Object expected) {
    	Assert.assertEquals(actual, expected);
    }
    
    @SuppressWarnings("deprecation")
	public static void assertEquals(Object[] actual, Object[] expected) {
    	Assert.assertEquals(actual, expected);
    }
     
    public static void verifyTrue(boolean condition) {
    	try {
    		assertTrue(condition);
    	} catch(Throwable e) {
    		addVerificationFailure(e);
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
    	try {
    		assertTrue(condition, message);
    	} catch(Throwable e) {
    		addVerificationFailure(e);
    	}
    }
    
    public static void verifyFalse(boolean condition) {
    	try {
    		assertFalse(condition);
		} catch(Throwable e) {
    		addVerificationFailure(e);
		}
    }
    
    public static void verifyFalse(boolean condition, String message) {
    	try {
    		assertFalse(condition, message);
    	} catch(Throwable e) {
    		addVerificationFailure(e);
    	}
    }
    
    public static void verifyEquals(boolean actual, boolean expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
		}
    }
    
    public static void verifyEquals(String actual, String expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
		}
    }

    public static void verifyEquals(Object actual, Object expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
		}
    }
    
    public static void verifyEquals(Object[] actual, Object[] expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
		}
    }

    public static void fail(String message) {
    	Assert.fail(message);
    }
    
	public static String getVerificationFailures() {
		String verificationErrorString = verificationErrors.toString();

		return verificationErrorString;
	}
	
	public static void addVerificationFailure(Throwable e) {

		verificationErrors.append(e.toString());
	}
	
	public static boolean failedVerification() {
		 String verificationErrorString = verificationErrors.toString();
		 if (!"".equals(verificationErrorString)) {

		    	return true;
		 }
		 return false;
		    
	}
}
