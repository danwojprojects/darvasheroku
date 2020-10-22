package com.wojtkowski.darvasheroku;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {

    public static final PropertiesFile INSTANCE = new PropertiesFile();
    private Properties properties;

    private PropertiesFile() {
        try {
            File file = ResourceUtils.getFile("classpath:config.properties");
            InputStream input = new FileInputStream(file);
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperties(String key){
        return  properties.getProperty(key);
    }
}
