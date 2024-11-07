package com.opencart.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileReader
{
	 private static Properties prop;

	    // Method to load properties
	    private static void loadProperties() {
	        if (prop == null) {
	            prop = new Properties();
	            try {
	                FileInputStream fstream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
	                prop.load(fstream);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    // Method to get the properties object
	    public static Properties getProperties() {
	        if (prop == null) {
	            loadProperties();  // Ensure properties are loaded if not already done
	        }
	        return prop;
	    }
	}


