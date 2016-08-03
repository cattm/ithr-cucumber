package com.ithr.ppe.test.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class CommandExecutor {
	public static Logger log = Logger.getLogger(CommandExecutor.class);
    private static String sr = "";
    private static String se = "";
	
	private static int execCmd(String command, boolean anderror)
    {
	   
         try {
                Process p = Runtime.getRuntime().exec(command);

                BufferedReader stdInput = new BufferedReader(new
                     InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                     InputStreamReader(p.getErrorStream()));

                log.info("Here is the standard output of the command:\n");
                while ((sr = stdInput.readLine()) != null) {
                    log.info(sr);
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
	
	public static String getResponseOutput () {
		return se;
	}
	
	public static String getErrorOutput () {
		return sr;
	}
	
	/*
	 * TODO: 02/08/2016
	 * Ion has modified the status helper so that
	 * https://dit.offers.vodafone.com/spotifyHelper?opco=xx&username=yy - creates account then gives status on subsequent calls
	 * action= terminate - will terminate offers
	 * use this!!! 
	 */
}
