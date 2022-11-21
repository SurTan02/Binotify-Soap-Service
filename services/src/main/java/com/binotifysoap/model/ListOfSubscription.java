package com.binotifysoap.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class ListOfSubscription {
    private List<Subscription> container;

    public ListOfSubscription() {
        container = new ArrayList<>();
    }

    @XmlElement
    public List<Subscription> getSubscription() {
        return this.container;
    }

    public void addInstance(Subscription instance) {
        this.container.add(instance);
    }
}
