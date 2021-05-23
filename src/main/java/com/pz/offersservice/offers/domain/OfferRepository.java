package com.pz.offersservice.offers.domain;

import com.pz.offersservice.offers.domain.dto.OfferReport;
import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    Optional<Offer> get(Long offerId);

    OfferReport get(OffersReportParameters offersReportParameters);

    Long add(Offer offer);

    void delete(Long offerId);

    List<Tag> getTags();
}
