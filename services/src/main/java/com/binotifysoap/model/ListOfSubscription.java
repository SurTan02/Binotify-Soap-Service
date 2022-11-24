package com.binotifysoap.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListOfSubscription {
    private List<Subscription> container;
    private int total_pages;
    private int current_page;
    

    public ListOfSubscription() {
        container = new ArrayList<>();
    }

    @XmlElement
    public List<Subscription> getSubscription() {
        return this.container;
    }

    @XmlElement
    public int getTotalPages() {
        return this.total_pages;
    }

    @XmlElement
    public int getCurrentPage() {
        return this.current_page;
    }

    public void addInstance(Subscription instance) {
        this.container.add(instance);
    }

    public void setTotalPages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setCurrentPage(int current_page) {
        this.current_page = current_page;
    }

}
