package com.binotifysoap.service;

import javax.jws.WebService;


import java.sql.*;



@WebService(endpointInterface = "com.binotifysoap.service.SubscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public String createSubscriptionDatabase() {
        
        DBHandler handler = new DBHandler();
        // Connection conn = handler.getConnection();
        // Statement statement = conn.createStatement();
        // String sql = "CREATE TABLE IF NOT EXISTS subscription (creator_id int not null auto_increment primary key, subscriber_id int not null);";
        // statement.executeUpdate(sql);

        return "jaring";
    }

    @Override
    public String addSubscription(int creator_id ,  int subscriber_id){
        try {
            
            DBHandler handler = new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO  subscription (creator_id, subscriber_id) VALUES ('%d', '%d')";
            String formattedSQL = String.format(sql, creator_id, subscriber_id);
            int count = statement.executeUpdate(formattedSQL);

            return "Penambahan berhasil. Return Value" + count;

        } catch (Exception e) {
            e.printStackTrace();
            return "error " + e.getMessage();
            // TODO: handle exception
        }


        
    }    
}
