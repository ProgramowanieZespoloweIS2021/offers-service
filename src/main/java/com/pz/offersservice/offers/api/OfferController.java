package com.pz.offersservice.offers.api;

import com.pz.offersservice.offers.api.mapper.UrlParametersToFilteringCriteriaMapper;
import com.pz.offersservice.offers.api.mapper.UrlParametersToOrderingCriteriaMapper;
import com.pz.offersservice.offers.domain.dto.OfferPostDTO;
import com.pz.offersservice.offers.domain.dto.OfferReportDTO;
import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.filter.FilteringCriteria;
import com.pz.offersservice.offers.domain.order.OrderingCriteria;
import com.pz.offersservice.offers.domain.OfferService;
import com.pz.offersservice.offers.domain.entity.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final UrlParametersToOrderingCriteriaMapper urlParametersToOrderingCriteriaMapper;
    private final UrlParametersToFilteringCriteriaMapper urlParametersToFilteringCriteriaMapper;


    public OfferController(OfferService offerService,
                           UrlParametersToOrderingCriteriaMapper urlParametersToOrderingCriteriaMapper,
                           UrlParametersToFilteringCriteriaMapper urlParametersToFilteringCriteriaMapper) {
        this.offerService = offerService;
        this.urlParametersToOrderingCriteriaMapper = urlParametersToOrderingCriteriaMapper;
        this.urlParametersToFilteringCriteriaMapper = urlParametersToFilteringCriteriaMapper;
    }


    @GetMapping
    public OfferReportDTO getOffers(@RequestParam(defaultValue = "20", name = "limit") Integer pageLimit,
                                    @RequestParam(defaultValue = "0", name = "offset") Integer pageOffset,
                                    @RequestParam(defaultValue = "", name = "order_by") List<String> orderBy,
                                    @RequestParam(defaultValue = "", name = "owner_id") String ownerIdFilter,
                                    @RequestParam(defaultValue = "", name = "min_price") List<String> minimalPriceFilter,
                                    @RequestParam(defaultValue = "", name = "tags") List<String> tagsFilter) {
        List<OrderingCriteria> orderingCriteria = urlParametersToOrderingCriteriaMapper.map(orderBy);
        List<FilteringCriteria> filteringCriteria = urlParametersToFilteringCriteriaMapper.map(ownerIdFilter, minimalPriceFilter, tagsFilter);
        return offerService.getOffers(pageLimit, pageOffset, orderingCriteria, filteringCriteria);
    }


    @GetMapping("/{id}")
    public Offer getOfferDetails(@PathVariable("id") Long offerId) {
        return offerService.getOfferDetails(offerId);
    }


    @PostMapping
    public Long addOffer(@RequestBody @Valid OfferPostDTO offerPostDto) {
        return offerService.addOffer(offerPostDto);
    }


    @PostMapping("/{id}")
    public Long updateOffer(@PathVariable("id") Long offerId, @RequestBody @Valid OfferPostDTO offerPostDto) {
        return offerService.updateOffer(offerId, offerPostDto);
    }


    @DeleteMapping("/{id}")
    public Long deleteOffer(@PathVariable("id") Long offerId) {
        offerService.deleteOffer(offerId);
        return offerId;
    }


    @GetMapping("/tags")
    public List<Tag> getTags() {
        return offerService.getTags();
    }

}
