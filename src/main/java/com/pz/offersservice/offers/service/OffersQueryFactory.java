package com.pz.offersservice.offers.service;

import com.pz.offersservice.offers.model.Offer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// TODO: refactor according to: https://www.baeldung.com/spring-data-criteria-queries
@Component
public class OffersQueryFactory {

    private final EntityManager entityManager;


    public OffersQueryFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public TypedQuery<Offer> getOffersQuery(int pageLimit, int pageOffset, List<String> orderingCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Offer> criteriaQuery = criteriaBuilder.createQuery(Offer.class);
        Root<Offer> offer = criteriaQuery.from(Offer.class);

        List<Predicate> filteringPredicates = new ArrayList<>();
        filteringPredicates.add(criteriaBuilder.equal(offer.get("archived"), false));
        List<Order> orderingPredicates = parseOrderingPredicates(orderingCriteria, criteriaBuilder, offer);
        criteriaQuery.select(offer)
                .where(filteringPredicates.toArray(new Predicate[]{}))
                .orderBy(orderingPredicates);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageOffset)
                .setMaxResults(pageLimit);
    }


    private Order createOrderingPredicate(String orderingCriteria, CriteriaBuilder criteriaBuilder, Root<Offer> offerRoot) {
        final Pattern descendingPattern = Pattern.compile("^desc\\(([A-z]+)\\)$");
        final Pattern ascendingPattern = Pattern.compile("^asc\\(([A-z]+)\\)$");
        Matcher descendingMatcher = descendingPattern.matcher(orderingCriteria);
        Matcher ascendingMatcher = ascendingPattern.matcher(orderingCriteria);
        if(descendingMatcher.matches()) {
            return criteriaBuilder.desc(offerRoot.get(descendingMatcher.group(1)));
        }
        else if(ascendingMatcher.matches()) {
            return criteriaBuilder.asc(offerRoot.get(ascendingMatcher.group(1)));
        }
        else {
            throw new RuntimeException("Invalid ordering predicate");
        }
    }


    private List<Order> parseOrderingPredicates(List<String> orderingCriteria, CriteriaBuilder criteriaBuilder, Root<Offer> offerRoot) {
        if(orderingCriteria.isEmpty()) {
            return Collections.singletonList(createOrderingPredicate("desc(creationTimestamp)", criteriaBuilder, offerRoot));
        }
        else {
            return orderingCriteria.stream()
                    .map(criteria -> createOrderingPredicate(criteria, criteriaBuilder, offerRoot))
                    .collect(Collectors.toList());
        }
    }




}
