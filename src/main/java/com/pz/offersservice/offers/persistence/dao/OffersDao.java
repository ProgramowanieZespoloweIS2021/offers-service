package com.pz.offersservice.offers.persistence.dao;

import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.dto.OfferReportDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.service.OffersReportParameters;

import java.util.Optional;


public interface OffersDao {

    OfferReportDTO getOffers(OffersReportParameters offersReportParameters);

    Optional<Offer> getOffer(Long offerId);

    Long createOffer(OfferPostDTO offer);

    void deleteOffer(Long offerId);

}
