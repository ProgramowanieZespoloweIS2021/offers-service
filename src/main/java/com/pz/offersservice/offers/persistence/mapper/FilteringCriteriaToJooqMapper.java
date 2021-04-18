package com.pz.offersservice.offers.persistence.mapper;

import com.pz.offersservice.offers.domain.filter.*;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

@Component
public class FilteringCriteriaToJooqMapper {

    private final DSLContext create;


    private Condition convertMinimalPriceFilter(MinimalPriceFilter minimalPriceFilter) {
        FilteringType filteringType = minimalPriceFilter.getFilteringType();
        if(filteringType.equals(FilteringType.LESSER)) {
            return min(field("tiers.price")).lessThan(minimalPriceFilter.getMinimalPrice());
        }
        else if (filteringType.equals(FilteringType.GREATER)) {
            return min(field("tiers.price")).greaterThan(minimalPriceFilter.getMinimalPrice());
        }
        else {
            throw new RuntimeException(); // TODO: better exception
        }
    }


    private Condition convertOwnerIdFilter(OwnerIdFilter ownerIdFilter) {
        FilteringType filteringType = ownerIdFilter.getFilteringType();
        if(filteringType.equals(FilteringType.EQUAL)) {
            return field("owner_id").equal(ownerIdFilter.getOwnerId());
        }
        else {
            throw new RuntimeException(); // TODO: better exception
        }
    }


    private Condition convertTagsFilter(TagsFilter tagsFilter) {
        Field<Integer> matchingTagsCountingQuery = field(
                create
                        .selectCount()
                        .from(table("offers_tags"))
                        .where(field("tag_name").in(tagsFilter.getTags()).and(field("offer_id").eq(field("offers.id")))));
        return matchingTagsCountingQuery.greaterOrEqual(tagsFilter.getTags().size());
    }


    private Condition convertArchivedFilter(ArchivedFilter archivedFilter) {
        FilteringType filteringType = archivedFilter.getFilteringType();
        if(filteringType.equals(FilteringType.EQUAL)) {
            return field("offers.is_archived").equal(archivedFilter.getValue());
        }
        else {
            throw new RuntimeException(); // TODO: better exception
        }
    }


    private Condition convertSingleCriteria(FilteringCriteria filteringCriteria) {
        if(filteringCriteria instanceof MinimalPriceFilter) {
            MinimalPriceFilter filter = (MinimalPriceFilter) filteringCriteria;
            return convertMinimalPriceFilter(filter);
        }
        else if(filteringCriteria instanceof OwnerIdFilter) {
            OwnerIdFilter filter = (OwnerIdFilter) filteringCriteria;
            return convertOwnerIdFilter(filter);
        }
        else if(filteringCriteria instanceof TagsFilter) {
            TagsFilter filter = (TagsFilter) filteringCriteria;
            return convertTagsFilter(filter);
        }
        else if(filteringCriteria instanceof ArchivedFilter) {
            ArchivedFilter filter = (ArchivedFilter) filteringCriteria;
            return convertArchivedFilter(filter);
        }
        else {
            throw new RuntimeException();
        }
    }


    public FilteringCriteriaToJooqMapper(DSLContext context) {
        this.create = context;
    }


    public Condition convert(List<FilteringCriteria> filteringCriteria) {
        List<Condition> jooqFilteringConditions =
                filteringCriteria
                        .stream()
                        .map(this::convertSingleCriteria)
                        .collect(Collectors.toList());
        return jooqFilteringConditions
                .stream()
                .reduce(trueCondition(), Condition::and);
    }

}
