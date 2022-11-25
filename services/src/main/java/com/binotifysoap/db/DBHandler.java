package com.binotifysoap.db;

import java.sql.*;

public class DBHandler {

    private static String DB_URL      = "jdbc:mysql://binotify-soap-service-db:3306/database";
    private static String DB_Username = "user";
    private static String DB_Password = "password";

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
