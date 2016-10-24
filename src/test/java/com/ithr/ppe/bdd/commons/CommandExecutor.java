package com.ithr.ppe.bdd.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class CommandExecutor {
	public static Logger log = Logger.getLogger(CommandExecutor.class);
    private static String sr = "";
    private static String se = "";
    private static boolean errored = false;
    // TODO: not sure this model is 100% SAFE - take a fresh look at Statics
 
	
	public static String execCmd(String command, boolean anderror)
    {
	     String cmdOutput = "";
         try {
                Process p = Runtime.getRuntime().exec(command);

                BufferedReader stdInput = new BufferedReader(new
                     InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                     InputStreamReader(p.getErrorStream()));

                log.debug("Here is the standard output of the command:\n");
                while ((sr = stdInput.readLine()) != null) {
                    log.debug(sr);
                    cmdOutput += "\n" + sr;
                }

                // read any errors from the attempted command
                if (anderror) {
                	log.error("Here is the standard error of the command (if any):\n");
                	while ((se = stdError.readLine()) != null) {
                		log.error(se);
                	}
                }

                return cmdOutput;
            }
            catch (IOException e) {
                log.error("exception happened reading file: ");
                e.printStackTrace();
                errored = true;
                return "";
            }
    }
	
	public static int testCmnd() {
		String mycommand = "ls -l";
		String outcome = execCmd(mycommand, false);
		log.info("command returned : " + outcome);
		return 0;
	}
	public static int execCurlSpotifyTerminate (String param) {
		String mycommand = "curl -i -dusername=" + param + " https://vodafone-it:ee2Q1kqsdVXpfpH27q5@ws-sales-testing.spotify.com/3/product/terminate";
		String outcome = execCmd(mycommand, true);
		log.debug("command returned : " + outcome);
		return 0;
	}
	
	public static String execCurlSoftwareVersion (String envurl) {
		String mycommand = "curl " + envurl + "version";
		String outcome = execCmd(mycommand, false);
		log.debug("command returned : " + outcome);
		return outcome;
	}
	
	public static String execFindExactJsonFile(String path, String compare) {
		String jsonfile = "NOT FOUND";
		String mycommand = "ls -rt " + path;
		String outcome = execCmd(mycommand,false);
        String[] arr = outcome.split("\n");
        
        // TODO: there might be more than one so we need to find the highest version
        // TODO: to be safe we also need to check rather than assume its a json
        // also we might not find it
        for (String s : arr ) {
        	if (s.contains(compare)) {
        		log.info("Found a valid file : " + s);
        	    jsonfile = s;
        	}
        }
        return jsonfile; 
	}
	
	public static String findValidResults(String file) {
		String size = "NOT FOUND";
		String mycommand = "./src/test/bin/getrep.sh " + file;		
		String outcome = execCmd(mycommand,false);

    
        String arr = outcome.replace("\n", "");
    
        	log.info(arr);
        	if (arr.equals("0")) {
        		log.info("file may be there but empty : " + arr);
        		size = arr;
        	} else if (arr.equals("")){
        		;
        	} else {
        		size = arr;
        	}
        return size; 
	}
	public boolean cmdErrored () {
		return errored;
	}
	
	public String getErrorOutput () {
		String x = se;
		return x;
	}
	
	
}
