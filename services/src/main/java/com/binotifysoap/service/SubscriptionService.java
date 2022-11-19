package com.binotifysoap.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface SubscriptionService {
    @WebMethod
    public String createSubscriptionDatabase();

    @WebMethod
    public String addSubscription(int CreatorId ,  int SubscriptionId);
}
