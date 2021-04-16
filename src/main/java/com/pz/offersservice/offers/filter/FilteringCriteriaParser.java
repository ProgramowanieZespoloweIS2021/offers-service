package com.pz.offersservice.offers.filter;

import com.pz.offersservice.offers.filter.filter.FilteringCriteria;
import com.pz.offersservice.offers.filter.filter.MinimalPriceFilter;
import com.pz.offersservice.offers.filter.filter.OwnerIdFilter;
import com.pz.offersservice.offers.filter.filter.TagsFilter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
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
