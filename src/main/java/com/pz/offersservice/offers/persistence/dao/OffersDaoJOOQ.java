package com.pz.offersservice.offers.persistence.dao;

import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.dto.OfferReportDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.service.OffersReportParameters;
import org.jooq.*;
import org.jooq.exception.NoDataFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;


@Component
public class OffersDaoJOOQ implements OffersDao {

    private final DSLContext create;
    private final OffersReportQueryFactory offersReportQueryFactory;


    public OffersDaoJOOQ(DSLContext context, OffersReportQueryFactory offersReportQueryFactory) {
        this.create = context;
        this.offersReportQueryFactory = offersReportQueryFactory;
    }


    @Override
    public OfferReportDTO getOffers(OffersReportParameters offersReportParameters) {
        SelectSeekStepN<?> query = offersReportQueryFactory.create(offersReportParameters);
        Integer totalCount = create.fetchCount(query);
        List<OfferBriefDTO> offers = query
                .limit(offersReportParameters.getPageSize())
                .offset(offersReportParameters.getPageOffset())
                .fetchInto(OfferBriefDTO.class);
        return new OfferReportDTO(totalCount, offers);
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
    public Optional<Offer> getOffer(Long offerId) {
        return Optional.ofNullable(
                create.select(
                        field("id"),
                        field("owner_id"),
                        field("title"),
                        field("description"),
                        field("creation_timestamp").cast(LocalDateTime.class),
                        field("is_archived")
                )
                        .from(table("offers"))
                        .where(field("id").eq(offerId))
                        .fetchAnyInto(Offer.class));
    }

}
