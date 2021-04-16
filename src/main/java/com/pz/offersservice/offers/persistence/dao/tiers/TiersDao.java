package com.pz.offersservice.offers.persistence.dao.tiers;

import com.pz.offersservice.offers.entity.Tier;

import java.util.List;

public interface TiersDao {

    void createTiersForOffer(List<Tier> tiers, Long offerId);

}
