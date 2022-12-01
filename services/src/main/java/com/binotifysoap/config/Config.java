package com.binotifysoap.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;


public class Config{
    private static HashMap<String, String> configurations;
    
    static {    
        try {
            configurations = new HashMap<>();   
            // APP PORT
            String APP_PORT = getEnv("BINOTIFY_SOAP_SERVICE_APP_PORT", "8081");
            configurations.put("APP_PORT", APP_PORT);
            
            
            String REST_PORT = getEnv("BINOTIFY_REST_SERVICE_PORT", "8080");
            configurations.put("REST_PORT", REST_PORT);
            
            String REST_HOST = getEnv("BINOTIFY_REST_SERVICE_HOST", "binotify-rest-service-app");
            configurations.put("REST_HOST", REST_HOST);

            String DB_HOST = getEnv("BINOTIFY_SOAP_SERVICE_DB_HOST", "binotify-soap-service-db");
            configurations.put("DB_HOST", DB_HOST);

            String DB_PORT = getEnv("BINOTIFY_SOAP_SERVICE_DB_PORT", "3306");
            configurations.put("DB_PORT", DB_PORT);

            String DB_NAME = getEnv("BINOTIFY_SOAP_SERVICE_DB_NAME", "database");
            configurations.put("DB_NAME", DB_NAME);

            String DB_Username = getEnv("BINOTIFY_SOAP_SERVICE_DB_USER", "user");
            configurations.put("DB_Username", DB_Username);

            String DB_Password = getEnv("BINOTIFY_SOAP_SERVICE_DB_PASSWORD", "password");
            configurations.put("DB_Password", DB_Password);

            String DB_URL  = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
            configurations.put("DB_URL", DB_URL);

            String EMAIL  = getEnv("EMAIL", "suryanto.tan5@gmail.com");
            configurations.put("EMAIL", EMAIL);

            String EMAIL_PASSWORD  = getEnv("EMAIL_PASSWORD", "suryanto.tan5@gmail.com");
            configurations.put("EMAIL_PASSWORD", EMAIL_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getConfig(String key){
        return configurations.get(key);
    }

    private static String getEnv(String key, String defaultValue){
        Dotenv dotenv = Dotenv.load();

        String value = dotenv.get(key, defaultValue);

        if (value.length() == 0) value = defaultValue;
        return value;
    }
}
