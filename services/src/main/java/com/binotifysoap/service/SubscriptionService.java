package com.binotifysoap.service;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


import com.binotifysoap.model.ListOfSubscription;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface SubscriptionService {
    @WebMethod
    @WebResult(name = "Subscriptions")
    public String createSubscriptionDatabase();

    @WebMethod
    @WebResult(name = "Subscriptions")
    public String addSubscription(int CreatorId ,  int SubscriptionId);

    @WebMethod
    @WebResult(name = "Subscriptions")
    public ListOfSubscription getSubscription();
}
