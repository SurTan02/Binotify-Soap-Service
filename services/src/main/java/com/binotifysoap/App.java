package com.binotifysoap;

import java.util.List;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

import com.binotifysoap.security.Gateway;
import com.binotifysoap.security.Logger;
import com.binotifysoap.service.APIKeyServiceImpl;
import com.binotifysoap.service.SubscriptionServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        try{
            Logger logger = new Logger();
            Gateway gateway = new Gateway();

            Endpoint APIKeyEndpoint = Endpoint.create(new APIKeyServiceImpl()); 
            List<Handler> APIKeyEndpointHandlers = APIKeyEndpoint.getBinding().getHandlerChain();
            APIKeyEndpointHandlers.add(logger);
            APIKeyEndpoint.getBinding().setHandlerChain(APIKeyEndpointHandlers);
            APIKeyEndpoint.publish("http://0.0.0.0:8081/com/binotifysoap/APIKeyService");

            Endpoint subscriptionEndpoint = Endpoint.create(new SubscriptionServiceImpl());
            List<Handler> subscriptionEndpointHandlers = subscriptionEndpoint.getBinding().getHandlerChain();
            // subscriptionEndpointHandlers.add(gateway);
            subscriptionEndpointHandlers.add(logger);
            subscriptionEndpoint.getBinding().setHandlerChain(subscriptionEndpointHandlers);
            subscriptionEndpoint.publish("http://0.0.0.0:8081/com/binotifysoap/SubscriptionService");
            System.out.println( "Hello Sekai!" );
        }catch (Exception e){
            System.out.println( e.getMessage() );
        }
    }
}