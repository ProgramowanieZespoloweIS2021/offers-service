package com.pz.offersservice.utils;

import com.pz.offersservice.offers.domain.entity.Tag;
import com.pz.offersservice.offers.domain.entity.Thumbnail;
import com.pz.offersservice.offers.domain.entity.Tier;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SampleOfferTestDataProvider {

    public static List<Tag> tagsFromNames(String... tagNames) {
        return Stream.of(tagNames).map(Tag::new).collect(Collectors.toList());
    }

    public static List<Tier> sampleValidTiers(Long firstTierId, Long secondTierId, Long thirdTierId) {
        return List.of(
                Tier.builder().id(firstTierId).title("Lowest").description("Lowest tier").price(new BigDecimal("50.00")).deliveryTime(3L).build(),
                Tier.builder().id(secondTierId).title("Mid").description("Mid tier").price(new BigDecimal("100.00")).deliveryTime(5L).build(),
                Tier.builder().id(thirdTierId).title("High").description("High tier").price(new BigDecimal("200.00")).deliveryTime(7L).build()
        );
    }

    public static List<String> sampleValidThumbnailUrls() {
        return List.of(
                "https://en.wikipedia.org/wiki/Google_logo#/media/File:First-google-logo.gif",
                "https://en.wikipedia.org/wiki/Google_logo#/media/File:Google_2015_logo.svg",
                "https://en.wikipedia.org/wiki/Google_logo#/media/File:Google.png"
        );
    }

    public static List<Thumbnail> sampleValidThumbnails() {
        List<String> thumbnailUrls = sampleValidThumbnailUrls();
        return IntStream
                .range(1, thumbnailUrls.size())
                .mapToObj(index -> new Thumbnail((long) index, thumbnailUrls.get(index)))
                .collect(Collectors.toList());
    }
}
