package com.pz.offersservice.offers.domain.dto;

import com.pz.offersservice.offers.domain.entity.Tag;
import com.pz.offersservice.offers.domain.entity.Thumbnail;

import java.math.BigDecimal;
import java.util.List;


public class OfferBrief {

    private final Long id;
    private final Long ownerId;
    private final String name;
    private final BigDecimal minimalPrice;
    private final List<Tag> tags;
    private final List<Thumbnail> thumbnails;

    private OfferBrief(Long id, Long ownerId, String name, BigDecimal minimalPrice, List<Tag> tags, List<Thumbnail> thumbnails) {
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

    public static OfferBriefBuilder builder() {
        return new OfferBriefBuilder();
    }

    public static class OfferBriefBuilder {

        private Long id;
        private Long ownerId;
        private String name;
        private BigDecimal minimalPrice;
        private List<Tag> tags;
        private List<Thumbnail> thumbnails;

        public OfferBriefBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OfferBriefBuilder ownerId(Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public OfferBriefBuilder name(String name) {
            this.name = name;
            return this;
        }

        public OfferBriefBuilder minimalPrice(BigDecimal minimalPrice) {
            this.minimalPrice = minimalPrice;
            return this;
        }

        public OfferBriefBuilder tags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public OfferBriefBuilder thumbnails(List<Thumbnail> thumbnails) {
            this.thumbnails = thumbnails;
            return this;
        }

        public OfferBrief build() {
            return new OfferBrief(id, ownerId, name, minimalPrice, tags, thumbnails);
        }
    }
}
