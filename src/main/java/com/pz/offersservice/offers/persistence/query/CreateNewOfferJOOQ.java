package com.pz.offersservice.offers.persistence.query;

import com.pz.offersservice.offers.entity.Offer;
import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class CreateNewOfferJOOQ {

    private final DSLContext create;

    public CreateNewOfferJOOQ(DSLContext context) {
        this.create = context;
    }

    public Long execute(Offer offer) {
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
}
