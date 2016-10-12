package com.ithr.ppe.test.cucumber.reporter;


public class ReportPDF {
	
	private String outputDirectory;
    private String outputName;
    private String pdfPageSize = "auto";
    
	public String getPdfPageSize() {
	        return pdfPageSize;
	    }
/*
	
	private void convertSvgToPng(File svg, File png) throws Exception {
	        String svgUriInput = svg.toURI().toURL().toString();
	        TranscoderInput inputSvgImage = new TranscoderInput(svgUriInput);
	        //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
	        OutputStream pngOStream = new FileOutputStream(png);
	        TranscoderOutput outputPngImage = new TranscoderOutput(pngOStream);
	        // Step-3: Create PNGTranscoder and define hints if required
	        PNGTranscoder myConverter = new PNGTranscoder();
	        // Step-4: Convert and Write output
	        myConverter.transcode(inputSvgImage, outputPngImage);
	        // Step 5- close / flush Output Stream
	        pngOStream.flush();
	        pngOStream.close();
	    }
	public String replaceSvgWithPng(File htmlFile) throws Exception {
	        String tempPath = this.outputDirectory + File.separator + "temp" + (new Date()).getTime();
	        File folder = new File(tempPath);
	        //Files.createTempDirectory("temp").toFile();
	        folder.mkdirs();
	        folder.deleteOnExit();
	        String htmlText = FileUtils.readFileToString(htmlFile);
	        Pattern p = Pattern.compile("<svg(.*?)</svg>", Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	        Matcher m = p.matcher(htmlText);
	        int index = 0;
	        while (m.find()) {
	            String svg = m.group(0);
	            File svgOutput = new File(tempPath + File.separator + index + ".svg");
	            svgOutput.deleteOnExit();
	            FileUtils.writeStringToFile(svgOutput, svg);
	            File png = new File(tempPath + File.separator + index + ".png");
	            png.deleteOnExit();
	            convertSvgToPng(svgOutput, png);
	            htmlText = m.replaceFirst(
	                Matcher.quoteReplacement(String.format(Locale.US,
	                        "<img src=\"%s\"></img>",
	                    folder.getName() + "/" + index + ".png")));
	            m = p.matcher(htmlText);
	            index++;
	        }
	        return htmlText;
	    }
	    
	public void exportToPDF(File htmlFile, String suffix) throws Exception {
	        File bakupFile = new File(htmlFile.getAbsolutePath() + ".bak.html");
	        String url = bakupFile.toURI().toURL().toString();
	        String outputFile = this.outputDirectory + File.separator + this.outputName
	                + "-" + suffix + ".pdf";
	        String updatedContent = replaceSvgWithPng(htmlFile);
	        updatedContent = updatedContent.replaceAll("\"hoverTable\"", "\"_hoverTable\"");
	        updatedContent = updatedContent.replaceAll("__PAGESIZE__", this.getPdfPageSize());
	        FileUtils.writeStringToFile(bakupFile, updatedContent);
	        OutputStream os = new FileOutputStream(outputFile);

	        ITextRenderer renderer = new ITextRenderer();
	        renderer.setDocument(url);
	        renderer.layout();
	        renderer.createPDF(os);

	        os.close();
	}
*/
	    
}
