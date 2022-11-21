package com.binotifysoap.service;

import javax.jws.WebService;

import com.binotifysoap.model.ListOfSubscription;
import com.binotifysoap.model.Subscription;

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
    
    @Override
    public ListOfSubscription getSubscription(){
    // public Subscription[] getSubscription(){
        ListOfSubscription arrayOfSubscription = new ListOfSubscription();
        // JSONObject jsonObject = new JSONObject();
        try {
            DBHandler handler = new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM subscription";
            ResultSet rs = statement.executeQuery(sql);
            
            // JSONArray array = new JSONArray();
            // Subscription[] arrayOfSubscription = new Subscription[3];
            while(rs.next()) {
                Subscription instance = new Subscription(rs.getInt("creator_id"),
                                                         rs.getInt("subscriber_id"),
                                                         rs.getString("status"));
                // arrayOfSubscription[i] = (instance);
                arrayOfSubscription.addInstance(instance);
                // JSONObject record = new JSONObject();
                //Inserting key-value pairs into the json object
                // record.put("creator_id", rs.getInt("creator_id"));
                // record.put("subscriber_id", rs.getInt("subscriber_id"));
                // record.put("status", rs.getString("status"));
                // array.add(record);
                // arrayOfSubscription[i] = rs.getString("creator_id");
            }
            // jsonObject.put("Subscription Data", array);
            
            System.out.println("hehehe "+ arrayOfSubscription);
            return arrayOfSubscription;
        } catch (Exception e) {
            // TODO: handle exception
            // Subscription[] arrayOfSubscription = new Subscription[3];
            // jsonObject.put("Subscription Data", e.getMessage());
            
            return arrayOfSubscription;
        }
    }
}
