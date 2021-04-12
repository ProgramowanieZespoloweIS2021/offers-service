package com.pz.offersservice.offers.service;

import java.util.List;

public class OffersReportParameters {

    private final Integer pageSize;
    private final Integer pageOffset;
    private final List<OrderingCriteria> orderingCriteria;
    private final List<String> tags;

    public OffersReportParameters(Integer pageSize, Integer pageOffset, List<OrderingCriteria> orderingCriteria, List<String> tags) {
        this.pageSize = pageSize;
        this.pageOffset = pageOffset;
        this.orderingCriteria = orderingCriteria;
        this.tags = tags;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageOffset() {
        return pageOffset;
    }

    public List<OrderingCriteria> getOrderingCriteria() {
        return orderingCriteria;
    }

    public List<String> getTags() {
        return tags;
    }
}
