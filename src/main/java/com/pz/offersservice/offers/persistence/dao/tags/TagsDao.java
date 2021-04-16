package com.pz.offersservice.offers.persistence.dao.tags;

import com.pz.offersservice.offers.entity.Tag;

import java.util.List;

public interface TagsDao {

    void associateTagsWithOffer(List<String> tags, Long offerId);

    List<Tag> getTags();

}
