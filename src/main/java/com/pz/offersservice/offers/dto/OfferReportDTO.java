package com.pz.offersservice.offers.dto;

import java.util.List;

public class OfferReportDTO {

    private final Integer totalNumberOfOffers;
    private final List<OfferBriefDTO> offers;

    public OfferReportDTO(Integer totalNumberOfOffers, List<OfferBriefDTO> offers) {
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
