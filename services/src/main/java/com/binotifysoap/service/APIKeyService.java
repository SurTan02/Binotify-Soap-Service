package com.binotifysoap.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface APIKeyService {
    
    @WebMethod(operationName = "GenerateAPIKey")
    @WebResult(name = "APIKey")
    public String generateAPIKey(
        @WebParam(name = "client") 
        String client
    ) throws Exception;

}
