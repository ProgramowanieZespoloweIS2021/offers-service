package com.pz.offersservice.offers.dao;

import com.pz.offersservice.offers.service.OffersReportParameters;
import org.jooq.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.impl.DSL.*;

@Component
public class OffersReportQueryFactory {

    private final OrderingCriteriaToSortFieldsConverter orderingCriteriaToSortFieldsConverter;
    private final DSLContext create;


    private Field<Integer> createMatchingTagsCountingQuery(List<String> tags) {
        return field(
                create
                .selectCount()
                .from(table("offers_tags"))
                .where(field("tag_name").in(tags).and(field("offer_id").eq(field("id")))));
    }


    private Condition createOffersFilteringClause(OffersReportParameters offersReportParameters) {
        List<String> tags = offersReportParameters.getTags();
        Field<Integer> matchingTagsCountingQuery = createMatchingTagsCountingQuery(tags);
        return trueCondition()
                .and(field("is_archived").equal(false))
                .and(matchingTagsCountingQuery.greaterOrEqual(tags.size()));
    }


    private CommonTableExpression<?> createCommonTableExpressionWithOffersData() {
        return name("offers_cte")
                .fields("id", "title", "lowest_price", "creation_timestamp", "is_archived")
                .as(select(
                        field("offers.id").cast(Long.class),
                        field("offers.title").cast(String.class),
                        min(field("tiers.price")).cast(BigDecimal.class),
                        field("offers.creation_timestamp").cast(LocalDateTime.class),
                        field("offers.is_archived").cast(Boolean.class)
                )
                        .from(table("offers"))
                        .leftJoin(table("tiers"))
                        .on(field("offers.id").eq(field("tiers.offer_id")))
                        .groupBy(field("offers.id"))
        );
    }


    public OffersReportQueryFactory(OrderingCriteriaToSortFieldsConverter orderingCriteriaToSortFieldsConverter, DSLContext context) {
        this.orderingCriteriaToSortFieldsConverter = orderingCriteriaToSortFieldsConverter;
        this.create = context;
    }


    public SelectForUpdateStep<?> create(OffersReportParameters offersReportParameters) {
        List<SortField<?>> sortFields = orderingCriteriaToSortFieldsConverter.convert(offersReportParameters.getOrderingCriteria());
        Condition whereClause = createOffersFilteringClause(offersReportParameters);
        CommonTableExpression<?> offersCTE = createCommonTableExpressionWithOffersData();
        return create.with(offersCTE).select(
                    field("id"),
                    field("title"),
                    field("lowest_price")
                )
                .from(offersCTE)
                .where(whereClause)
                .orderBy(sortFields)
                .limit(offersReportParameters.getPageSize())
                .offset(offersReportParameters.getPageOffset());
    }

}
