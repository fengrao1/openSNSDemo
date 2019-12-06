package com.webtest.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

public class ReadProperties {

	public static final String filePath="conf/config.properties";	

	public static String getPropValue(String key) {
		Properties prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(filePath);
			prop.load(fis);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prop.getProperty(key);
		
	}

//	public static String getPropertyValue(String key) throws IOException {
//		Properties prop = new Properties();
//		FileInputStream fis = new FileInputStream(filePath);
//		prop.load(fis);
//		fis.close();
//		return prop.getProperty(key);
//		
//	}
}
