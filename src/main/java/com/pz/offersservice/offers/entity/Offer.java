package com.pz.offersservice.offers.entity;

import java.time.LocalDateTime;

public class Offer {

    private final Long id;
    private final Long ownerId;
    private final String title;
    private final String description;
    private final LocalDateTime creationTimestamp;

    public Offer(Long id, Long ownerId, String title, String description, LocalDateTime creationTimestamp) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.creationTimestamp = creationTimestamp;
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
}
