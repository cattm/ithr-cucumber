package com.ithr.ppe.bdd.reporter.reporter;

import java.io.File;

import net.masterthought.cucumber.Configuration;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ithr.ppe.bdd.commons.CommandExecutor;

public class PPEConfiguration extends Configuration {

	public static Logger log = Logger.getLogger(PPEConfiguration.class);

	private boolean displayFeature = false;	
	private String reportTitle = "";
	private String ppebuild = "";
	private String begin = "";
	private String end = "";

	public PPEConfiguration(File reportOutputDirectory, String projectName) {
		super(reportOutputDirectory, projectName);
		setPPEBuild();
	}
	
	public void setTitle(String title) {
		this.reportTitle = title;
	}
	
	public String getTitle() {
		return this.reportTitle;
	}
		

	public void setDisplayFeature(boolean display) {
		this.displayFeature = display;
	}
	
	public boolean getDisplayFeature() {
		return this.displayFeature;
	}
	
	public void setBeginTime(String begintime) {
		this.begin = begintime;
	}
	
	public String getBeginTime() {
		return this.begin;
	}
	public void setEndTime(String endtime) {
		this.end = endtime;
	}
	
	public String getEndTime() {
		return this.end;
	}
	private void setPPEBuild () {
		ppebuild = findSWVersion();
	}
	
	public String getPPEBuild () {
		return this.ppebuild;
	}
	
	private static String findSWVersion () {
			// Temp Test solution
	    	String sw = CommandExecutor.execCurlSoftwareVersion("https://dit.offers.vodafone.com/");
	    	sw = StringUtils.replace(sw, "\n", " ");
	    	sw = StringUtils.replace(sw, "\t", " ");
	    	sw = StringUtils.replace(sw, "Manifest", "");
	    	String softwareVersion = StringUtils.replace(sw, "date/time", "");
	    	return softwareVersion;
	}
}
