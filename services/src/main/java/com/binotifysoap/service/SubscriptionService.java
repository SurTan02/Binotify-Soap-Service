package com.binotifysoap.service;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


import com.binotifysoap.model.ListOfSubscription;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface SubscriptionService {

    @WebMethod(operationName = "AddSubscription")
    @WebResult(name = "Subscriptions")
    public String addSubscription(
        @WebParam(name = "creator_id") 
        int creator_id,

        @WebParam(name = "subscriber_id")  
        int subscriber_id
    ) throws Exception;

    @WebMethod(operationName = "UpdateSubscription")
    @WebResult(name = "Subscriptions")
    // @WebParam(name="<name you want in soap>")
    public String updateSubscription(
        @WebParam(name = "creator_id") 
        int creator_id,

        @WebParam(name = "subscriber_id")  
        int subscriber_id,

        @WebParam(name = "status")  
        String status
    ) throws Exception;

    @WebMethod(operationName = "GetSubscription")
    @WebResult(name = "Subscriptions")
    public ListOfSubscription getSubscription(
        @WebParam(name = "current_page")
        int current_page
    ) throws Exception;
}
