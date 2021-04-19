package com.pz.offersservice.offers.persistence.dao;

import com.pz.offersservice.offers.domain.entity.Tag;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class GetAllTagsJOOQ {

    private final DSLContext create;

    public GetAllTagsJOOQ(DSLContext context) {
        this.create = context;
    }

    public List<Tag> execute() {
        return create.select(field("name"))
                .from(table("tags"))
                .fetchInto(Tag.class);
    }
}
