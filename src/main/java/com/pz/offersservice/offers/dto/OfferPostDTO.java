package com.pz.offersservice.offers.dto;

import com.pz.offersservice.tiers.entity.Tier;

import java.util.List;

public class OfferPostDTO {
    
    private final Long ownerId;
    private final String title;
    private final String description;
    private final List<String> tags;
    private final List<Tier> tiers;

    public OfferPostDTO(Long ownerId, String title, String description, List<String> tags, List<Tier> tiers) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public List<Tier> getTiers() {
        return tiers;
    }
}
