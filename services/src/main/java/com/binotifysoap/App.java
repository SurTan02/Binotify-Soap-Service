package com.binotifysoap;

import java.util.List;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

import com.binotifysoap.helper.Logging;

import com.binotifysoap.service.SubscriptionServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        try{
            Endpoint endpoint = Endpoint.create(new SubscriptionServiceImpl());

            List<Handler> handlers = endpoint.getBinding().getHandlerChain();

            handlers.add(new Logging());
            endpoint.getBinding().setHandlerChain(handlers);

            endpoint.publish("http://0.0.0.0:8081/com/binotifysoap/SubscriptionService");
            // Endpoint.publish("http://0.0.0.0:8081/com/binotifysoap/SubscriptionService", new SubscriptionServiceImpl());

            System.out.println( "Hello Sekai!" );
        }catch (Exception e){
            System.out.println( e.getMessage() );
        }
    }
}