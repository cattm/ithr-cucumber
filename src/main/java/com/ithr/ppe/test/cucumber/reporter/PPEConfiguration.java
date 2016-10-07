package com.ithr.ppe.test.cucumber.reporter;

import java.io.File;

import net.masterthought.cucumber.Configuration;

public class PPEConfiguration extends Configuration {

		private String mySW = "name:   ppe_r16-10-1_rc8  type:   frozen Build : 20161004-1251";
		public PPEConfiguration(File reportOutputDirectory, String projectName) {
	        super(reportOutputDirectory, projectName);
	    }
		
		public String getPPEBuild() {
			return mySW;
		}
		
}
