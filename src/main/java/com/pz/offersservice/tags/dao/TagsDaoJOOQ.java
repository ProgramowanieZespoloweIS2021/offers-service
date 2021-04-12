package com.pz.offersservice.tags.dao;

import com.pz.offersservice.tags.entity.Tag;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class TagsDaoJOOQ implements TagsDao {

    private final DSLContext create;

    public TagsDaoJOOQ(DSLContext context) {
        this.create = context;
    }

    @Override
    public void associateTagsWithOffer(List<String> tags, Long offerId) {
        var insertQueries = tags.stream().map(tag ->
                create.insertInto(
                        table("offers_tags"),
                        field("offer_id"),
                        field("tag_name")
                ).values(offerId, tag)).collect(Collectors.toList());
        create.batch(insertQueries).execute();
    }

    @Override
    public List<Tag> getTags() {
        return create.select(
                field("name")
        )
                .from(table("tags"))
                .fetchInto(Tag.class);
    }

    @Override
    public List<Tag> getTagsForOffer(Long offerId) {
        return create.select(
                field("name")
        )
                .from(table("tags"))
                .join(table("offers_tags"))
                .on(field("tags.name").eq(field("offers_tags.tag_name")))
                .where(field("offers_tags.offer_id").eq(offerId))
                .fetchInto(Tag.class);
    }
}
