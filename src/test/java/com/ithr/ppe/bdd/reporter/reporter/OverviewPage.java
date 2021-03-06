package com.ithr.ppe.bdd.reporter.reporter;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportResult;
import net.masterthought.cucumber.generators.AbstractPage;

public class OverviewPage  extends AbstractPage {
		
		public static final String WEB_PAGE = "anoverview.html";

		private PPEConfiguration myconfig;
		
		public OverviewPage(ReportResult reportResult, PPEConfiguration configuration) {
				super(reportResult, "overview.vm", (Configuration) configuration);
				this.myconfig = configuration;
			}
	    @Override
	    public String getWebPage() {
	        return WEB_PAGE;
	    }

	    @Override
	    public void prepareReport() {
	    	
	    	context.put("all_features", report.getAllFeatures());
	    	context.put("all_features_passed",report.getAllPassedFeatures());
	    	context.put("all_features_failed",report.getAllFailedFeatures());
	        context.put("report_summary", report.getFeatureReport());
	        context.put("parallel", myconfig.isParallelTesting());
	        context.put("ppebuild", myconfig.getPPEBuild());
	        context.put("finishedat", myconfig.getEndTime());
	        context.put("displaytable", myconfig.getDisplayFeature());
	    }

}
