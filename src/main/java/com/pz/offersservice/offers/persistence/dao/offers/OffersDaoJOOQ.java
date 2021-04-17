package com.pz.offersservice.offers.persistence.dao.offers;

import com.pz.offersservice.offers.dto.OfferReportDTO;
import com.pz.offersservice.offers.entity.Offer;
import com.pz.offersservice.offers.persistence.query.CreateNewOfferJOOQ;
import com.pz.offersservice.offers.persistence.query.GetOffersReportJOOQ;
import com.pz.offersservice.offers.persistence.query.GetSingleOfferJOOQ;
import com.pz.offersservice.offers.persistence.query.MarkOfferAsArchivedJOOQ;
import com.pz.offersservice.offers.service.OffersReportParameters;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
public class OffersDaoJOOQ implements OffersDao {

    private final GetOffersReportJOOQ getOffersReportJOOQ;
    private final GetSingleOfferJOOQ getSingleOfferJOOQ;
    private final MarkOfferAsArchivedJOOQ markOfferAsArchivedJOOQ;
    private final CreateNewOfferJOOQ createNewOfferJOOQ;

    public OffersDaoJOOQ(GetOffersReportJOOQ getOffersReportJOOQ,
                         GetSingleOfferJOOQ getSingleOfferJOOQ,
                         MarkOfferAsArchivedJOOQ markOfferAsArchivedJOOQ,
                         CreateNewOfferJOOQ createNewOfferJOOQ) {
        this.getOffersReportJOOQ = getOffersReportJOOQ;
        this.getSingleOfferJOOQ = getSingleOfferJOOQ;
        this.markOfferAsArchivedJOOQ = markOfferAsArchivedJOOQ;
        this.createNewOfferJOOQ = createNewOfferJOOQ;
    }

    @Override
    public OfferReportDTO getOffers(OffersReportParameters offersReportParameters) {
        return getOffersReportJOOQ.execute(offersReportParameters);
    }

    @Override
    public Optional<Offer> getOffer(Long offerId) {
        return getSingleOfferJOOQ.execute(offerId);
    }

    @Override
    public void deleteOffer(Long offerId) {
        markOfferAsArchivedJOOQ.execute(offerId);
    }

    @Override
    public Long createOffer(Offer offer) {
        return createNewOfferJOOQ.execute(offer);
    }

}
