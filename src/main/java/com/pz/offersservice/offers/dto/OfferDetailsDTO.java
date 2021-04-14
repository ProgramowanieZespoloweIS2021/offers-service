package com.pz.offersservice.offers.dto;

import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.tags.entity.Tag;
import com.pz.offersservice.tiers.entity.Tier;

import java.time.LocalDateTime;
import java.util.List;


public class OfferDetailsDTO {

    private final Long id;
    private final Long ownerId;
    private final String title;
    private final String description;
    private final LocalDateTime creationTimestamp;
    private final List<Tier> tiers;
    private final List<Tag> tags;

    public OfferDetailsDTO(Offer offer, List<Tier> tiers, List<Tag> tags) {
        this.id = offer.getId();
        this.ownerId = offer.getOwnerId();
        this.title = offer.getTitle();
        this.description = offer.getDescription();
        this.creationTimestamp = offer.getCreationTimestamp();
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

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
