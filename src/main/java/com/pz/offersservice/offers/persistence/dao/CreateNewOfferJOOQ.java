package com.pz.offersservice.offers.persistence.dao;

import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Tag;
import com.pz.offersservice.offers.domain.entity.Thumbnail;
import com.pz.offersservice.offers.domain.entity.Tier;
import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class CreateNewOfferJOOQ {

    private final DSLContext create;

    public CreateNewOfferJOOQ(DSLContext context) {
        this.create = context;
    }

    public Long execute(Offer offer) {
        Long offerId = insertOfferIntoTheDatabase(offer);
        insertTiersIntoTheDatabase(offerId, offer.getTiers());
        insertThumbnailsIntoTheDatabase(offerId, offer.getThumbnails());
        associateTagsWithOffer(offerId, offer.getTags());
        return offerId;
    }

    private Long insertOfferIntoTheDatabase(Offer offer) {
        return create.insertInto(
                table("offers"),
                field("owner_id"),
                field("title"),
                field("description"),
                field("creation_timestamp"),
                field("is_archived")
        )
                .values(offer.getOwnerId(), offer.getTitle(), offer.getDescription(), offer.getCreationTimestamp(), offer.getArchived())
                .returning(field("id"))
                .fetchOptional()
                .orElseThrow(() -> new NoDataFoundException("Could not create offer.")) // TODO: maybe other exception
                .into(Long.class);
    }

    private void insertTiersIntoTheDatabase(Long offerId, List<Tier> tiers) {
        var insertQueries = tiers.stream()
                .map(tier -> create.insertInto(
                        table("tiers"),
                        field("offer_id"),
                        field("title"),
                        field("description"),
                        field("price"),
                        field("delivery_time"))
                        .values(offerId, tier.getTitle(), tier.getDescription(), tier.getPrice(), tier.getDeliveryTime())
                ).collect(Collectors.toList());
        create.batch(insertQueries).execute();
    }

    private void insertThumbnailsIntoTheDatabase(Long offerId, List<Thumbnail> thumbnails) {
        var insertQueries = thumbnails.stream()
                .map(thumbnail -> create.insertInto(
                        table("thumbnails"),
                        field("offer_id"),
                        field("url"))
                        .values(offerId, thumbnail)
                ).collect(Collectors.toList());
        create.batch(insertQueries).execute();
    }

    private void associateTagsWithOffer(Long offerId, List<Tag> tags) {
        var insertQueries = tags.stream().map(tag ->
                create.insertInto(
                        table("offers_tags"),
                        field("offer_id"),
                        field("tag_name")
                ).values(offerId, tag.getName())).collect(Collectors.toList());
        create.batch(insertQueries).execute();
    }
}
