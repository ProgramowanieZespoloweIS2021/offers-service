package com.pz.offersservice.offers.dto;

import com.pz.offersservice.tiers.entity.Tier;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


public class OfferPostDTO {

    @NotNull(message = "Owner ID is mandatory.")
    private final Long ownerId;

    @NotEmpty(message = "Offer title is mandatory and can not be empty.")
    private final String title;

    @NotEmpty(message = "Offer description is mandatory and can not be empty.")
    private final String description;

    @NotEmpty(message = "Offer must contain at least one tag.")
    private final List<String> tags;

    @NotEmpty(message = "Offer must contain at least one tier.")
    private final List<@Valid Tier> tiers;

    public OfferPostDTO(Long ownerId, String title, String description, List<String> tags, List<Tier> tiers) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.tiers = tiers;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Tier> getTiers() {
        return tiers;
    }
}
