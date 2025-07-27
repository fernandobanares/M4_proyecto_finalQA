package com.qa.automation.config;

public class ConfigManager {
    
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final int IMPLICIT_WAIT = 15;
    private static final int EXPLICIT_WAIT = 30;
    
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    public static int getImplicitWait() {
        return IMPLICIT_WAIT;
    }
    
    public static int getExplicitWait() {
        return EXPLICIT_WAIT;
    }
}
