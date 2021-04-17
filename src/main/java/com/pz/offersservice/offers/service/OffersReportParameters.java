package com.pz.offersservice.offers.service;

import com.pz.offersservice.offers.filter.filter.FilteringCriteria;
import com.pz.offersservice.offers.order.OrderingCriteria;

import java.util.List;

public class OffersReportParameters {

    private final Integer pageSize;
    private final Integer pageOffset;
    private final List<OrderingCriteria> orderingCriteria;
    private final List<FilteringCriteria> filteringCriteria;

    public OffersReportParameters(Integer pageSize, Integer pageOffset, List<OrderingCriteria> orderingCriteria, List<FilteringCriteria> filteringCriteria) {
        this.pageSize = pageSize;
        this.pageOffset = pageOffset;
        this.orderingCriteria = orderingCriteria;
        this.filteringCriteria = filteringCriteria;
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

    public List<FilteringCriteria> getFilteringCriteria() {
        return filteringCriteria;
    }
}
