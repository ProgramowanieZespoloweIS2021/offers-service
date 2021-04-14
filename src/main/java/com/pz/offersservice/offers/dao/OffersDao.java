package com.pz.offersservice.offers.dao;

import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.entity.OfferReportPage;
import com.pz.offersservice.offers.service.OffersReportParameters;

import java.util.List;


public interface OffersDao {

    OfferReportPage getOffers(OffersReportParameters offersReportParameters);

    Offer getOffer(Long offerId);

    Long createOffer(OfferPostDTO offer);

    void deleteOffer(Long offerId);

}
