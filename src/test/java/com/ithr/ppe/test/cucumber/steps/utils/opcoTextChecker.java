package com.ithr.ppe.test.cucumber.steps.utils;

public class opcoTextChecker {
    // TODO move these so the are read in from a file once we know how many we need to do 
	final String gbSubscriptionText = "You have no subscriptions. Please take a look at the available offers.";
    final String deSubscriptionText = "Du hast noch kein aktives Angebot gebucht";
    final String ieSubscriptionText = "You have no subscriptions. Please take a look at the available offers.";
    
    final String gbConfirmText = "Your entertainment selection has been confirmed";
    final String deConfirmText = "Danke für Deine Buchung!";
    //final String deConfirmText = "Genieße jetzt Vodafone MobileTV mit dem Sky Fußball-Bundesliga-Paket";
    final String ieConfirmText = "Your entertainment selection has been confirmed";
   
    
	public boolean checkSubscriptionText(String opco, String onpage) {
		
		switch (opco) {
			case "gb" :	return onpage.equals(gbSubscriptionText);
			case "de" : return onpage.equals(deSubscriptionText);
			case "ie" :	return onpage.equals(ieSubscriptionText);
			default: return false;
		}		
	}
	
	public boolean checkConfirmText(String opco, String onpage) {
		
		switch (opco) {
			case "gb" :	return onpage.equals(gbConfirmText);
			case "de" : return onpage.equals(deConfirmText);
			case "ie" :	return onpage.equals(ieConfirmText);
			default: return false;
		}		
	}
}
