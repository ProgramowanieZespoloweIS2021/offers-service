package com.pz.offersservice.offers.dto;

public class Tag {

    private final Long id;
    private final String value;

    public Tag(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
