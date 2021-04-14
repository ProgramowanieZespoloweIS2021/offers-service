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

    private final OrderingCriteriaToJooqConverter orderingCriteriaToJooqConverter;
    private final FilteringCriteriaToJooqConverter filteringCriteriaToJooqConverter;
    private final DSLContext create;


    private Condition createOffersFilteringClause(OffersReportParameters offersReportParameters) {
        return filteringCriteriaToJooqConverter.convertAllCriteria(offersReportParameters.getFilteringCriteria());
    }


    private CommonTableExpression<?> createCommonTableExpressionWithOffersData() {
        return name("offers_cte")
                .fields("id", "owner_id", "title", "lowest_price", "creation_timestamp", "is_archived")
                .as(select(
                        field("offers.id").cast(Long.class),
                        field("offers.owner_id").cast(Long.class),
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


    public OffersReportQueryFactory(OrderingCriteriaToJooqConverter orderingCriteriaToJooqConverter, FilteringCriteriaToJooqConverter filteringCriteriaToJooqConverter, DSLContext context) {
        this.orderingCriteriaToJooqConverter = orderingCriteriaToJooqConverter;
        this.filteringCriteriaToJooqConverter = filteringCriteriaToJooqConverter;
        this.create = context;
    }


    public SelectSeekStepN<?> create(OffersReportParameters offersReportParameters) {
        List<SortField<?>> sortFields = orderingCriteriaToJooqConverter.convert(offersReportParameters.getOrderingCriteria());
        Condition whereClause = createOffersFilteringClause(offersReportParameters);
        CommonTableExpression<?> offersCTE = createCommonTableExpressionWithOffersData();
        return create.with(offersCTE).select(
                    field("id"),
                    field("title"),
                    field("lowest_price")
                )
                .from(offersCTE)
                .where(whereClause)
                .orderBy(sortFields);
    }

}
