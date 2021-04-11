package com.pz.offersservice.offers.service;

import org.jooq.SortField;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jooq.impl.DSL.field;

@Component
public class OrderingCriteriaFactory {

    private static final Set<String> POSSIBLE_ORDERING_URL_PARAMETERS = Stream.of(
            "title",
            "creation_timestamp",
            "lowest_price"
    ).collect(Collectors.toCollection(HashSet::new));

    private static final Pattern DESCENDING_PATTERN = Pattern.compile("^desc\\(([A-z]+)\\)$");
    private static final Pattern ASCENDING_PATTERN = Pattern.compile("^asc\\(([A-z]+)\\)$");


    private SortField<?> parseSingleUrlParameter(String urlParameter) {
        Matcher descendingMatcher = DESCENDING_PATTERN.matcher(urlParameter);
        Matcher ascendingMatcher = ASCENDING_PATTERN.matcher(urlParameter);
        if(descendingMatcher.matches()) {
            String orderingProperty = descendingMatcher.group(1);
            if(POSSIBLE_ORDERING_URL_PARAMETERS.contains(orderingProperty)) {
                return field("cte1." + orderingProperty).desc();
            }
        }
        else if(ascendingMatcher.matches()) {
            String orderingProperty = ascendingMatcher.group(1);
            if(POSSIBLE_ORDERING_URL_PARAMETERS.contains(orderingProperty)) {
                return field("cte1." + orderingProperty).asc();
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ordering parameters.");
    }


    private List<SortField<?>> getDefaultOrderingCriteria() {
        return Collections.singletonList(field("cte1.creation_timestamp").desc());
    }


    public List<SortField<?>> fromUrlParameters(List<String> urlParameters) {
        return urlParameters.isEmpty()
                ? getDefaultOrderingCriteria()
                : urlParameters.stream().map(this::parseSingleUrlParameter).collect(Collectors.toList());
    }
}
