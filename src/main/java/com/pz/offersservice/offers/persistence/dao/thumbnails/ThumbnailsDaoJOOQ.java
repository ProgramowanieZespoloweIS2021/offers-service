package com.pz.offersservice.offers.persistence.dao.thumbnails;

import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class ThumbnailsDaoJOOQ implements ThumbnailsDao {

    private final DSLContext create;

    public ThumbnailsDaoJOOQ(DSLContext context) {
        this.create = context;
    }

    @Override
    public void createThumbnailsForOffer(List<String> thumbnails, Long offerId) {
        var insertQueries = thumbnails.stream()
                .map(thumbnail -> create.insertInto(
                        table("thumbnails"),
                        field("offer_id"),
                        field("url"))
                        .values(offerId, thumbnail)
                ).collect(Collectors.toList());
        create.batch(insertQueries).execute();
    }
}
