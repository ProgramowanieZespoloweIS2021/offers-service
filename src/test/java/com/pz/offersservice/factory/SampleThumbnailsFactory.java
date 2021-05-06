package com.pz.offersservice.factory;

import com.pz.offersservice.offers.domain.entity.Thumbnail;

import java.util.Arrays;
import java.util.List;

public class SampleThumbnailsFactory {

    public static List<Thumbnail> createListOfValidThumbnails() {
        return Arrays.asList(
                new Thumbnail(1L, "https://upload.wikimedia.org/wikipedia/commons/5/5f/Java_short_snippet_code_big_PL.png"),
                new Thumbnail(2L, "https://upload.wikimedia.org/wikipedia/commons/2/2f/Google_2015_logo.svg")
        );
    }
}
