package com.binotifysoap;

import javax.xml.ws.Endpoint;

// import com.binotifysoap.service.DBHandler;
import com.binotifysoap.service.SubscriptionServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        try{
            // Endpoint.publish("http://localhost:8090/com/binotifysoap/SubscriptionService", args)
            Endpoint.publish("http://0.0.0.0:8081/com/binotifysoap/SubscriptionService", new SubscriptionServiceImpl());
            // Endpoint.publish("http://localhost:8090/com/binotifysoap/SubscriptionService", new SubscriptionServiceImpl());
            System.out.println( "Hello Sekai!" );
        }catch (Exception e){
            System.out.println( e.getMessage() );
        }
    }
}