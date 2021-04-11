package com.pz.offersservice.offers.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TagsFromFilteringCriteriaExtractor {

    private final Pattern TAGS_FILTERING_PATTERN = Pattern.compile("^tags:([0-9]+)$");

    private Optional<Long> extractTagIdFromSingleCriteria(String tagSpecification) {
        Matcher tagMatcher = TAGS_FILTERING_PATTERN.matcher(tagSpecification);
        if(tagMatcher.matches()) {
            return Optional.of(Long.parseLong(tagMatcher.group(1)));
        }
        return Optional.empty();
    }


    public List<Long> extract(List<String> filteringCriteria) {
        return filteringCriteria.stream()
                .map(this::extractTagIdFromSingleCriteria)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

}
