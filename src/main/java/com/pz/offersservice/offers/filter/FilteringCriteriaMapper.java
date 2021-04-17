package com.pz.offersservice.offers.filter;

import com.pz.offersservice.offers.exception.InvalidFilteringCriteriaException;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilteringCriteriaMapper {

    private final Pattern filteringSpecificationPattern = Pattern.compile("^([A-z]+):([A-z0-9]+)$");
    private final Map<String, FilteringType> FILTERING_TYPES_BY_STRING_REPRESENTATIONS = Map.ofEntries(
            new AbstractMap.SimpleImmutableEntry<>("lt", FilteringType.LESSER),
            new AbstractMap.SimpleImmutableEntry<>("le", FilteringType.LESSER_OR_EQUAL),
            new AbstractMap.SimpleImmutableEntry<>("gt", FilteringType.GREATER),
            new AbstractMap.SimpleImmutableEntry<>("ge", FilteringType.GREATER_OR_EQUAL),
            new AbstractMap.SimpleImmutableEntry<>("eq", FilteringType.EQUAL)
    );


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


    public AbstractMap.SimpleImmutableEntry<FilteringType, String> mapSingleCriteriaSpecification(String filteringSpecification) {
        var filteringSpecificationParts = splitFilteringSpecification(filteringSpecification);
        var filteringTypeSpecification = filteringSpecificationParts.getKey();
        var filteringValueSpecification = filteringSpecificationParts.getValue();
        var filteringType = FILTERING_TYPES_BY_STRING_REPRESENTATIONS.get(filteringTypeSpecification);
        return new AbstractMap.SimpleImmutableEntry<>(filteringType, filteringValueSpecification);
    }

}
