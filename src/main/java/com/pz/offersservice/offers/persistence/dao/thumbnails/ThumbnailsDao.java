package com.pz.offersservice.offers.persistence.dao.thumbnails;

import com.pz.offersservice.offers.entity.Thumbnail;

import java.util.List;

public interface ThumbnailsDao {

    void createThumbnailsForOffer(List<String> thumbnails, Long offerId);
}
