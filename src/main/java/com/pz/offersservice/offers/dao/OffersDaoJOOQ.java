package com.pz.offersservice.offers.dao;

import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.service.OrderingCriteriaFactory;
import org.jooq.*;
import org.jooq.exception.NoDataFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.impl.DSL.*;


@Component
public class OffersDaoJOOQ implements OffersDao {

    private final DSLContext create;
    private final OrderingCriteriaFactory orderingCriteriaFactory;


    public OffersDaoJOOQ(DSLContext context, OrderingCriteriaFactory orderingCriteriaFactory) {
        this.create = context;
        this.orderingCriteriaFactory = orderingCriteriaFactory;
    }


    @Override
    public List<OfferBriefDTO> getOffers(Integer pageLimit, Integer pageOffset, List<String> orderingCriteria) {
        CommonTableExpression<Record5<Long, String, BigDecimal, LocalDateTime, Boolean>> cte1 =
                name("cte1").fields("id", "title", "lowest_price", "creation_timestamp", "is_archived").as(
                        select(
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

        List<SortField<?>> orderByClause = orderingCriteriaFactory.fromUrlParameters(orderingCriteria);

        Condition whereClause = trueCondition();
        whereClause = whereClause.and(field("cte1.is_archived").equal(false));

        return create.with(cte1).select(
                cte1.field("id"),
                cte1.field("title"),
                cte1.field("lowest_price")
        )
                .from(cte1)
                .where(whereClause)
                .orderBy(orderByClause)
                .limit(pageLimit)
                .offset(pageOffset)
                .fetchInto(OfferBriefDTO.class);
    }


    @Override
    public void deleteOffer(Long offerId) {
        create.update(table("offers"))
                .set(field("is_archived"), true)
                .where(field("id").eq(offerId))
                .execute();
    }


    @Override
    public Long createOffer(OfferPostDTO offerPostDto) {
        return create.insertInto(
                table("offers"),
                field("owner_id"),
                field("title"),
                field("description"),
                field("creation_timestamp"),
                field("is_archived")
        )
                .values(offerPostDto.getOwnerId(), offerPostDto.getTitle(), offerPostDto.getDescription(), LocalDateTime.now(), false)
                .returning(field("id"))
                .fetchOptional()
                .orElseThrow(() -> new NoDataFoundException("Could not create offer."))
                .into(Long.class);
    }


    @Override
    public Offer getOffer(Long offerId) {
        return create.select(
                field("id"),
                field("owner_id"),
                field("title"),
                field("description"),
                field("creation_timestamp").cast(LocalDateTime.class)
        )
                .from(table("offers"))
                .where(field("id").eq(offerId))
                .fetchAnyInto(Offer.class);
    }

}
