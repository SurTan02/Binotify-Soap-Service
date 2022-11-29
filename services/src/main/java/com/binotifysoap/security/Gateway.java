package com.binotifysoap.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.binotifysoap.db.DBHandler;

public class Gateway implements SOAPHandler<SOAPMessageContext> {

    static Connection conn = DBHandler.getConnection();

    private static Map<String,List<String>> ALLOWED_METHODS = new HashMap<String,List<String>>() {{
        put("BINOTIFY REST SERVICE", Arrays.asList("UpdateSubscription", "GetSubscription"));
        put("BINOTIFY APP"         , Arrays.asList("AddSubscription"));
    }};

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if (!((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)))
            return checkAPIKey(context);

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
    
    public boolean checkAPIKey(SOAPMessageContext context) {
        String APIKey;
        try {
            APIKey = (String) (((LinkedList) ((Map) context.get(MessageContext.HTTP_REQUEST_HEADERS)).get("x-api-key")).getFirst());
        } catch (Exception e) {
            throwFault(context);
            return false;
        }

        String query = "SELECT * FROM api_keys WHERE client_key = ? LIMIT 1";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, APIKey);

            ResultSet result = statement.executeQuery();

            result.next();
            if (ALLOWED_METHODS.get(result.getString("client")).contains(getMethod(context))) {
                return true;
            } 

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        throwFault(context);
        return false;
    }

    public String getMethod(SOAPMessageContext context) {
        return ((QName) context.get(MessageContext.WSDL_OPERATION)).toString().replaceAll("\\{.*?\\}", "");
    }

    public void throwFault(SOAPMessageContext context) throws SOAPFaultException {
        System.out.println("[API KEY] Forbidden");

        try {
            SOAPBody body = context.getMessage().getSOAPBody();
            SOAPFault fault = body.addFault();
            fault.setFaultString("Forbidden");

            throw new SOAPFaultException(fault);
        } catch (Exception e) {
            throw new SOAPFaultException(null);
        }
    }
}
