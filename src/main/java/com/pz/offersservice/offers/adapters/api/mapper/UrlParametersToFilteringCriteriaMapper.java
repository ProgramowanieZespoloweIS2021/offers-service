package com.pz.offersservice.offers.adapters.api.mapper;

import com.pz.offersservice.offers.domain.exception.InvalidFilteringCriteriaException;
import com.pz.offersservice.offers.domain.filter.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class UrlParametersToFilteringCriteriaMapper {

    private final Pattern filteringSpecificationPattern = Pattern.compile("^([A-z]+):([A-z0-9]+)$");
    private final Map<String, FilteringType> FILTERING_TYPES_BY_STRING_REPRESENTATIONS = Map.ofEntries(
            new AbstractMap.SimpleImmutableEntry<>("lt", FilteringType.LESSER),
            new AbstractMap.SimpleImmutableEntry<>("le", FilteringType.LESSER_OR_EQUAL),
            new AbstractMap.SimpleImmutableEntry<>("gt", FilteringType.GREATER),
            new AbstractMap.SimpleImmutableEntry<>("ge", FilteringType.GREATER_OR_EQUAL),
            new AbstractMap.SimpleImmutableEntry<>("eq", FilteringType.EQUAL)
    );


    public List<FilteringCriteria> map(String ownerIdUrlParameter, List<String> minimalPriceUrlParameter, List<String> tagsUrlParameter) {
        return Stream.of(
                parseOwnerIdUrlParameter(ownerIdUrlParameter).stream(),
                parseMinimalPriceUrlParameter(minimalPriceUrlParameter).stream(),
                parseTagsUrlParameter(tagsUrlParameter).stream()
        ).flatMap(str -> str).collect(Collectors.toList());
    }


    private Optional<OwnerIdFilter> parseOwnerIdUrlParameter(String ownerIdUrlParameter) {
        if(ownerIdUrlParameter.equals("")) {
            return Optional.empty();
        }
        AbstractMap.SimpleImmutableEntry<FilteringType, String> filterParts = mapSingleCriteriaSpecification(ownerIdUrlParameter);
        return Optional.of(new OwnerIdFilter(filterParts.getKey(), Long.parseLong(filterParts.getValue())));
    }


    private List<MinimalPriceFilter> parseMinimalPriceUrlParameter(List<String> minimalPriceUrlParameter) {
        return minimalPriceUrlParameter.stream().map(
                singleParameter -> {
                    var filterParts = mapSingleCriteriaSpecification(singleParameter);
                    return new MinimalPriceFilter(filterParts.getKey(), new BigDecimal(filterParts.getValue()));
                }
        ).collect(Collectors.toList());
    }


    private Optional<TagsFilter> parseTagsUrlParameter(List<String> tagsUrlParameter) {
        if(tagsUrlParameter.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TagsFilter(tagsUrlParameter));
    }


    private AbstractMap.SimpleImmutableEntry<String, String> splitFilteringSpecification(String filteringSpecification) {
        Matcher filteringSpecificationMatcher = filteringSpecificationPattern.matcher(filteringSpecification);
        if(filteringSpecificationMatcher.matches()) {
            String filteringTypeSpecification = filteringSpecificationMatcher.group(1);
            String filteringValueSpecification = filteringSpecificationMatcher.group(2);
            return new AbstractMap.SimpleImmutableEntry<>(filteringTypeSpecification, filteringValueSpecification);
        }
        else {
            throw new InvalidFilteringCriteriaException("Invalid filtering specification"); // TODO: expand message
        }
    }


    private AbstractMap.SimpleImmutableEntry<FilteringType, String> mapSingleCriteriaSpecification(String filteringSpecification) {
        var filteringSpecificationParts = splitFilteringSpecification(filteringSpecification);
        var filteringTypeSpecification = filteringSpecificationParts.getKey();
        var filteringValueSpecification = filteringSpecificationParts.getValue();
        var filteringType = FILTERING_TYPES_BY_STRING_REPRESENTATIONS.get(filteringTypeSpecification);
        return new AbstractMap.SimpleImmutableEntry<>(filteringType, filteringValueSpecification);
    }

}
