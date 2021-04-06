package com.pz.offersservice.offers.controller;

import com.pz.offersservice.offers.dto.OfferFetchDto;
import com.pz.offersservice.offers.dto.OfferPostDto;
import com.pz.offersservice.offers.model.Offer;
import com.pz.offersservice.offers.service.OffersService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/offers")
public class OffersController {

    private final OffersService offersService;


    public OffersController(OffersService offersService) {
        this.offersService = offersService;
    }


    @GetMapping
    public Collection<OfferFetchDto> getOffers(@RequestParam(defaultValue = "20") Integer pageLimit,
                                               @RequestParam(defaultValue = "0") Integer pageOffset,
                                               @RequestParam(defaultValue = "desc(creationTimestamp)") List<String> orderingCriteria) {
        return offersService.getOffers(pageLimit, pageOffset, orderingCriteria);
    }


    @GetMapping("/{id}")
    public Offer getOfferDetails(@PathVariable("id") Long offerId) {
        return offersService.getOfferDetails(offerId);
    }


    @PostMapping
    public String addOffer(@RequestBody OfferPostDto offerPostDto) {
        offersService.addOffer(offerPostDto);
        return "OK";
    }


    @PostMapping("/{id}")
    public String updateOffer(@PathVariable("id") Long offerId, @RequestBody OfferPostDto offerPostDto) {
        offersService.updateOffer(offerId, offerPostDto);
        return "OK";
    }


    @DeleteMapping("/{id}")
    public String deleteOffer(@PathVariable("id") Long offerId) {
        offersService.deleteOffer(offerId);
        return "OK";
    }

}
