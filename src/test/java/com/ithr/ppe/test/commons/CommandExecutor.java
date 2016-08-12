package com.ithr.ppe.test.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class CommandExecutor {
	public static Logger log = Logger.getLogger(CommandExecutor.class);
    private static String sr = "";
    private static String se = "";
    // TODO: not sure this model is 100% SAFE - take a fresh look at Statics
    private static String result;
	
	private static int execCmd(String command, boolean anderror)
    {
	     result = "";
         try {
                Process p = Runtime.getRuntime().exec(command);

                BufferedReader stdInput = new BufferedReader(new
                     InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                     InputStreamReader(p.getErrorStream()));

                log.info("Here is the standard output of the command:\n");
                while ((sr = stdInput.readLine()) != null) {
                    log.info(sr);
                    result += "\n" + sr;
                }

                // read any errors from the attempted command
                if (anderror) {
                	log.info("Here is the standard error of the command (if any):\n");
                	while ((se = stdError.readLine()) != null) {
                		log.info(se);
                	}
                }

                return(0);
            }
            catch (IOException e) {
                log.error("exception happened : ");
                e.printStackTrace();
                return(-1);
            }
    }
	
	public static int testCmnd() {
		String mycommand = "ls -l";
		int status = execCmd(mycommand, false);
		log.info("command Status returned is : " + status);
		return status;
	}
	public static int execCurlSpotifyTerminate (String param) {
		String mycommand = "curl -i -dusername=" + param + " https://vodafone-it:ee2Q1kqsdVXpfpH27q5@ws-sales-testing.spotify.com/3/product/terminate";
		int status = execCmd(mycommand, true);
		log.info("command Status returned is : " + status);
		return status;
	}
	
	public static int execCurlSoftwareVersion (String envurl) {
		String mycommand = "curl " + envurl + "version";
		int status = execCmd(mycommand, false);
		log.info("command Status returned is : " + status);
		return status;
	}
	
	public static String execFindExactJsonFile(String path, String compare) {
		String jsonfile = "";
		String mycommand = "ls -rt " + path;
		int status = execCmd(mycommand,false);
        String[] arr = result.split("\n");
        
        // TODO: there might be more than one so we need to find the highest version
        // TODO: to be safe we also need to check rather than assume its a json
        for (String s : arr ) {
        	if (s.contains(compare)) {
        		log.info("Found a valid file : " + s);
        	    jsonfile = s;
        	}
        }
        return jsonfile; 
	}
	public static String getResponseOutput () {
		String x = result;
		return x;
	}
	
	public String getErrorOutput () {
		String x = se;
		return x;
	}
	
	
}
