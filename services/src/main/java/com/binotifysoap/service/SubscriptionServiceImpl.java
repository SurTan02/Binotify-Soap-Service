package com.binotifysoap.service;

import com.binotifysoap.db.DBHandler;
import com.binotifysoap.model.ListOfSubscription;
import com.binotifysoap.model.Subscription;

import java.sql.*;

import javax.jws.WebService;

@WebService(
    serviceName = "SubscriptionService",
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
            e.printStackTrace();
            return "error " + e.getMessage();
            // TODO: handle exception
        }    
    }
    
    @Override
    public String updateSubscription(int creator_id ,  int subscriber_id, String status){
        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE subscription SET status = ('%s') WHERE creator_id ='%d' AND subscriber_id = '%d'";
            String formattedSQL = String.format(sql, status, creator_id, subscriber_id);
            int count = statement.executeUpdate(formattedSQL);

            return "Pengubahan berhasil. Return Value" + count;

        } catch (Exception e) {
            e.printStackTrace();
            return "error " + e.getMessage();
            // TODO: handle exception
        }    
    }

    @Override
    public ListOfSubscription getSubscription(int current_page){
    // public Subscription[] getSubscription(){
        
        ListOfSubscription arrayOfSubscription = new ListOfSubscription();
        try {
            Statement statement = conn.createStatement();
            
            // Count number of subscription
            String sql = "SELECT COUNT(*) FROM subscription";
            ResultSet rs = statement.executeQuery(sql);
            
            rs.next();
            int total_subscription = rs.getInt("COUNT(*)");
            arrayOfSubscription.setTotalPages((total_subscription/10) + 1); 
            arrayOfSubscription.setCurrentPage(current_page);
            
            sql = "SELECT * FROM subscription LIMIT 10 OFFSET %d";
            String formattedSQL = String.format(sql, (current_page-1) * 10 );
            rs = statement.executeQuery(formattedSQL);
            
            while(rs.next()) {
                Subscription instance = new Subscription(rs.getInt("creator_id"),
                                                         rs.getInt("subscriber_id"),
                                                         rs.getString("status"));
                arrayOfSubscription.addInstance(instance);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return arrayOfSubscription;
    }
}
