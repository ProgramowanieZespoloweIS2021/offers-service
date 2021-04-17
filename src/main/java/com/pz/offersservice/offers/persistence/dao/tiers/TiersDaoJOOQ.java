package com.pz.offersservice.offers.persistence.dao.tiers;

import com.pz.offersservice.offers.entity.Tier;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.DSL.field;

@Component
public class TiersDaoJOOQ implements TiersDao {

    private final DSLContext create;

    public TiersDaoJOOQ(DSLContext context) {
        this.create = context;
    }

    @Override
    public void createTiersForOffer(List<Tier> tiers, Long offerId) {
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



}
