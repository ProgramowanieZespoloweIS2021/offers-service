package com.pz.offersservice.tags.dao;

import com.pz.offersservice.tags.entity.Tag;

import java.util.List;

public interface TagsDao {

    void associateTagsWithOffer(List<String> tags, Long offerId);

    List<Tag> getTags();

    List<Tag> getTagsForOffer(Long offerId);

}
