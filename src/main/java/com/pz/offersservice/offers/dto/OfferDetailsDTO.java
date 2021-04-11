package com.pz.offersservice.offers.dto;

import java.util.List;

public class OfferDetailsDTO {

    private final Long id;
    private final Long ownerId;
    private final String title;
    private final String description;
    private final List<Tier> tiers;
    private final List<Tag> tags;

    public OfferDetailsDTO(Long id, Long ownerId, String title, String description, List<Tier> tiers, List<Tag> tags) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.tiers = tiers;
        this.tags = tags;
    }

    public Long getId() {
        return id;
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

    public List<Tier> getTiers() {
        return tiers;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
