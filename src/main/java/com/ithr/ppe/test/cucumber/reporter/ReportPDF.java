package com.ithr.ppe.test.cucumber.reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXB;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;

public class ReportPDF {
	
	private String outputDirectory;
    private String outputName;
    private String pdfPageSize = "auto";
    
	public String getPdfPageSize() {
	        return pdfPageSize;
	    }

	
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

	    
}
