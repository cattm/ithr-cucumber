package com.ithr.ppe.test.commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateStamp {
	private Locale theLocaleToUse = Locale.UK;
	
	public void setLocaleToUse(Locale locale) {
		this.theLocaleToUse = locale;
	}
	
	public String getSimpleDateFormat() {
		return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", theLocaleToUse).format(new Date());
	}
	
	public String getFileDateFormat() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss", theLocaleToUse).format(new Date());
	}
	
	public String getRanDateFormat() {
		return new SimpleDateFormat("yyyyMMddHHmmss", theLocaleToUse).format(new Date());
	}
}
