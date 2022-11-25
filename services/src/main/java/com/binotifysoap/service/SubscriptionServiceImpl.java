package com.binotifysoap.service;

import com.binotifysoap.db.DBHandler;
import com.binotifysoap.model.ListOfSubscription;
import com.binotifysoap.model.Subscription;

import java.sql.*;

import javax.jws.WebService;

@WebService(
    serviceName = "SubsctiptionService",
    endpointInterface = "com.binotifysoap.service.SubscriptionService" 
)
public class SubscriptionServiceImpl implements SubscriptionService {

    static Connection conn = DBHandler.getConnection();

    @Override
    public String addSubscription(int creator_id, int subscriber_id) {
        try {
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO  subscription (creator_id, subscriber_id) VALUES ('%d', '%d')";
            String formattedSQL = String.format(sql, creator_id, subscriber_id);
            int count = statement.executeUpdate(formattedSQL);

            return "Penambahan berhasil. Return Value" + count;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("[ERROR] " + e.getMessage());
            return "error " + e.getMessage();
        }    
    }
    
    @Override
    public String updateSubscription(int creator_id, int subscriber_id, String status) {
        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE subscription SET status = ('%s') WHERE creator_id ='%d' AND subscriber_id = '%d'";
            String formattedSQL = String.format(sql, status, creator_id, subscriber_id);
            int count = statement.executeUpdate(formattedSQL);

            return "Pengubahan berhasil. Return Value" + count;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("[ERROR] " + e.getMessage());
            return "error " + e.getMessage();
        }    
    }

    @Override
    public ListOfSubscription getSubscription() {
        
        ListOfSubscription arrayOfSubscription = new ListOfSubscription();

        try {
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM subscription";
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Subscription instance = new Subscription(rs.getInt("creator_id"),
                                                         rs.getInt("subscriber_id"),
                                                         rs.getString("status"));
                arrayOfSubscription.addInstance(instance);
            }
            return arrayOfSubscription;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("[ERROR] " + e.getMessage());
            return arrayOfSubscription;
        }
    }
}
