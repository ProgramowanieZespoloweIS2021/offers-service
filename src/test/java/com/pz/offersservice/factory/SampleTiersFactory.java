package com.pz.offersservice.factory;

import com.pz.offersservice.offers.domain.entity.Tier;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleTiersFactory {

    public static Tier fromId(Long id) {
        return new Tier(id, "Sample tier", "Sample tier description", new BigDecimal("2.50"), 2L);
    }

    public static List<Tier> fromIds(Long... ids) {
        return Stream.of(ids).map(SampleTiersFactory::fromId).collect(Collectors.toList());
    }
}
