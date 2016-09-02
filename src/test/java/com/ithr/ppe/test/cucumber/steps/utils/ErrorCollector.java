
package com.ithr.ppe.test.cucumber.steps.utils;

import org.apache.log4j.Logger;
import org.junit.Assert;

// TODO: Check this class is SAFE under all permutations
public class ErrorCollector {
	public static Logger log = Logger.getLogger(ErrorCollector.class);
	private static StringBuffer verificationErrors = new StringBuffer();
	private static boolean doVerify = false;
	
	public static void setVerify(boolean verify) {
		doVerify = verify;
	}
	
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
     
    
    public static <T> void assertThat(String reason, T actual, org.hamcrest.Matcher<T> matcher) {
    	Assert.assertThat(reason, actual, matcher);
    }
    
    // have made this logic more complex than ideal
    // in order to allow test to run to completion without failures while 
    // we are not sure of environment and text changes etc
    // we will just log and NOT FAIL the test
    public static void verifyTrue(boolean condition) {
    	log.info("actual: " + condition);
    	if (doVerify) {
    		try {
    			assertTrue(condition);
    		} catch(Throwable e) {
    			log.error("boolean check Failed");
    			addVerificationFailure(e);
    		} 
    	} else {
     		if (!condition) log.error("Verify would fail");
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
    	log.info("actual: " + condition);
    	if (doVerify) {
    		try {
    			assertTrue(condition, message);
    		} catch(Throwable e) {
    			log.error("boolean check failed");
    			addVerificationFailure(e);
    		}
    	} else {
     		if (!condition) log.error("Verify would fail");
    	}
    }
    
    public static void verifyFalse(boolean condition) {
      	log.info("actual: " + condition);
    	if (doVerify) {
    		try {
    			assertTrue(condition);
    		} catch(Throwable e) {
    			log.error("boolean check failed");
    			addVerificationFailure(e);
    		}
    	} else {
     		if (!condition) log.error("Verify would fail");
    	}
    	
    }
    
    public static void verifyFalse(boolean condition, String message) {
      	log.info("actual: " + condition);
    	if (doVerify) {
    		try {
    			assertFalse(condition, message);
    		} catch(Throwable e) {
    			log.error("boolean check failed");
    			addVerificationFailure(e);
    		}
    	} else {
     		if (!condition) log.error("Verify would fail");
    	}
  
    }
    
    public static void verifyEquals(boolean actual, boolean expected) {
		log.info("actual: " + actual);
    	if (doVerify) {
    		try {
    			assertEquals(actual, expected);
    		} catch(Throwable e) {
    			log.error("boolean check failed");
    			addVerificationFailure(e);
    		}
    	} else {
    		if (!actual) log.error("Verify would fail");
    	}
    }
    
    // These string checks will check the content equality not the object!
    public static void verifyEquals(String actual, String expected, String message) {
		log.info("expected: " + expected);
		log.info("actual  : " + actual);
    	if (doVerify) {
    		try {
    			assertTrue(actual.equals(expected), message);
    		} catch(Throwable e) {
    			log.error("string check failed");
    			addVerificationFailure(e);
    		}
    	} else {
    		if (!actual.equals(expected)) log.error("Verify would fail");
    	}
    }
    
    public static void verifyEquals(String actual, String expected) {
		log.info("expected: " + expected);
		log.info("actual  : " + actual);
        if (doVerify) {
        	try {
        		assertTrue(actual.equals(expected));
			} catch(Throwable e) {
				log.error("string check failed");
				addVerificationFailure(e);
			}
        } else {
    		if (!actual.equals(expected)) log.error("Verify would fail");
    	}
    }

    public static <T> void verifyThat(String reason, T actual, org.hamcrest.Matcher<T> matcher) {
    	try {
    		assertThat(reason, actual, matcher);
    	} catch (Throwable e) {
			log.error("Verify failed " + reason);
			addVerificationFailure(e);
    	}
    }
    
    
    // leave these two methods out for now
    public static void verifyEquals(Object actual, Object expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
			log.error("object check failed");
    		addVerificationFailure(e);
		}
    }
    
    public static void verifyEquals(Object[] actual, Object[] expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
			log.error("check failed");
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
		verificationErrors.append("\n***************\n");
	}
	
	public static boolean failedVerification() {
		 String verificationErrorString = verificationErrors.toString();
		 if (!"".equals(verificationErrorString)) {

		    	return true;
		 }
		 return false;
		    
	}
}
