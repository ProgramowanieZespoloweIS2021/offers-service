package com.pz.offersservice.offers.entity;

import com.pz.offersservice.offers.dto.OfferBriefDTO;

import java.util.List;

public class OfferReportPage {

    private final Integer totalNumberOfOffers;
    private final List<OfferBriefDTO> offers;

    public OfferReportPage(Integer totalNumberOfOffers, List<OfferBriefDTO> offers) {
        this.totalNumberOfOffers = totalNumberOfOffers;
        this.offers = offers;
    }

    public Integer getTotalNumberOfOffers() {
        return totalNumberOfOffers;
    }

    public List<OfferBriefDTO> getOffers() {
        return offers;
    }
}
