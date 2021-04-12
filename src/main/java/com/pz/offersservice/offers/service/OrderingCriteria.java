package com.pz.offersservice.offers.service;

public class OrderingCriteria {

    private final String property;
    private final String direction;

    public OrderingCriteria(String property, String direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public String getDirection() {
        return direction;
    }
}
