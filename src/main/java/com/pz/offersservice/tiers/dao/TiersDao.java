package com.pz.offersservice.tiers.dao;

import com.pz.offersservice.tiers.entity.Tier;

import java.util.List;

public interface TiersDao {

    void createTiersForOffer(List<Tier> tiers, Long offerId);

    List<Tier> getTiersForOffer(Long offerId);

}
