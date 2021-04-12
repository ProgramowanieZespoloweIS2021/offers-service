package com.pz.offersservice.offers.controller;

import com.pz.offersservice.offers.dto.OfferBriefDTO;
import com.pz.offersservice.offers.dto.OfferDetailsDTO;
import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.service.OffersService;
import com.pz.offersservice.tags.entity.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OffersController {

    private final OffersService offersService;


    public OffersController(OffersService offersService) {
        this.offersService = offersService;
    }


    @GetMapping
    public List<OfferBriefDTO> getOffers(@RequestParam(defaultValue = "20") Integer pageLimit,
                                         @RequestParam(defaultValue = "0") Integer pageOffset,
                                         @RequestParam(defaultValue = "desc(creation_timestamp)") List<String> orderingCriteria,
                                         @RequestParam(defaultValue = "") List<String> tags) {
        return offersService.getOffers(pageLimit, pageOffset, orderingCriteria, tags);
    }


    @GetMapping("/{id}")
    public OfferDetailsDTO getOfferDetails(@PathVariable("id") Long offerId) {
        return offersService.getOfferDetails(offerId);
    }


    @PostMapping
    public Long addOffer(@RequestBody OfferPostDTO offerPostDto) {
        return offersService.addOffer(offerPostDto);
    }


    @PostMapping("/{id}")
    public Long updateOffer(@PathVariable("id") Long offerId, @RequestBody OfferPostDTO offerPostDto) {
        return offersService.updateOffer(offerId, offerPostDto);
    }


    @DeleteMapping("/{id}")
    public Long deleteOffer(@PathVariable("id") Long offerId) {
        offersService.deleteOffer(offerId);
        return offerId;
    }


    @GetMapping("/tags")
    public List<Tag> getTags() {
        return offersService.getTags();
    }

}
