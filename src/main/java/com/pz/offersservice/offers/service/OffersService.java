package com.pz.offersservice.offers.service;

import com.pz.offersservice.offers.dao.OffersDao;
import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferDetailsDTO;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.tags.dao.TagsDao;
import com.pz.offersservice.tags.dao.TagsDaoJOOQ;
import com.pz.offersservice.tags.entity.Tag;
import com.pz.offersservice.tiers.dao.TiersDao;
import com.pz.offersservice.tiers.entity.Tier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OffersService {

    private final OffersDao offersDaoJOOQ;
    private final TiersDao tiersDaoJOOQ;
    private final TagsDao tagsDaoJOOQ;

    public OffersService(OffersDao offersDaoJOOQ, TiersDao tiersDaoJOOQ, TagsDaoJOOQ tagsDaoJOOQ) {
        this.offersDaoJOOQ = offersDaoJOOQ;
        this.tiersDaoJOOQ = tiersDaoJOOQ;
        this.tagsDaoJOOQ = tagsDaoJOOQ;
    }

    public List<OfferBriefDTO> getOffers(int pageLimit, int pageOffset, List<String> orderingCriteria) {
        return offersDaoJOOQ.getOffers(pageLimit, pageOffset, orderingCriteria);
    }

    public OfferDetailsDTO getOfferDetails(Long offerId) {
        Offer offer = offersDaoJOOQ.getOffer(offerId);
        List<Tier> offerTiers = tiersDaoJOOQ.getTiersForOffer(offerId);
        List<Tag> offerTags = tagsDaoJOOQ.getTagsForOffer(offerId);
        return new OfferDetailsDTO(offer, offerTiers, offerTags);
    }

    public void deleteOffer(Long offerId) {
        offersDaoJOOQ.deleteOffer(offerId);
    }

    public Long addOffer(OfferPostDTO offerPostDto) {
        Long offerId = offersDaoJOOQ.createOffer(offerPostDto);
        tiersDaoJOOQ.createTiersForOffer(offerPostDto.getTiers(), offerId);
        tagsDaoJOOQ.associateTagsWithOffer(offerPostDto.getTagIds(), offerId);
        return offerId;
    }

    public Long updateOffer(Long offerId, OfferPostDTO offerPostDto) {
        deleteOffer(offerId);
        return addOffer(offerPostDto);
    }

    public List<Tag> getTags() {
        return tagsDaoJOOQ.getTags();
    }

}
