package com.pz.offersservice.offers.dto;

import java.math.BigDecimal;


public class OfferBriefDTO {

    private final Long id;
    private final String name;
    private final BigDecimal minimalPrice;

    public OfferBriefDTO(Long id, String name, BigDecimal minimalPrice) {
        this.id = id;
        this.name = name;
        this.minimalPrice = minimalPrice;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }
}
