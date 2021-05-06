package com.pz.offersservice.factory;

import com.pz.offersservice.offers.domain.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleTagsFactory {

    public static List<Tag> fromNames(String... names) {
        return Stream.of(names).map(Tag::new).collect(Collectors.toList());
    }
}
