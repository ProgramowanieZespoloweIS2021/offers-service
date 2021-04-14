package com.pz.offersservice.offers.filtering;

import com.pz.offersservice.offers.filtering.filter.FilteringCriteria;
import com.pz.offersservice.offers.filtering.filter.MinimalPriceFilter;
import com.pz.offersservice.offers.filtering.filter.OwnerIdFilter;
import com.pz.offersservice.offers.filtering.filter.TagsFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilteringCriteriaParser {

    private final FilteringCriteriaMapper filteringCriteriaMapper = new FilteringCriteriaMapper();


    public Optional<OwnerIdFilter> parseOwnerIdUrlParameter(String ownerIdUrlParameter) {
        if(ownerIdUrlParameter.equals("")) {
            return Optional.empty();
        }
        var filterParts = filteringCriteriaMapper.mapSingleCriteriaSpecification(ownerIdUrlParameter);
        return Optional.of(new OwnerIdFilter(filterParts.getKey(), Long.parseLong(filterParts.getValue())));
    }


    public List<MinimalPriceFilter> parseMinimalPriceUrlParameter(List<String> minimalPriceUrlParameter) {
        return minimalPriceUrlParameter.stream().map(
                singleParameter -> {
                    var filterParts = filteringCriteriaMapper.mapSingleCriteriaSpecification(singleParameter);
                    return new MinimalPriceFilter(filterParts.getKey(), new BigDecimal(filterParts.getValue()));
                }
        ).collect(Collectors.toList());
    }


    public Optional<TagsFilter> parseTagsUrlParameter(List<String> tagsUrlParameter) {
        if(tagsUrlParameter.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TagsFilter(tagsUrlParameter));
    }


    public List<FilteringCriteria> parse(String ownerIdUrlParameter,
                                         List<String> minimalPriceUrlParameter,
                                         List<String> tagsUrlParameter) {
        return Stream.of(
                parseOwnerIdUrlParameter(ownerIdUrlParameter).stream(),
                parseMinimalPriceUrlParameter(minimalPriceUrlParameter).stream(),
                parseTagsUrlParameter(tagsUrlParameter).stream()
        ).flatMap(str -> str).collect(Collectors.toList());
    }
}
