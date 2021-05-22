package com.pz.offersservice.unit;

import com.pz.offersservice.utils.SampleOfferTestDataProvider;
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

import java.time.LocalDateTime;
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
        List<Tier> tiers = SampleOfferTestDataProvider.sampleValidTiers(1L, 2L, 3L);
        List<Tag> tags = SampleOfferTestDataProvider.tagsFromNames("Java", "JavaScript");
        List<Thumbnail> thumbnails = SampleOfferTestDataProvider.sampleValidThumbnails();
        Long offerToDeleteId = 1L;
        Offer sampleNonArchivedOffer = Offer.builder()
                .id(offerToDeleteId).ownerId(1L).title("Sample offer").description("Sample offer description")
                .creationTimestamp(LocalDateTime.now()).isArchived(false)
                .tiers(tiers).tags(tags).thumbnails(thumbnails)
                .build();
        Mockito.when(offerRepository.get(offerToDeleteId)).thenReturn(Optional.of(sampleNonArchivedOffer));

        underTest.deleteOffer(offerToDeleteId);

        Mockito.verify(offerRepository, Mockito.times(1)).get(offerToDeleteId);
        Mockito.verify(offerRepository, Mockito.times(1)).delete(offerToDeleteId);
    }

    @Test
    public void deletingAlreadyArchivedOfferFails() {
        List<Tier> tiers = SampleOfferTestDataProvider.sampleValidTiers(1L, 2L, 3L);
        List<Tag> tags = SampleOfferTestDataProvider.tagsFromNames("Java", "JavaScript");
        List<Thumbnail> thumbnails = SampleOfferTestDataProvider.sampleValidThumbnails();
        Long offerToDeleteId = 1L;
        Offer sampleArchivedOffer = Offer.builder()
                .id(offerToDeleteId).ownerId(1L).title("Sample offer").description("Sample offer description")
                .creationTimestamp(LocalDateTime.now()).isArchived(true)
                .tiers(tiers).tags(tags).thumbnails(thumbnails)
                .build();
        Mockito.when(offerRepository.get(offerToDeleteId)).thenReturn(Optional.of(sampleArchivedOffer));

        assertThrows(InvalidOfferSpecificationException.class, () -> underTest.deleteOffer(offerToDeleteId));

        Mockito.verify(offerRepository, Mockito.times(1)).get(offerToDeleteId);
    }

    @Test
    public void deletingNonExistingOfferFails() {
        Mockito.when(offerRepository.get(Mockito.any(Long.class))).thenReturn(Optional.empty());

        assertThrows(InvalidOfferSpecificationException.class, () -> underTest.deleteOffer(3L));

        Mockito.verify(offerRepository, Mockito.times(1)).get(3L);
    }
}
