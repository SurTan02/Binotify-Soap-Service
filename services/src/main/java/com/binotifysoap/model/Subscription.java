package com.binotifysoap.model;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Subscription")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subscription implements Comparable<Subscription> {

    @XmlElement(name = "creator_id")
    private Integer creator_id;

    @XmlElement(name = "subscriber_id")
    private Integer subscriber_id;

    @XmlElement(name = "status")
    private String status;

    public int getCreator_id() {
        return this.creator_id;
    }

    public int getSubscriber_id() {
        return this.subscriber_id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCreator_d(Integer creator_id) {
        this.creator_id = creator_id;
    }

    public void setSubscriber_id(Integer subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Subscription() {
        
    }

    public Subscription(int creator_id, int subscriber_id, String status) {
        this.creator_id = creator_id;
        this.subscriber_id = subscriber_id;
        this.status = status;
    }

    @Override
    public String toString() {
        return Integer.toString(this.creator_id) + " - " + Integer.toString(this.subscriber_id) + " | " + this.status;
    }

    @Override
    public int compareTo(Subscription o) {
        if (this.creator_id.compareTo(o.getCreator_id()) != 0) {
            return this.creator_id.compareTo(o.getCreator_id());
        }

        return this.subscriber_id.compareTo(o.getSubscriber_id());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Subscription s = (Subscription) o;
        return this.creator_id.equals(s.getCreator_id()) && this.subscriber_id.equals(s.getSubscriber_id()) && this.status.equals(s.getStatus());
    }

    public static boolean same(List<Subscription> s1, List<Subscription> s2) {
        for (int i = 0; i < s1.size(); i++) {
            if (!s1.get(i).equals(s2.get(i))) {
                return false;
            }
        }
        return true;
    }
}
