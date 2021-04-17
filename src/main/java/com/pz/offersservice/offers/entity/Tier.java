package com.pz.offersservice.offers.entity;

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

    @Min(value = 1, message = "Tier delivery time can not be shorter than one day.")
    @NotNull(message = "Tier delivery time is mandatory.")
    private final Long deliveryTime;

    public Tier(Long id, String title, String description, BigDecimal price, Long deliveryTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.deliveryTime = deliveryTime;
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

    public Long getDeliveryTime() {
        return deliveryTime;
    }
}
