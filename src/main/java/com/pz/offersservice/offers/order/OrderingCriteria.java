package com.pz.offersservice.offers.order;

public class OrderingCriteria {

    private final String property;
    private final OrderingType orderingType;

    public OrderingCriteria(String property, OrderingType orderingType) {
        this.property = property;
        this.orderingType = orderingType;
    }

    public String getProperty() {
        return property;
    }

    public OrderingType getOrderingType() {
        return orderingType;
    }
}
