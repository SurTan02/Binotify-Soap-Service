package com.binotifysoap.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class Subscription {
    private int creator_id;
    private int subscriber_id;
    private String status;

    public Subscription() {
        
    }

    public Subscription(int creator_id, int subscriber_id, String status) {
        this.creator_id = creator_id;
        this.subscriber_id = subscriber_id;
        this.status = status;
    }

    @XmlElement
    public int getCreatorId() {
        return this.creator_id;
    }

    @XmlElement
    public int getSubscriberId() {
        return this.subscriber_id;
    }

    @XmlElement
    public String getStatus() {
        return this.status;
    }
}
