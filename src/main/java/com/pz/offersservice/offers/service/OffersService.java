package com.pz.offersservice.offers.service;

import com.pz.offersservice.offers.dto.OfferReportDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.entity.Thumbnail;
import com.pz.offersservice.offers.persistence.dao.offers.OffersDao;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.exception.InvalidOfferIdentifierException;
import com.pz.offersservice.offers.filter.FilteringType;
import com.pz.offersservice.offers.filter.filter.ArchivedFilter;
import com.pz.offersservice.offers.filter.filter.FilteringCriteria;
import com.pz.offersservice.offers.order.OrderingCriteria;
import com.pz.offersservice.offers.persistence.dao.tags.TagsDao;
import com.pz.offersservice.offers.persistence.dao.tags.TagsDaoJOOQ;
import com.pz.offersservice.offers.entity.Tag;
import com.pz.offersservice.offers.persistence.dao.thumbnails.ThumbnailsDao;
import com.pz.offersservice.offers.persistence.dao.thumbnails.ThumbnailsDaoJOOQ;
import com.pz.offersservice.offers.persistence.dao.tiers.TiersDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OffersService {

    private final OffersDao offersDaoJOOQ;
    private final TiersDao tiersDaoJOOQ;
    private final TagsDao tagsDaoJOOQ;
    private final ThumbnailsDao thumbnailsDaoJOOQ;


    public OffersService(OffersDao offersDaoJOOQ, TiersDao tiersDaoJOOQ, TagsDaoJOOQ tagsDaoJOOQ, ThumbnailsDaoJOOQ thumbnailsDaoJOOQ) {
        this.offersDaoJOOQ = offersDaoJOOQ;
        this.tiersDaoJOOQ = tiersDaoJOOQ;
        this.tagsDaoJOOQ = tagsDaoJOOQ;
        this.thumbnailsDaoJOOQ = thumbnailsDaoJOOQ;
    }


    public OfferReportDTO getOffers(Integer pageSize, Integer pageOffset, List<OrderingCriteria> orderingCriteria, List<FilteringCriteria> filteringCriteria) {
        filteringCriteria.add(new ArchivedFilter(FilteringType.EQUAL, false));
        OffersReportParameters offersReportParameters = new OffersReportParameters(pageSize, pageOffset, orderingCriteria, filteringCriteria);
        return offersDaoJOOQ.getOffers(offersReportParameters);
    }


    public Offer getOfferDetails(Long offerId) {
        return offersDaoJOOQ
                .getOffer(offerId)
                .orElseThrow(() -> new InvalidOfferIdentifierException("Offer with this ID does not exist."));
    }


    public void deleteOffer(Long offerId) {
        Offer offer = offersDaoJOOQ
                .getOffer(offerId)
                .orElseThrow(() -> new InvalidOfferIdentifierException("Offer with this ID does not exist."));
        if(offer.getArchived()) {
            throw new InvalidOfferIdentifierException("This offer has already been archived.");
        }
        offersDaoJOOQ.deleteOffer(offerId);
    }


    public Long addOffer(OfferPostDTO offerPostDto) {
        Offer offer = new Offer(null,
                offerPostDto.getOwnerId(),
                offerPostDto.getTitle(),
                offerPostDto.getDescription(),
                LocalDateTime.now(),
                false,
                offerPostDto.getTiers(),
                offerPostDto.getTags().stream().map(Tag::new).collect(Collectors.toList()), // TODO: replace string, maybe create builder for offer
                offerPostDto.getThumbnails().stream().map(url -> new Thumbnail(null, url)).collect(Collectors.toList())); // TODO: replace string, maybe create builder for offer
        Long offerId = offersDaoJOOQ.createOffer(offer);
        tiersDaoJOOQ.createTiersForOffer(offerPostDto.getTiers(), offerId);
        thumbnailsDaoJOOQ.createThumbnailsForOffer(offerPostDto.getThumbnails(), offerId);
        tagsDaoJOOQ.associateTagsWithOffer(offerPostDto.getTags(), offerId);
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
