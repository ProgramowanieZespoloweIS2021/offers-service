package com.pz.offersservice.offers.domain.dto;

import java.util.List;

public class OfferReport {

    private final Integer totalNumberOfOffers;
    private final List<OfferBrief> offers;

    public OfferReport(Integer totalNumberOfOffers, List<OfferBrief> offers) {
        this.totalNumberOfOffers = totalNumberOfOffers;
        this.offers = offers;
    }

    public Integer getTotalNumberOfOffers() {
        return totalNumberOfOffers;
    }

    public List<OfferBrief> getOffers() {
        return offers;
    }
}
