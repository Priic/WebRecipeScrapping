package com.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	public static String getPropFromProperty(String filename,String key) throws IOException {
		// web driver will be launched here
				FileInputStream fis = new FileInputStream("src/test/resources/propertiesFiles/"+filename+".properties");
				Properties prop = new Properties(); // creates a empty property list with no default values
				prop.load(fis);
				return prop.getProperty(key);
	}
}
