package com.pz.offersservice.offers.service;

import com.pz.offersservice.offers.dao.OffersDao;
import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferDetailsDTO;
import com.pz.offersservice.offers.dto.OfferPostDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OffersService {

    private final OffersDao offersDaoJOOQ;

    public OffersService(OffersDao offersDaoJOOQ) {
        this.offersDaoJOOQ = offersDaoJOOQ;
    }

    public List<OfferBriefDTO> getOffers(int pageLimit, int pageOffset, List<String> orderingCriteria, List<String> filteringCriteria) {
        return offersDaoJOOQ.getOffers(pageLimit, pageOffset, orderingCriteria);
    }

    public OfferDetailsDTO getOfferDetails(Long offerId) {
        OfferDetailsDTO offerDetailsDTO = offersDaoJOOQ.getOfferDetails(offerId);
        System.out.println(offerDetailsDTO.getTags());
        return offerDetailsDTO;
    }

    public void deleteOffer(Long offerId) {
        offersDaoJOOQ.deleteOffer(offerId);
    }

    public void addOffer(OfferPostDto offerPostDto) {
        offersDaoJOOQ.createOffer(offerPostDto);
    }

}
