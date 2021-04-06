package com.pz.offersservice.offers.converter;

import com.pz.offersservice.offers.dto.OfferPostDto;
import com.pz.offersservice.offers.model.Offer;
import com.pz.offersservice.tags.service.TagsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OfferPostDtoToEntityConverter {

    private final TagsService tagsService;


    public OfferPostDtoToEntityConverter(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    public Offer convert(OfferPostDto offerPostDto) {
        Offer offer = new Offer();
        offer.setOwnerId(offerPostDto.getOwnerId());
        offer.setDescription(offerPostDto.getDescription());
        offer.setTitle(offerPostDto.getTitle());
        offer.setCreationTimestamp(LocalDateTime.now());
        offer.setArchived(false);
        offer.setTags(tagsService.getTagsByIds(offerPostDto.getTagIds()));
        return offer;
    }

}
