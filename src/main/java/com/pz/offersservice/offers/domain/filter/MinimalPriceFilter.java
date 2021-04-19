package com.pz.offersservice.offers.domain.filter;

import com.pz.offersservice.offers.domain.exception.InvalidFilteringCriteriaException;

import java.math.BigDecimal;
import java.util.List;

public class MinimalPriceFilter implements FilteringCriteria {

    private static final List<FilteringType> SUPPORTED_FILTERING_TYPES = List.of(
            FilteringType.LESSER,
            FilteringType.GREATER);

    private final FilteringType filteringType;
    private final BigDecimal minimalPrice;

    public MinimalPriceFilter(FilteringType filteringType, BigDecimal minimalPrice) {
        if(!SUPPORTED_FILTERING_TYPES.contains(filteringType)) {
            throw new InvalidFilteringCriteriaException(""); // TODO: add message
        }
        this.filteringType = filteringType;
        this.minimalPrice = minimalPrice;
    }

    public FilteringType getFilteringType() {
        return filteringType;
    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }
}
