package com.pz.offersservice.offers.controller;

import com.pz.offersservice.offers.dto.OfferPostDTO;
import com.pz.offersservice.offers.dto.OfferReportDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.filter.filter.FilteringCriteria;
import com.pz.offersservice.offers.filter.FilteringCriteriaParser;
import com.pz.offersservice.offers.order.OrderingCriteria;
import com.pz.offersservice.offers.order.OrderingCriteriaParser;
import com.pz.offersservice.offers.service.OffersService;
import com.pz.offersservice.offers.entity.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/offers")
public class OffersController {

    private final OffersService offersService;
    private final OrderingCriteriaParser orderingCriteriaParser;
    private final FilteringCriteriaParser filteringCriteriaParser;


    public OffersController(OffersService offersService,
                            OrderingCriteriaParser orderingCriteriaParser,
                            FilteringCriteriaParser filteringCriteriaParser) {
        this.offersService = offersService;
        this.orderingCriteriaParser = orderingCriteriaParser;
        this.filteringCriteriaParser = filteringCriteriaParser;
    }


    @GetMapping
    public OfferReportDTO getOffers(@RequestParam(defaultValue = "20", name = "limit") Integer pageLimit,
                                    @RequestParam(defaultValue = "0", name = "offset") Integer pageOffset,
                                    @RequestParam(defaultValue = "", name = "order_by") List<String> orderBy,
                                    @RequestParam(defaultValue = "", name = "owner_id") String ownerIdFilter,
                                    @RequestParam(defaultValue = "", name = "min_price") List<String> minimalPriceFilter,
                                    @RequestParam(defaultValue = "", name = "tags") List<String> tagsFilter) {
        List<OrderingCriteria> orderingCriteria = orderingCriteriaParser.parse(orderBy);
        List<FilteringCriteria> filteringCriteria = filteringCriteriaParser.parse(ownerIdFilter, minimalPriceFilter, tagsFilter);
        return offersService.getOffers(pageLimit, pageOffset, orderingCriteria, filteringCriteria);
    }


    @GetMapping("/{id}")
    public Offer getOfferDetails(@PathVariable("id") Long offerId) {
        return offersService.getOfferDetails(offerId);
    }


    @PostMapping
    public Long addOffer(@RequestBody @Valid OfferPostDTO offerPostDto) {
        return offersService.addOffer(offerPostDto);
    }


    @PostMapping("/{id}")
    public Long updateOffer(@PathVariable("id") Long offerId, @RequestBody @Valid OfferPostDTO offerPostDto) {
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
