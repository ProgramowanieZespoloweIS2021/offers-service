package com.pz.offersservice.offers.domain;

import com.pz.offersservice.offers.domain.dto.OfferReportDTO;
import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Thumbnail;
import com.pz.offersservice.offers.domain.dto.OfferPostDTO;
import com.pz.offersservice.offers.domain.exception.InvalidOfferSpecificationException;
import com.pz.offersservice.offers.domain.filter.FilteringType;
import com.pz.offersservice.offers.domain.filter.ArchivedFilter;
import com.pz.offersservice.offers.domain.filter.FilteringCriteria;
import com.pz.offersservice.offers.domain.order.OrderingCriteria;
import com.pz.offersservice.offers.domain.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    public OfferReportDTO getOffers(Integer pageSize, Integer pageOffset, List<OrderingCriteria> orderingCriteria, List<FilteringCriteria> filteringCriteria) {
        filteringCriteria.add(new ArchivedFilter(FilteringType.EQUAL, false));
        OffersReportParameters offersReportParameters = new OffersReportParameters(pageSize, pageOffset, orderingCriteria, filteringCriteria);
        return offerRepository.get(offersReportParameters);
    }


    public Offer getOfferDetails(Long offerId) {
        return offerRepository
                .get(offerId)
                .orElseThrow(() -> new InvalidOfferSpecificationException("Offer with this ID does not exist."));
    }


    public void deleteOffer(Long offerId) {
        Offer offer = getOfferDetails(offerId);
        if(offer.getArchived()) {
            throw new InvalidOfferSpecificationException("This offer has already been archived.");
        }
        offerRepository.delete(offerId);
    }


    public Long addOffer(OfferPostDTO offerPostDto) {
        List<Tag> tags = offerPostDto.getTags().stream().map(Tag::new).collect(Collectors.toList());
        if(!validateTags(tags)) {
            throw new InvalidOfferSpecificationException("Non-existing tag specification.");
        }

        List<Thumbnail> thumbnails = offerPostDto.getThumbnails().stream().map(url -> new Thumbnail(null, url)).collect(Collectors.toList());
        LocalDateTime offerCreationTimestamp = LocalDateTime.now();
        Offer offer = new Offer(
                null,
                offerPostDto.getOwnerId(),
                offerPostDto.getTitle(),
                offerPostDto.getDescription(),
                offerCreationTimestamp,
                false,
                offerPostDto.getTiers(),
                tags,
                thumbnails);
        return offerRepository.add(offer);
    }


    private Boolean validateTags(List<Tag> providedTags) {
        List<Tag> availableTags = offerRepository.getTags();
        return providedTags.stream().allMatch(tag -> {
            String tagName = tag.getName();
            Optional<Tag> availableTagWithTheSameName = availableTags.stream().filter(availableTag -> availableTag.getName().equals(tagName)).findAny();
            return availableTagWithTheSameName.isPresent();
        });
    }


    public Long updateOffer(Long offerId, OfferPostDTO offerPostDto) {
        deleteOffer(offerId);
        return addOffer(offerPostDto);
    }


    public List<Tag> getTags() {
        return offerRepository.getTags();
    }

}
