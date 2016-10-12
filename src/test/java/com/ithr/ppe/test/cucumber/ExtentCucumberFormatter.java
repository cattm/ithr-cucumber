package com.ithr.ppe.test.cucumber;
/**
 * Implements the Cucumber formatter for Extent Reports with Cucumber
 * Builds on a gitHub project that is free to use and modify as detailed in the license below
 * 
 *  * @author Marcus Catt (marcus.catt@ithrconsulting.com)
 * 
 * Original code is:
 * Copyright (c) 2016 Vimal Raj
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used 
 * to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class ExtentCucumberFormatter {
	

}
/*
public class ExtentCucumberFormatter implements Reporter, Formatter  {
	    private static ExtentReports extent;
	    private ExtentTest featureTest;
	    private ExtentTest scenarioTest;
	    private LinkedList<Step> testSteps = new LinkedList<Step>();
	    private static File htmlReportDir;
	    private static Map systemInfo;
	    private boolean scenarioOutlineTest;

	    private static final Map<String, String> MIME_TYPES_EXTENSIONS = new HashMap() {
	        {
	            this.put("image/bmp", "bmp");
	            this.put("image/gif", "gif");
	            this.put("image/jpeg", "jpg");
	            this.put("image/png", "png");
	            this.put("image/svg+xml", "svg");
	            this.put("video/ogg", "ogg");
	        }
	    };

	    public ExtentCucumberFormatter(File filePath) {
	        if (!filePath.getPath().equals("")) {
	            String reportPath = filePath.getPath();
	            this.htmlReportDir = new File(reportPath);
	            this.extent = new ExtentReports(reportPath);
	        } else {
	            String reportDir = "output/Run_" + System.currentTimeMillis();
	            this.htmlReportDir = new File(reportDir);
	            this.extent = new ExtentReports(reportDir + "/report.html");
	        }
	    }

	    public ExtentCucumberFormatter() {
	    }

	    
	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting,
	                                                       DisplayOrder displayOrder, NetworkMode networkMode,
	                                                       Locale locale) {
	        htmlReportDir = filePath;
	        extent = new ExtentReports(filePath.getAbsolutePath(), replaceExisting, displayOrder, networkMode, locale);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting,
	                                                       DisplayOrder displayOrder, NetworkMode networkMode) {
	        initiateExtentCucumberFormatter(filePath, replaceExisting, displayOrder, networkMode, null);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting,
	                                                       DisplayOrder displayOrder, Locale locale) {
	        initiateExtentCucumberFormatter(filePath, replaceExisting, displayOrder, null, locale);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting,
	                                                       DisplayOrder displayOrder) {
	        initiateExtentCucumberFormatter(filePath, replaceExisting, displayOrder, null, null);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting, NetworkMode networkMode,
	                                                       Locale locale) {
	        initiateExtentCucumberFormatter(filePath, replaceExisting, null, networkMode, locale);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting,
	                                                       NetworkMode networkMode) {
	        initiateExtentCucumberFormatter(filePath, replaceExisting, null, networkMode, null);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, NetworkMode networkMode) {
	        initiateExtentCucumberFormatter(filePath, null, null, networkMode, null);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting, Locale locale) {
	        initiateExtentCucumberFormatter(filePath, replaceExisting, null, null, locale);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Boolean replaceExisting) {
	        initiateExtentCucumberFormatter(filePath, replaceExisting, null, null, null);
	    }

	    public static void initiateExtentCucumberFormatter(File filePath, Locale locale) {
	        initiateExtentCucumberFormatter(filePath, null, null, null, locale);
	    }

	    public static void initiateExtentCucumberFormatter(File reportoffset) {
	        initiateExtentCucumberFormatter(reportoffset, null, null, null, null);
	    }

	    public static void initiateExtentCucumberFormatter() {
	    	DateStamp mydate = new DateStamp();	
			String reportFilePath = "reports/site/extent/" + mydate.getFileDayFormat() + "/" + mydate.getFileTimeFormat() + "_report.html";
	        initiateExtentCucumberFormatter(new File(reportFilePath));
	    }

	    public static void loadConfig(File configFile) {
	        extent.loadConfig(configFile);
	    }

	    public static void addSystemInfo(String param, String value) {
	        if (systemInfo == null) {
	            systemInfo = new HashMap();
	        }
	        systemInfo.put(param, value);
	    }

	    public static void addSystemInfo(Map<String, String> info) {
	        if (systemInfo == null) {
	            systemInfo = new HashMap();
	        }
	        systemInfo.putAll(info);
	    }

	    public void before(Match match, Result result) {

	    }

	    public void result(Result result) {
	        if (!scenarioOutlineTest) {
	            if ("passed".equals(result.getStatus())) {
	                scenarioTest.log(LogStatus.PASS, testSteps.poll().getName(), "PASSED");
	            } else if ("failed".equals(result.getStatus())) {
	                scenarioTest.log(LogStatus.FAIL, testSteps.poll().getName(), result.getError());
	            } else if ("skipped".equals(result.getStatus())) {
	                scenarioTest.log(LogStatus.SKIP, testSteps.poll().getName(), "SKIPPED");
	            } else if ("undefined".equals(result.getStatus())) {
	                scenarioTest.log(LogStatus.UNKNOWN, testSteps.poll().getName(), "UNDEFINED");
	            }
	        }
	    }

	    public void after(Match match, Result result) {

	    }

	    public void match(Match match) {

	    }

	    public void embedding(String s, byte[] bytes) {
	        if (!scenarioOutlineTest) {
	            String extension = (String)MIME_TYPES_EXTENSIONS.get(s);
	            String fileName = "screenshot-" + System.currentTimeMillis() + "." + extension;
	            this.writeBytesAndClose(bytes, this.reportFileOutputStream(fileName));
	            scenarioTest.log(LogStatus.INFO, scenarioTest.addScreenCapture(fileName));
	        }
	    }

	    public void write(String s) {
	        if (!scenarioOutlineTest)
	            scenarioTest.log(LogStatus.INFO, s);
	    }

	    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {
	    }

	    public void uri(String s) {
	    }

	    public void feature(Feature feature) {
	        featureTest = extent.startTest("Feature: " + feature.getName());
	    }

	    public void scenarioOutline(ScenarioOutline scenarioOutline) {
	        scenarioOutlineTest = true;
	    }

	    public void examples(Examples examples) {
	    }

	    public void startOfScenarioLifeCycle(Scenario scenario) {
	        scenarioTest = extent.startTest("Scenario: " + scenario.getName());

	        for (Tag tag : scenario.getTags()) {
	            scenarioTest.assignCategory(tag.getName());
	        }
	        scenarioOutlineTest = false;
	    }

	    public void background(Background background) {
	    }

	    public void scenario(Scenario scenario) {
	    }

	    public void step(Step step) {
	        if (!scenarioOutlineTest)
	            testSteps.add(step);
	    }

	    public void endOfScenarioLifeCycle(Scenario scenario) {
	        if (!scenarioOutlineTest) {
	            extent.endTest(scenarioTest);
	            featureTest.appendChild(scenarioTest);
	        }
	    }

	    public void done() {
	    }

	    public void close() {
	        extent.addSystemInfo(systemInfo);
	        extent.close();
	    }

	    public void eof() {
	        extent.endTest(featureTest);
	        extent.flush();
	    }

	    private OutputStream reportFileOutputStream(String fileName) {
	        try {
	            return new URLOutputStream(new URL(this.htmlReportDir.toURI().toURL(), fileName));
	        } catch (IOException var3) {
	            throw new CucumberException(var3);
	        }
	    }

	    private void writeBytesAndClose(byte[] buf, OutputStream out) {
	        try {
	            out.write(buf);
	        } catch (IOException var4) {
	            throw new CucumberException("Unable to write to report file item: ", var4);
	        }
	    }

}*/
