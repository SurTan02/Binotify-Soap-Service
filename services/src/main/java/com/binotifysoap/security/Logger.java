package com.binotifysoap.security;

import java.sql.*;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.binotifysoap.db.DBHandler;
import com.sun.net.httpserver.*;

public class Logger implements SOAPHandler<SOAPMessageContext> {

    static Connection conn = DBHandler.getConnection();

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if ((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))
            log(context, true);
        
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        if ((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))
            log(context, false);

        return false;
    }

    @Override
    public void close(MessageContext context) {
        
    }

    @Override
    public Set<QName> getHeaders() {

        return null;
    }

    public void log(SOAPMessageContext context, boolean isSuccess) {
        String desc = (isSuccess ? "[Success] " : "[Error] ") + getDescription(context);
        String ip = getIP(context);
        String endpoint = getEndpoint(context);
        String ts = new Timestamp(System.currentTimeMillis()).toString();
        
        String query = "INSERT INTO logging (description, IP, endpoint, requested_at) VALUE (?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, desc);
            statement.setString(2, ip);
            statement.setString(3, endpoint);
            statement.setString(4, ts);

            int success = statement.executeUpdate();

            if (success == 1) System.out.println("[LOG] [" + ts + "] [" + ip + "] [" + endpoint +  "] " + desc);
            else System.out.println("[LOG] Failed to save log!");
        } catch (Exception e) {
            // TO DO: Handle Exception
            System.out.println("[LOG] error: " + e.getMessage());
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
