package com.pz.offersservice.factory;

import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Tag;
import com.pz.offersservice.offers.domain.entity.Thumbnail;
import com.pz.offersservice.offers.domain.entity.Tier;

import java.time.LocalDateTime;
import java.util.List;

public class SampleOffersFactory {


    public static Offer createArchivedOffer(Long id, List<Tier> tiers, List<Tag> tags, List<Thumbnail> thumbnails) {
        return new Offer(
                id,
                1L,
                "Sample offer",
                "Sample offer description",
                LocalDateTime.now(),
                true,
                tiers,
                tags,
                thumbnails);
    }


    public static  Offer createNonArchivedOffer(Long id, List<Tier> tiers, List<Tag> tags, List<Thumbnail> thumbnails) {
        return new Offer(
                id,
                1L,
                "Sample offer",
                "Sample offer description",
                LocalDateTime.now(),
                false,
                tiers,
                tags,
                thumbnails);
    }
}
