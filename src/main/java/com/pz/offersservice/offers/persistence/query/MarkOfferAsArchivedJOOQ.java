package com.pz.offersservice.offers.persistence.query;

import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class MarkOfferAsArchivedJOOQ {

    private final DSLContext create;

    public MarkOfferAsArchivedJOOQ(DSLContext context) {
        this.create = context;
    }

    public void execute(Long offerId) {
        create.update(table("offers"))
                .set(field("is_archived"), true)
                .where(field("id").eq(offerId))
                .execute();
    }

}
