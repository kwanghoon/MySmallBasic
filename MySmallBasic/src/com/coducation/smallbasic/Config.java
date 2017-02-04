package com.coducation.smallbasic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	private static final String mostrecentsmallbasicfile = "mostrecentsmallbasicfile";
	private static final String debug = "debug";
	
	// The most recent Small Basic file
	public static String getMostRecentSmallBasicFile() {
		return get(mostrecentsmallbasicfile);
	}
	
	public static void putMostRecentSmallBasicFile(String filename) {
		put(mostrecentsmallbasicfile, filename);
	}
	
	public static boolean getDebug() {
		String s = get(debug);
		if ("true".equalsIgnoreCase(s))
			return true;
		else
			return false;
	}
	
	public static void putDebug(boolean b) {
		if (b)
			put(debug, "true");
		else
			put(debug, "false");
	}
	
	
	// Utility
	private static final String configFile = "mysmallbasic.config";

	public static String get(String key) {
		Properties prop = readConfig();
		return prop.getProperty(key);
	}	
	
	public static void put(String key, String value) {
		Properties prop = readConfig();
		OutputStream output = null;
		
		try {
			output = new FileOutputStream(configFile);
			
			prop.setProperty(key, value);
			
			prop.store(output, null);
		}
		catch(IOException io) {
		}
		finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static Properties readConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream(configFile);
			
			prop.load(input);
		}
		catch(IOException ex) {
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}	
		}
		return prop;
	}
}
