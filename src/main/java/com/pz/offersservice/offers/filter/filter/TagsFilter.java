package com.pz.offersservice.offers.filter.filter;

import java.util.List;

public class TagsFilter implements FilteringCriteria {

    private final List<String> tags;

    public TagsFilter(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }
}
