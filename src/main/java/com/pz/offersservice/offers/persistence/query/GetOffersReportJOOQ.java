package com.pz.offersservice.offers.persistence.query;

import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferReportDTO;
import com.pz.offersservice.offers.persistence.mapper.FilteringCriteriaToJooqMapper;
import com.pz.offersservice.offers.persistence.mapper.OrderingCriteriaToJooqMapper;
import com.pz.offersservice.offers.service.OffersReportParameters;
import org.jooq.*;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

@Component
public class GetOffersReportJOOQ {

    private final OrderingCriteriaToJooqMapper orderingCriteriaToJooqMapper;
    private final FilteringCriteriaToJooqMapper filteringCriteriaToJooqMapper;
    private final DSLContext create;
    private final JdbcMapper<OfferBriefDTO> jdbcMapper;


    public GetOffersReportJOOQ(OrderingCriteriaToJooqMapper orderingCriteriaToJooqMapper,
                               FilteringCriteriaToJooqMapper filteringCriteriaToJooqMapper,
                               DSLContext context) {
        this.orderingCriteriaToJooqMapper = orderingCriteriaToJooqMapper;
        this.filteringCriteriaToJooqMapper = filteringCriteriaToJooqMapper;
        this.create = context;
        this.jdbcMapper = JdbcMapperFactory
                .newInstance()
                .unorderedJoin()
                .addKeys("id", "tags_name", "thumbnails_id")
                .newMapper(OfferBriefDTO.class);
    }


    public OfferReportDTO execute(OffersReportParameters offersReportParameters) {
        List<SortField<?>> sortFields = orderingCriteriaToJooqMapper.convert(offersReportParameters.getOrderingCriteria());
        Condition whereClause = filteringCriteriaToJooqMapper.convert(offersReportParameters.getFilteringCriteria());
        return createOffersReport(
                offersReportParameters.getPageSize(),
                offersReportParameters.getPageOffset(),
                sortFields,
                whereClause);
    }


    private OfferReportDTO createOffersReport(Integer limit, Integer offset, List<SortField<?>> sortClause, Condition whereClause) {
        SelectSeekStepN<?> filteredAndSortedSubQuery = createSubQuery(sortClause, whereClause);
        Integer totalCount = create.fetchCount(filteredAndSortedSubQuery);
        Table<?> test2 = filteredAndSortedSubQuery.limit(limit).offset(offset).asTable("sub_query");
        ResultQuery<?> query = createJoinQuery(sortClause, test2);
        List<OfferBriefDTO> offers = mapQueryIntoList(query);
        return new OfferReportDTO(totalCount, offers);
    }


    private SelectSeekStepN<?> createSubQuery(List<SortField<?>> sortClause, Condition whereClause) {
        return create.select(
                field("offers.id").as("id"),
                field("offers.owner_id").as("owner_id"),
                field("offers.title").as("title"),
                field("offers.creation_timestamp").as("creation_timestamp").cast(LocalDateTime.class),
                field("offers.is_archived").as("is_archived"),
                min(field("tiers.price")).as("lowest_price")
        )
                .from(table("offers"))
                .leftJoin(table("tiers")).on(field("offers.id").eq(field("tiers.offer_id")))
                .groupBy(field("offers.id"))
                .having(whereClause)
                .orderBy(sortClause);
    }


    private ResultQuery<?> createJoinQuery(List<SortField<?>> sortClause, Table<?> offersTable) {
        return create.select(
                field("sub_query.id").as("id"),
                field("sub_query.owner_id").as("owner_id"),
                field("sub_query.title").as("name"), // TODO: rename to title
                field("sub_query.lowest_price").as("minimal_price"), // TODO: rename
                field("offers_tags.tag_name").as("tags_name"),
                field("thumbnails.id").as("thumbnails_id"),
                field("thumbnails.url").as("thumbnails_url")
        )
                .from(offersTable)
                .leftJoin(table("offers_tags")).on(field("offers_tags.offer_id").eq(field("sub_query.id")))
                .leftJoin(table("thumbnails")).on(field("thumbnails.offer_id").eq(field("sub_query.id")))
                .orderBy(sortClause);
    }


    private List<OfferBriefDTO> mapQueryIntoList(ResultQuery<?> query) {
        try {
            ResultSet rs = query.fetchResultSet();
            return jdbcMapper.stream(rs).collect(Collectors.toList());
        }
        catch(SQLException ex) {
            return Collections.emptyList(); // TODO: replace
        }
    }

}
