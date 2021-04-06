package com.pz.offersservice.offers.service;

import com.pz.offersservice.offers.converter.OfferPostDtoToEntityConverter;
import com.pz.offersservice.offers.dto.OfferFetchDto;
import com.pz.offersservice.offers.dto.OfferPostDto;
import com.pz.offersservice.offers.model.Offer;
import com.pz.offersservice.offers.repository.OffersRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OffersService {

    private final OffersRepository offersRepository;
    private final OffersQueryFactory offersQueryFactory;
    private final OfferPostDtoToEntityConverter offerPostDtoToEntityConverter;


    public OffersService(OffersRepository offersRepository,
                         OffersQueryFactory offersQueryFactory,
                         OfferPostDtoToEntityConverter offerPostDtoToEntityConverter) {
        this.offersRepository = offersRepository;
        this.offersQueryFactory = offersQueryFactory;
        this.offerPostDtoToEntityConverter = offerPostDtoToEntityConverter;
    }


    public Collection<OfferFetchDto> getOffers(int pageLimit, int pageOffset, List<String> orderingCriteria) {
        var query = offersQueryFactory.getOffersQuery(pageLimit, pageOffset, orderingCriteria);
        return query.getResultList().stream().map(OfferFetchDto::fromOfferEntity).collect(Collectors.toList());
    }


    public Offer getOfferDetails(Long offerId) {
        return offersRepository.getOne(offerId);
    }


    public void addOffer(OfferPostDto offerPostDto) {
        Offer offer = offerPostDtoToEntityConverter.convert(offerPostDto);
        offersRepository.save(offer);
    }


    public void updateOffer(Long offerId, OfferPostDto offerPostDto) {
        deleteOffer(offerId);
        addOffer(offerPostDto);
    }


    public void deleteOffer(Long offerId) {
        Offer offerToDelete = offersRepository.getOne(offerId);
        offerToDelete.setArchived(true);
    }

}
