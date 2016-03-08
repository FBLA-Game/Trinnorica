package org.fbla.game.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesFile {
	
	private Properties properties = new Properties();
	public PropertiesFile(File file) {
		try {
			properties.load(new FileInputStream(file));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public String getProperty(String property){
		if(property == "version")
			return properties.getProperty("major") + "." + properties.getProperty("minor") + "." + properties.getProperty("distro");
		return properties.getProperty(property);
	}

}
