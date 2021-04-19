package com.pz.offersservice.offers.domain.entity;

public class Thumbnail {

    private final Long id;
    private final String url; // TODO: maybe different type

    public Thumbnail(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
