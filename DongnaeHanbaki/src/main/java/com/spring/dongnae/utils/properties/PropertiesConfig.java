package com.spring.dongnae.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {
    private static final String FILE_NAME = "enviroment.properties";
    private static final Properties prop = new Properties();
    
    // static 초기화 블록을 사용하여 클래스 로딩 시 설정값 로드
    static {
    	try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_NAME)) {
    	    if (is == null) {
    	        throw new RuntimeException("environment.properties 파일을 찾을 수 없습니다.");
    	    }
    	    prop.load(is);
    	} catch (IOException e) {
    	    throw new RuntimeException("Database 설정 파일 로딩 실패", e);
    	}
    }

    public static String getName() {
        return prop.getProperty("cloudinary.cloud_name");
    }

    public static String getKey() {
        return prop.getProperty("cloudinary.api_key");
    }
    
    public static String getSecret() {
    	return prop.getProperty("cloudinary.api_secret");
    }
    
    public static String getENV() {
    	return prop.getProperty("cloudinary.api_env");
    }
    
    public static String getMongoDB() {
    	return prop.getProperty("mongodb.key");
    }
}