package com.pz.offersservice.offers.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Offer {

    private final Long id;
    private final Long ownerId;
    private final String title;
    private final String description;
    private final LocalDateTime creationTimestamp;
    private final Boolean isArchived;
    private final List<Tier> tiers;
    private final List<Tag> tags;
    private final List<Thumbnail> thumbnails;

    public Offer(Long id, Long ownerId, String title, String description, LocalDateTime creationTimestamp, Boolean isArchived, List<Tier> tiers, List<Tag> tags, List<Thumbnail> thumbnails) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.creationTimestamp = creationTimestamp;
        this.isArchived = isArchived;
        this.tiers = tiers;
        this.tags = tags;
        this.thumbnails = thumbnails;
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

    public Boolean getArchived() {
        return isArchived;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }
}
