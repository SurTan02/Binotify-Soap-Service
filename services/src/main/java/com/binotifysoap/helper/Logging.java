package com.binotifysoap.helper;

import java.sql.*;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.binotifysoap.db.DBHandler;
import com.sun.net.httpserver.*;

public class Logging implements SOAPHandler<SOAPMessageContext> {

    static Connection conn = DBHandler.getConnection();

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if (!((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) 
            log(context);
        
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {
        
    }

    @Override
    public Set<QName> getHeaders() {

        return null;
    }

    public void log(SOAPMessageContext context) {
        String desc = getDescription(context);
        String ip = getIP(context);
        String endpoint = getEndpoint(context);

        try {
            String query = "INSERT INTO logging (description, IP, endpoint) VALUE (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, desc);
            statement.setString(2, ip);
            statement.setString(3, endpoint);

            int succsess = statement.executeUpdate();

            if (succsess == 1) System.out.println("[LOG] [" + ip + "] [" + endpoint +  "] " + desc);
            else System.out.println("[LOG] Failed to save log!");

        } catch (Exception e) {
            // TO DO: Handle Exception
            e.printStackTrace();
        }
    }

    public String getIP(SOAPMessageContext context) {
        HttpExchange exchange = (HttpExchange) context.get("com.sun.xml.ws.http.exchange");
        String remoteHost = exchange.getRemoteAddress().getHostString();

        return remoteHost;
    }

    public String getEndpoint(SOAPMessageContext context) {
        // String endpoint = (String) ((LinkedList) ((Map) context.get(MessageContext.HTTP_REQUEST_HEADERS)).get("Host")).getFirst();
        String endpoint  = ((QName) context.get(MessageContext.WSDL_SERVICE)).toString().replaceAll("\\{.*?\\}", "");

        return "/" + endpoint;
    }

    public String getDescription(SOAPMessageContext context) {
        return ((QName) context.get(MessageContext.WSDL_OPERATION)).toString().replaceAll("\\{.*?\\}", "");
    }
    
}
