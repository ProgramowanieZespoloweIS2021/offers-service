package com.pz.offersservice.offers.dto;

import java.util.List;

public class OfferPostDto {

    private final Long ownerId;
    private final String title;
    private final String description;
    private final List<Long> tagIds;
    private final List<Tier> tiers;

    public OfferPostDto(Long ownerId, String title, String description, List<Long> tagIds, List<Tier> tiers) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.tagIds = tagIds;
        this.tiers = tiers;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public List<Tier> getTiers() {
        return tiers;
    }
}
