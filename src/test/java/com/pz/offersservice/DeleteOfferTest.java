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

public class DeleteOfferTest {

    @Mock
    private OfferRepository offerRepository;

    private OfferService underTest;

    @BeforeEach
    public void setUp() {
        offerRepository = Mockito.mock(OfferRepository.class);
        underTest = new OfferService(offerRepository);
    }

    @Test
    public void offerIsSuccessfullyDeleted() {
        List<Tier> sampleTiers = SampleTiersFactory.fromIds(1L, 2L);
        List<Tag> sampleTags = SampleTagsFactory.fromNames("Cpp", "Java", "JavaScript");
        List<Thumbnail> sampleThumbnails = SampleThumbnailsFactory.createListOfValidThumbnails();
        Offer sampleNonArchivedOffer = SampleOffersFactory.createNonArchivedOffer(1L, sampleTiers, sampleTags, sampleThumbnails);
        Mockito.when(offerRepository.get(1L)).thenReturn(Optional.of(sampleNonArchivedOffer));

        underTest.deleteOffer(1L);

        Mockito.verify(offerRepository, Mockito.times(1)).get(1L);
        Mockito.verify(offerRepository, Mockito.times(1)).delete(1L);
    }

    @Test
    public void deletingAlreadyArchivedOfferFails() {
        List<Tier> sampleTiers = SampleTiersFactory.fromIds(1L, 2L);
        List<Tag> sampleTags = SampleTagsFactory.fromNames("Cpp", "Java", "JavaScript");
        List<Thumbnail> sampleThumbnails = SampleThumbnailsFactory.createListOfValidThumbnails();
        Offer sampleArchivedOffer = SampleOffersFactory.createArchivedOffer(2L, sampleTiers, sampleTags, sampleThumbnails);
        Mockito.when(offerRepository.get(2L)).thenReturn(Optional.of(sampleArchivedOffer));

        assertThrows(InvalidOfferSpecificationException.class, () -> underTest.deleteOffer(2L));

        Mockito.verify(offerRepository, Mockito.times(1)).get(2L);
    }

    @Test
    public void deletingNonExistingOfferFails() {
        Mockito.when(offerRepository.get(Mockito.any(Long.class))).thenReturn(Optional.empty());

        assertThrows(InvalidOfferSpecificationException.class, () -> underTest.deleteOffer(3L));

        Mockito.verify(offerRepository, Mockito.times(1)).get(3L);
    }
}
