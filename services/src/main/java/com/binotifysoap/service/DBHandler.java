package com.binotifysoap.service;

import java.sql.*;

public class DBHandler {
    private Connection connection;
    private String DB_URL;
    private static String DB_Username = "user";
    private static String DB_Password = "password";

    public DBHandler() {
        // Dotenv dotenv = Dotenv.load();
        // System.out.println("MySQL Host: " + "tes");

        // APAAN NI PUSING BRO
        this.DB_URL = "jdbc:mysql://binotify-soap-service-db:3306/database";
        // this.DB_URL = "jdbc:mysql://192.168.128.3:13307/database";
        // this.DB_URL = "jdbc:mysql://0.0.0.0:13307/database";
        try {
            System.out.println("Connecting");
            this.connection = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
            System.out.println("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() { return this.connection; }
}



