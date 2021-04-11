package com.pz.offersservice.offers.dao;

import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferDetailsDTO;
import com.pz.offersservice.offers.dto.OfferPostDto;

import java.util.List;


public interface OffersDao {

    List<OfferBriefDTO> getOffers(Integer pageLimit, Integer pageOffset, List<String> orderingCriteria);

    OfferDetailsDTO getOfferDetails(Long offerId);

    void createOffer(OfferPostDto offer);

    void deleteOffer(Long offerId);

}
