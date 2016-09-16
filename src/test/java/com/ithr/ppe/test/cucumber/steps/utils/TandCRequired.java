package com.ithr.ppe.test.cucumber.steps.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TandCRequired {
	public static Logger log = Logger.getLogger(TandCRequired.class);
	private static Properties prop = new Properties();
	private static Map<String, String []> tncMap = new HashMap<String,String []>();
	
	private static TandCRequired instance = null;
	
	protected TandCRequired () {
	}
	
	public static TandCRequired getInstance() {
		if(instance == null) {
	         instance = new TandCRequired();
	      }
	      return instance;
	}

	
	private String [] SplitOffers(String offer) {
		String [] tmp = null;
		if (offer.contains(", ")) {
			tmp = offer.split(", ");
			for (int i = 0; i < tmp.length; i++) {
			}
		} else {
			tmp = new String [1];
			tmp[0] = offer;
		}
		return tmp;
	}
	
	private void addMap(String opco, String prop) {
		
		String [] tncs = SplitOffers(prop);
		tncMap.put(opco, tncs);	 
	}
	
	private void addAllMaps() {
		String prop1 = GetProperty("DE.notandc");
		addMap("DE", prop1);
		prop1 = GetProperty("ES.notandc");
		addMap("ES", prop1);
		prop1 = GetProperty("GB.notandc");
		addMap("GB", prop1);
		prop1 = GetProperty("GR.notandc");
		addMap("GR", prop1);
		prop1 = GetProperty("IE.notandc");
		addMap("IE", prop1);
		prop1 = GetProperty("IS.notandc");
		addMap("IS", prop1);
		prop1 = GetProperty("IT.notandc");
		addMap("IT", prop1);
		prop1 = GetProperty("NL.notandc");
		addMap("NL", prop1);
		prop1 = GetProperty("NZ.notandc");
		addMap("NZ", prop1);
		prop1 = GetProperty("PT.notandc");
		addMap("PT", prop1);
		prop1 = GetProperty("RO.notandc");
		addMap("RO", prop1);
	}
	
	public boolean hasTnc(String opco, String partner) {
		
		String [] vals = tncMap.get(opco.toUpperCase());
		boolean tnc = true;
		for (int i = 0; i < vals.length; i++) {
			log.info("Read back " + vals[i]);
			if (partner.contentEquals(vals[i])) {
				log.info("Setting TNC required to false");
				tnc = false;
			}
		}
		return tnc;
		
	}
	public void loadPropertyFile (String file) throws IOException {
			
		FileInputStream fis = new FileInputStream(file);
		prop.load(fis);
		addAllMaps();
	
	}
	
	private  String GetProperty(String search) {
		return prop.getProperty(search);
	}	
	
}
