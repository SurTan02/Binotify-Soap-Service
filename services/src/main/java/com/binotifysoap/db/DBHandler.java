package com.binotifysoap.db;

import java.sql.*;
import com.binotifysoap.config.Config;

public class DBHandler {
    private static String DB_URL = Config.getConfig("DB_URL");
    private static String DB_Username = Config.getConfig("DB_Username");
    private static String DB_Password = Config.getConfig("DB_Password");

    private static Connection connection = null;
    
    static {    
        try {
            connection = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
            System.out.println("Database Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() { 
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
                System.out.println("Database Connected!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return connection; 
    }
}
