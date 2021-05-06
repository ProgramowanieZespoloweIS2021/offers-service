package com.pz.offersservice;

import com.pz.offersservice.factory.SampleOffersFactory;
import com.pz.offersservice.factory.SampleTagsFactory;
import com.pz.offersservice.factory.SampleThumbnailsFactory;
import com.pz.offersservice.factory.SampleTiersFactory;
import com.pz.offersservice.offers.domain.OfferRepository;
import com.pz.offersservice.offers.domain.OfferService;
import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Tag;
import com.pz.offersservice.offers.domain.entity.Thumbnail;
import com.pz.offersservice.offers.domain.entity.Tier;
import com.pz.offersservice.offers.domain.exception.InvalidOfferSpecificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetOfferDetailsTest {

    @Mock
    private OfferRepository offerRepository;

    private OfferService underTest;

    @BeforeEach
    public void setUp() {
        offerRepository = Mockito.mock(OfferRepository.class);
        underTest = new OfferService(offerRepository);
    }

    @Test
    public void offerDetailsAreReturned() {
        List<Tier> sampleTiers = SampleTiersFactory.fromIds(1L, 2L);
        List<Tag> sampleTags = SampleTagsFactory.fromNames("Cpp", "Java", "JavaScript");
        List<Thumbnail> sampleThumbnails = SampleThumbnailsFactory.createListOfValidThumbnails();
        Offer offer = SampleOffersFactory.createNonArchivedOffer(1L, sampleTiers, sampleTags, sampleThumbnails);
        Mockito.when(offerRepository.get(1L)).thenReturn(Optional.of(offer));

        underTest.getOfferDetails(1L);

        Mockito.verify(offerRepository, Mockito.times(1)).get(1L);
    }

    @Test
    public void requestingDetailsOfNonExistingOfferFails() {
        Mockito.when(offerRepository.get(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidOfferSpecificationException.class, () -> underTest.getOfferDetails(2L));

        Mockito.verify(offerRepository, Mockito.times(1)).get(2L);
    }
}
