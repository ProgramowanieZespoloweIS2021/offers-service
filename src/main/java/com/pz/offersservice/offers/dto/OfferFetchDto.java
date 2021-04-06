package com.pz.offersservice.offers.dto;

import com.pz.offersservice.offers.model.Offer;

import java.math.BigDecimal;

public class OfferFetchDto {

    private final Long id;
    private final String title;
    private final BigDecimal minimalPrice;

    private OfferFetchDto(Long id, String title, BigDecimal minimalPrice) {
        this.id = id;
        this.title = title;
        this.minimalPrice = minimalPrice;
    }


    public static OfferFetchDto fromOfferEntity(Offer offer) {
        return new OfferFetchDto(offer.getId(), offer.getTitle(), new BigDecimal("4.4"));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }
}
