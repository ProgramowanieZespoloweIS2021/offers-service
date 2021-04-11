package com.pz.offersservice.offers.dao;

import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.entity.Offer;

import java.util.List;


public interface OffersDao {

    List<OfferBriefDTO> getOffers(Integer pageLimit, Integer pageOffset, List<String> orderingCriteria);

    Offer getOffer(Long offerId);

    Long createOffer(OfferPostDTO offer);

    void deleteOffer(Long offerId);

}
