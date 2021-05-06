package com.pz.offersservice.offers.adapters.persistence.dao;

import com.pz.offersservice.offers.domain.entity.Offer;
import org.jooq.DSLContext;
import org.jooq.ResultQuery;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class GetSingleOfferJOOQ {

    private final DSLContext create;
    private final JdbcMapper<Offer> jdbcMapper;

    public GetSingleOfferJOOQ(DSLContext context) {
        this.create = context;
        this.jdbcMapper = JdbcMapperFactory
                .newInstance()
                .addKeys("id", "tiers_id", "tags_name", "thumbnails_id")
                .newMapper(Offer.class);
    }

    public Optional<Offer> execute(Long offerId) {
        ResultQuery<?> query = createQuery(offerId);
        return mapQueryResult(query);
    }

    private ResultQuery<?> createQuery(Long offerId) {
        return create.select(
                field("offers.id").as("id"),
                field("offers.owner_id").as("owner_id"),
                field("offers.title").as("title"),
                field("offers.description").as("description"),
                field("offers.creation_timestamp").as("creation_timestamp").cast(LocalDateTime.class),
                field("offers.is_archived").as("is_archived"),
                field("tiers.id").as("tiers_id"),
                field("tiers.title").as("tiers_title"),
                field("tiers.description").as("tiers_description"),
                field("tiers.price").as("tiers_price"),
                field("tiers.delivery_time").as("tiers_delivery_time"),
                field("offers_tags.tag_name").as("tags_name"),
                field("thumbnails.id").as("thumbnails_id"),
                field("thumbnails.url").as("thumbnails_url")
        )
                .from(table("offers"))
                .leftJoin(table("tiers")).on(field("tiers.offer_id").eq(field("offers.id")))
                .leftJoin(table("offers_tags")).on(field("offers_tags.offer_id").eq(field("offers.id")))
                .leftJoin(table("thumbnails")).on(field("thumbnails.offer_id").eq(field("offers.id")))
                .where(field("offers.id").eq(offerId));
    }

    // TODO: https://www.petrikainulainen.net/programming/jooq/jooq-tips-implementing-a-read-only-one-to-many-relationship/
    private Optional<Offer> mapQueryResult(ResultQuery<?> query) {
        try {
            ResultSet rs = query.fetchResultSet();
            Iterator<Offer> offersIterator = jdbcMapper.iterator(rs);
            if(!offersIterator.hasNext()) {
                return Optional.empty();
            }
            return Optional.of(offersIterator.next());
        }
        catch(SQLException ex) {
            return Optional.empty();
        }
    }

}
