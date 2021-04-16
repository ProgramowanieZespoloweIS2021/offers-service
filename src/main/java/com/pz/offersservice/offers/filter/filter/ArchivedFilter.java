package com.pz.offersservice.offers.filter.filter;

import com.pz.offersservice.offers.exception.InvalidFilteringCriteriaException;
import com.pz.offersservice.offers.filter.FilteringType;

import java.util.List;

public class ArchivedFilter implements FilteringCriteria {

    private static final List<FilteringType> SUPPORTED_FILTERING_TYPES = List.of(
            FilteringType.EQUAL);

    private final FilteringType filteringType;
    private final Boolean value;

    public ArchivedFilter(FilteringType filteringType, Boolean value) {
        if(!SUPPORTED_FILTERING_TYPES.contains(filteringType)) {
            throw new InvalidFilteringCriteriaException(""); // TODO: add message
        }
        this.filteringType = filteringType;
        this.value = value;
    }

    public FilteringType getFilteringType() {
        return filteringType;
    }

    public Boolean getValue() {
        return value;
    }
}
