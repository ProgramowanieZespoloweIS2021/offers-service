package com.pz.offersservice.offers.service;

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


@Component
public class OrderingCriteriaFactory {

    private static final Pattern DESCENDING_PATTERN = Pattern.compile("^desc\\(([A-z]+)\\)$");
    private static final Pattern ASCENDING_PATTERN = Pattern.compile("^asc\\(([A-z]+)\\)$");
    private static final Set<String> POSSIBLE_ORDERING_URL_PARAMETERS =
            Stream.of("title", "creation_timestamp", "lowest_price").collect(Collectors.toCollection(HashSet::new));


    private OrderingCriteria createDescendingOrderingCriteria(String orderingProperty) {
        if(!POSSIBLE_ORDERING_URL_PARAMETERS.contains(orderingProperty)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ordering property.");
        }
        return new OrderingCriteria(orderingProperty, "descending");
    }


    private OrderingCriteria createAscendingOrderingCriteria(String orderingProperty) {
        if(!POSSIBLE_ORDERING_URL_PARAMETERS.contains(orderingProperty)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ordering property.");
        }
        return new OrderingCriteria(orderingProperty, "ascending");
    }


    private OrderingCriteria parseSingleOrderingCriteriaSpecification(String orderingCriteriaSpecification) {
        Matcher descendingMatcher = DESCENDING_PATTERN.matcher(orderingCriteriaSpecification);
        Matcher ascendingMatcher = ASCENDING_PATTERN.matcher(orderingCriteriaSpecification);
        if(descendingMatcher.matches()) {
            return createDescendingOrderingCriteria(descendingMatcher.group(1));
        }
        else if (ascendingMatcher.matches()) {
            return createAscendingOrderingCriteria(ascendingMatcher.group(1));
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ordering criteria specification.");
        }
    }


    private List<OrderingCriteria> getDefaultOrderingCriteria() {
        return Collections.singletonList(new OrderingCriteria("creation_timestamp", "descending"));
    }


    public List<OrderingCriteria> fromCriteriaSpecifications(List<String> orderingCriteriaSpecifications) {
        return orderingCriteriaSpecifications.isEmpty()
                ? getDefaultOrderingCriteria()
                : orderingCriteriaSpecifications.stream().map(this::parseSingleOrderingCriteriaSpecification).collect(Collectors.toList());
    }

}
