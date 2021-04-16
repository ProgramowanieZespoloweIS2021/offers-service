package com.pz.offersservice.offers.dto;

import com.pz.offersservice.offers.entity.Tag;
import com.pz.offersservice.offers.entity.Thumbnail;

import java.math.BigDecimal;
import java.util.List;


public class OfferBriefDTO {

    private final Long id;
    private final Long ownerId;
    private final String name;
    private final BigDecimal minimalPrice;
    private final List<Tag> tags;
    private final List<Thumbnail> thumbnails;

    public OfferBriefDTO(Long id, Long ownerId, String name, BigDecimal minimalPrice, List<Tag> tags, List<Thumbnail> thumbnails) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.minimalPrice = minimalPrice;
        this.tags = tags;
        this.thumbnails = thumbnails;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }
}
