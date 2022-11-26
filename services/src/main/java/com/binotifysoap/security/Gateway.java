package com.binotifysoap.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.binotifysoap.db.DBHandler;

public class Gateway implements SOAPHandler<SOAPMessageContext> {

    static Connection conn = DBHandler.getConnection();
    
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

        String query = "SELECT COUNT(*) FROM api_keys WHERE client_key = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, APIKey);

            ResultSet result = statement.executeQuery();

            result.next();
            if (result.getInt("COUNT(*)") > 0) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        throwFault(context);
        return false;
    }

    public void throwFault(SOAPMessageContext context) throws SOAPFaultException {
        System.out.println("[API KEY] Forbidden");

        try {
            SOAPBody body = context.getMessage().getSOAPBody();
            SOAPFault fault = body.addFault();
            fault.setFaultString("Forbidden");

            throw new SOAPFaultException(fault);
        } catch (Exception e) {

        }
    }
}
