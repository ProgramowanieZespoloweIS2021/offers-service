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

    private Offer(Long id, Long ownerId, String title, String description, LocalDateTime creationTimestamp, Boolean isArchived, List<Tier> tiers, List<Tag> tags, List<Thumbnail> thumbnails) {
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

    public static OfferBuilder builder() {
        return new OfferBuilder();
    }

    public static class OfferBuilder {

        private Long id;
        private Long ownerId;
        private String title;
        private String description;
        private LocalDateTime creationTimestamp;
        private Boolean isArchived;
        private List<Tier> tiers;
        private List<Tag> tags;
        private List<Thumbnail> thumbnails;

        public OfferBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OfferBuilder ownerId(Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public OfferBuilder title(String title) {
            this.title = title;
            return this;
        }

        public OfferBuilder description(String description) {
            this.description = description;
            return this;
        }

        public OfferBuilder creationTimestamp(LocalDateTime creationTimestamp) {
            this.creationTimestamp = creationTimestamp;
            return this;
        }

        public OfferBuilder isArchived(Boolean isArchived) {
            this.isArchived = isArchived;
            return this;
        }

        public OfferBuilder tiers(List<Tier> tiers) {
            this.tiers = tiers;
            return this;
        }

        public OfferBuilder tags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public OfferBuilder thumbnails(List<Thumbnail> thumbnails) {
            this.thumbnails = thumbnails;
            return this;
        }

        public Offer build() {
            return new Offer(id, ownerId, title, description, creationTimestamp, isArchived, tiers, tags, thumbnails);
        }

    }
}
