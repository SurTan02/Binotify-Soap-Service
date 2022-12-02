package com.binotifysoap.service;

import com.binotifysoap.db.DBHandler;
import com.binotifysoap.model.ListOfSubscription;
import com.binotifysoap.model.Subscription;
import com.binotifysoap.security.Mail;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        
        int count;
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, creator_id);;
            statement.setInt(2, subscriber_id);;
            count = statement.executeUpdate();
        }
        String message = String.format("subscriber with id %s want to subscribe to creator with id %s",subscriber_id, creator_id);
        Mail.sendEmail(message);
        return "Penambahan berhasil. Row added " + count;
    }
    
    @Override
    public String updateSubscription(int creator_id, int subscriber_id, String status) throws Exception {
        
        String query = "UPDATE subscription SET status =? WHERE creator_id = ? AND subscriber_id = ?";
        int count;

        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setObject(1, status);
            statement.setInt(2, creator_id);;
            statement.setInt(3, subscriber_id);;
            count = statement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            throw new Exception(e.getMessage());
        }

        URL url = new URL("http://binotify-app-web:80/app/endpoint/updateStatus.php");
        HttpURLConnection php_conn = (HttpURLConnection) url.openConnection();
        String postParams = "creator_id=" + Integer.toString(creator_id) + "&subscriber_id=" + Integer.toString(subscriber_id) + "&status=" + status;
        php_conn.setRequestMethod("POST");

        php_conn.setDoOutput( true );
        try ( OutputStream os = php_conn.getOutputStream(); ) {
            os.write(postParams.getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
             System.out.println("[ERROR] " + e.getMessage());
        }

        int responseCode = php_conn.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("[ERROR] HTTP Error Code: " + Integer.toString(responseCode));
        }

        return "Pengubahan berhasil. Row updated " + count;
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
            arrayOfSubscription.setTotalPages(((total_subscription-1)/10) + 1); 
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
    
    @Override
    public String validateSubscription(int creator_id,int subscriber_id ) throws Exception {
        String query = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ? LIMIT 1";

        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, creator_id);
            statement.setInt(2, subscriber_id);

            ResultSet rs = statement.executeQuery();
            rs.next();
            return (rs.getString("status"));
        }catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            throw new Exception(e.getMessage());
        } 
    }

    @Override
    public Subscription[] pollSubscriptionsStatus(Subscription[] subscriptions) throws Exception {

        if (subscriptions.length == 0) {
            Thread.sleep(5000);
            return new Subscription[0];
        }

        int flag = 0;

        while (flag < 10) {

            Arrays.sort(subscriptions);

            List<Subscription> req = new ArrayList<Subscription>(Arrays.asList(subscriptions));

            String query = "SELECT * FROM subscription WHERE status != 'PENDING' AND ";

            for (int i = 0; i < req.size() - 1; i++) {
                query += "(creator_id = ? AND subscriber_id = ?) OR ";
            }

            query += "(creator_id = ? AND subscriber_id = ?) ORDER BY creator_id, subscriber_id";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                
                for (int i = 0; i < req.size(); i++) {
                    statement.setInt(i * 2 + 1, req.get(i).getCreator_id());
                    statement.setInt(i * 2 + 2, req.get(i).getSubscriber_id());
                }

                ResultSet result = statement.executeQuery();

                List<Subscription> tempResult = new ArrayList<Subscription>();

                while (result.next()) {
                    Subscription s = new Subscription(
                        result.getInt("creator_id"), 
                        result.getInt("subscriber_id"), 
                        result.getString("status"));

                    tempResult.add(s);
                }

                // if (Subscription.same(req, tempResult)) {
                //     Thread.sleep(1000);
                //     flag++;
                // } else {
                //     // TO DO: return only status not "PENDING"
                //     return tempResult.toArray(new Subscription[tempResult.size()]);
                // }

                if (tempResult.size() == 0) {
                    Thread.sleep(1000);
                    flag++;
                } else {
                    return tempResult.toArray(new Subscription[tempResult.size()]);
                }

            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage());
                throw new Exception(e.getMessage());
            }
        }
    
        return new Subscription[0];
    }
}
