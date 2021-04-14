package com.pz.offersservice.offers.service;

import com.pz.offersservice.offers.dao.OffersDao;
import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferDetailsDTO;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.entity.OfferReportPage;
import com.pz.offersservice.offers.exception.OfferArchivedException;
import com.pz.offersservice.offers.filtering.FilteringType;
import com.pz.offersservice.offers.filtering.filter.ArchivedFilter;
import com.pz.offersservice.offers.filtering.filter.FilteringCriteria;
import com.pz.offersservice.offers.ordering.OrderingCriteria;
import com.pz.offersservice.tags.dao.TagsDao;
import com.pz.offersservice.tags.dao.TagsDaoJOOQ;
import com.pz.offersservice.tags.entity.Tag;
import com.pz.offersservice.tiers.dao.TiersDao;
import com.pz.offersservice.tiers.entity.Tier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public OfferReportPage getOffers(Integer pageSize, Integer pageOffset, List<OrderingCriteria> orderingCriteria, List<FilteringCriteria> filteringCriteria) {
        filteringCriteria.add(new ArchivedFilter(FilteringType.EQUAL, false));
        OffersReportParameters offersReportParameters = new OffersReportParameters(pageSize, pageOffset, orderingCriteria, filteringCriteria);
        return offersDaoJOOQ.getOffers(offersReportParameters);
    }


    public OfferDetailsDTO getOfferDetails(Long offerId) {
        Offer offer = offersDaoJOOQ.getOffer(offerId);
        List<Tier> offerTiers = tiersDaoJOOQ.getTiersForOffer(offerId);
        List<Tag> offerTags = tagsDaoJOOQ.getTagsForOffer(offerId);
        return new OfferDetailsDTO(offer, offerTiers, offerTags);
    }


    public void deleteOffer(Long offerId) {
        Offer offer = offersDaoJOOQ.getOffer(offerId);
        if(offer.getArchived()) {
            throw new OfferArchivedException("This offer has already been archived.");
        }
        offersDaoJOOQ.deleteOffer(offerId);
    }


    public Long addOffer(OfferPostDTO offerPostDto) {
        Long offerId = offersDaoJOOQ.createOffer(offerPostDto);
        tiersDaoJOOQ.createTiersForOffer(offerPostDto.getTiers(), offerId);
        tagsDaoJOOQ.associateTagsWithOffer(offerPostDto.getTags(), offerId);
        return offerId;
    }


    public Long updateOffer(Long offerId, OfferPostDTO offerPostDto) {
        Offer offer = offersDaoJOOQ.getOffer(offerId);
        if(offer.getArchived()) {
            throw new OfferArchivedException("This offer has been archived and can not be modified.");
        }
        deleteOffer(offerId);
        return addOffer(offerPostDto);
    }


    public List<Tag> getTags() {
        return tagsDaoJOOQ.getTags();
    }

}
