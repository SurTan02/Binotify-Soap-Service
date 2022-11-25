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
    public String addSubscription(int creator_id, int subscriber_id) throws Exception {
        
        String query = "INSERT INTO  subscription (creator_id, subscriber_id) VALUES (?, ?)";
        
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, creator_id);;
            statement.setInt(2, subscriber_id);;
            int count = statement.executeUpdate();
            // Statement statement = conn.createStatement();

            return "Penambahan berhasil. Row added "+ count;

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            throw new Exception(e.getMessage());
        }    
    }
    
    @Override
    public String updateSubscription(int creator_id, int subscriber_id, String status) throws Exception {
        
        String query = "UPDATE subscription SET status =? WHERE creator_id = ? AND subscriber_id = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setObject(1, status);
            statement.setInt(2, creator_id);;
            statement.setInt(3, subscriber_id);;
            int count = statement.executeUpdate();
            return "Pengubahan berhasil. Row updated " + count;

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            throw new Exception(e.getMessage());
        }    
    }

    @Override
    public ListOfSubscription getSubscription(int current_page) throws Exception {
        
        ListOfSubscription arrayOfSubscription = new ListOfSubscription();
        String query = "SELECT COUNT(*) FROM subscription";

        try{
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            
            rs.next();
            int total_subscription = rs.getInt("COUNT(*)");
            arrayOfSubscription.setTotalPages((total_subscription/10) + 1); 
            arrayOfSubscription.setCurrentPage(current_page);
            
            query = "SELECT * FROM subscription LIMIT 10 OFFSET %d";
            query = String.format(query, (current_page-1) * 10);

            // Hmm pake bind prepared statement gabisa...
            // statement.setInt(1,  (current_page-1) * 10);
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery(query);
            
            while(rs.next()) {
                Subscription instance = new Subscription(rs.getInt("creator_id"),
                                                         rs.getInt("subscriber_id"),
                                                         rs.getString("status"));
                arrayOfSubscription.addInstance(instance);
            }
            return arrayOfSubscription;
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
