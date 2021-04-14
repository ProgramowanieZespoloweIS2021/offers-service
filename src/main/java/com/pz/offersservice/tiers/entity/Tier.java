package com.pz.offersservice.tiers.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Tier {

    private final Long id;

    @NotEmpty(message = "Tier title is mandatory and should not be empty.")
    private final String title;

    @NotEmpty(message = "Tier description is mandatory and should not be empty.")
    private final String description;

    @Min(value = 0, message = "Tier price can not be negative.")
    @NotNull(message = "Tier price is mandatory.")
    private final BigDecimal price;

    public Tier(Long id, String title, String description, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
