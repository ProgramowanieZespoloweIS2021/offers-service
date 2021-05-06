package com.pz.offersservice.offers.adapters.persistence;

import com.pz.offersservice.offers.domain.dto.OfferReportDTO;
import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Tag;
import com.pz.offersservice.offers.domain.OfferRepository;
import com.pz.offersservice.offers.domain.OffersReportParameters;
import com.pz.offersservice.offers.adapters.persistence.dao.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OfferRepositoryJOOQ implements OfferRepository {

    private final CreateNewOfferJOOQ createNewOffer;
    private final GetOffersReportJOOQ getOffersReport;
    private final GetSingleOfferJOOQ getSingleOffer;
    private final MarkOfferAsArchivedJOOQ markOfferAsArchived;
    private final GetAllTagsJOOQ getAllTags;

    public OfferRepositoryJOOQ(CreateNewOfferJOOQ createNewOffer,
                               GetOffersReportJOOQ getOffersReport,
                               GetSingleOfferJOOQ getSingleOffer,
                               MarkOfferAsArchivedJOOQ markOfferAsArchived,
                               GetAllTagsJOOQ getAllTags) {
        this.createNewOffer = createNewOffer;
        this.getOffersReport = getOffersReport;
        this.getSingleOffer = getSingleOffer;
        this.markOfferAsArchived = markOfferAsArchived;
        this.getAllTags = getAllTags;
    }

    @Override
    public Optional<Offer> get(Long offerId) {
        return getSingleOffer.execute(offerId);
    }

    @Override
    public OfferReportDTO get(OffersReportParameters offersReportParameters) {
        return getOffersReport.execute(offersReportParameters);
    }

    @Override
    public Long add(Offer offer) {
        return createNewOffer.execute(offer);
    }

    @Override
    public void delete(Long offerId) {
        markOfferAsArchived.execute(offerId);
    }

    @Override
    public List<Tag> getTags() {
        return getAllTags.execute();
    }
}
