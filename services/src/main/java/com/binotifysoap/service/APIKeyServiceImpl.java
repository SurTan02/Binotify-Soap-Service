package com.binotifysoap.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.crypto.KeyGenerator;
import javax.jws.WebService;
import javax.xml.bind.DatatypeConverter;

import com.binotifysoap.db.DBHandler;

@WebService(
    serviceName = "APIKeyService",
    endpointInterface = "com.binotifysoap.service.APIKeyService"
)
public class APIKeyServiceImpl implements APIKeyService {

    static Connection conn = DBHandler.getConnection();
    
    @Override
    public String generateAPIKey(String client) throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);

        String APIKey = DatatypeConverter.printHexBinary(keyGenerator.generateKey().getEncoded()).toLowerCase();
        
        String query = "INSERT INTO api_keys (client, client_key) VALUE (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, client);
            statement.setString(2, APIKey);

            int success = statement.executeUpdate();

            return APIKey;
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}
