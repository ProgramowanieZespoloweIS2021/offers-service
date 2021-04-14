package com.pz.offersservice.offers.persistence.dao;

import com.pz.offersservice.offers.persistence.mapper.FilteringCriteriaToJooqMapper;
import com.pz.offersservice.offers.persistence.mapper.OrderingCriteriaToJooqMapper;
import com.pz.offersservice.offers.service.OffersReportParameters;
import org.jooq.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.impl.DSL.*;

@Component
public class OffersReportQueryFactory {

    private final OrderingCriteriaToJooqMapper orderingCriteriaToJooqMapper;
    private final FilteringCriteriaToJooqMapper filteringCriteriaToJooqMapper;
    private final DSLContext create;


    private Condition createOffersFilteringClause(OffersReportParameters offersReportParameters) {
        return filteringCriteriaToJooqMapper.convertAllCriteria(offersReportParameters.getFilteringCriteria());
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


    public OffersReportQueryFactory(OrderingCriteriaToJooqMapper orderingCriteriaToJooqMapper, FilteringCriteriaToJooqMapper filteringCriteriaToJooqMapper, DSLContext context) {
        this.orderingCriteriaToJooqMapper = orderingCriteriaToJooqMapper;
        this.filteringCriteriaToJooqMapper = filteringCriteriaToJooqMapper;
        this.create = context;
    }


    public SelectSeekStepN<?> create(OffersReportParameters offersReportParameters) {
        List<SortField<?>> sortFields = orderingCriteriaToJooqMapper.convert(offersReportParameters.getOrderingCriteria());
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
