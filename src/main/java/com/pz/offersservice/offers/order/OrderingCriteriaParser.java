package com.pz.offersservice.offers.order;

import com.pz.offersservice.offers.exception.InvalidOrderingCriteriaException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class OrderingCriteriaParser {

    private static final Pattern ORDERING_PATTERN = Pattern.compile("^(asc|desc):([A-z]+)$");
    private static final Set<String> POSSIBLE_ORDERING_URL_PARAMETERS =
            Stream.of("title", "creation_timestamp", "lowest_price").collect(Collectors.toCollection(HashSet::new));


    private OrderingCriteria parseSingleOrderingCriteriaSpecification(String orderingCriteriaSpecification) {
        Matcher orderingMatcher = ORDERING_PATTERN.matcher(orderingCriteriaSpecification);
        if(orderingMatcher.matches()) {
            OrderingType orderingType = orderingMatcher.group(1).equals("asc") ? OrderingType.ASCENDING : OrderingType.DESCENDING;
            String orderingProperty = orderingMatcher.group(2);
            if(POSSIBLE_ORDERING_URL_PARAMETERS.contains(orderingProperty)) {
                return new OrderingCriteria(orderingProperty, orderingType);
            }
            else {
                throw new InvalidOrderingCriteriaException("Invalid ordering property.");
            }
        }
        else {
            throw new InvalidOrderingCriteriaException("Invalid ordering criteria specification.");
        }
    }


    private List<OrderingCriteria> getDefaultOrderingCriteria() {
        return Collections.singletonList(new OrderingCriteria("creation_timestamp", OrderingType.DESCENDING));
    }


    public List<OrderingCriteria> parse(List<String> orderingCriteriaSpecifications) {
        return orderingCriteriaSpecifications.isEmpty()
                ? getDefaultOrderingCriteria()
                : orderingCriteriaSpecifications.stream().map(this::parseSingleOrderingCriteriaSpecification).collect(Collectors.toList());
    }

}
