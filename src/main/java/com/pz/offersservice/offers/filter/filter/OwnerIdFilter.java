package com.pz.offersservice.offers.filter.filter;

import com.pz.offersservice.offers.exception.InvalidFilteringCriteriaException;
import com.pz.offersservice.offers.filter.FilteringType;

import java.util.List;

public class OwnerIdFilter implements FilteringCriteria {

    private static final List<FilteringType> SUPPORTED_FILTERING_TYPES = List.of(
            FilteringType.EQUAL);

    private final FilteringType filteringType;
    private final Long ownerId;

    public OwnerIdFilter(FilteringType filteringType, Long ownerId) {
        if(!SUPPORTED_FILTERING_TYPES.contains(filteringType)) {
            throw new InvalidFilteringCriteriaException(""); // TODO: add message
        }
        this.filteringType = filteringType;
        this.ownerId = ownerId;
    }

    public FilteringType getFilteringType() {
        return filteringType;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
