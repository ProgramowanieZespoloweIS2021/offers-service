package com.pz.offersservice.unit;

import com.pz.offersservice.offers.domain.OfferRepository;
import com.pz.offersservice.offers.domain.OfferService;
import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Tag;
import com.pz.offersservice.offers.domain.entity.Thumbnail;
import com.pz.offersservice.offers.domain.entity.Tier;
import com.pz.offersservice.offers.domain.exception.InvalidOfferSpecificationException;
import com.pz.offersservice.utils.SampleOfferTestDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void validOfferDetailsAreReturned() {
        List<Tier> tiers = SampleOfferTestDataProvider.sampleValidTiers(1L, 2L, 3L);
        List<Tag> tags = SampleOfferTestDataProvider.tagsFromNames("Java", "JavaScript");
        List<Thumbnail> thumbnails = SampleOfferTestDataProvider.sampleValidThumbnails();
        Long offerToQueryId = 1L;
        Offer sampleOffer = Offer.builder()
                .id(offerToQueryId).ownerId(1L).title("Sample offer").description("Sample offer description")
                .creationTimestamp(LocalDateTime.now()).isArchived(false)
                .tiers(tiers).tags(tags).thumbnails(thumbnails)
                .build();
        Mockito.when(offerRepository.get(offerToQueryId)).thenReturn(Optional.of(sampleOffer));

        Offer returnedOffer = underTest.getOfferDetails(offerToQueryId);

        assertEquals(sampleOffer.getId(), returnedOffer.getId());
        assertEquals(sampleOffer.getOwnerId(), returnedOffer.getOwnerId());
        assertEquals(sampleOffer.getTitle(), returnedOffer.getTitle());
        assertEquals(sampleOffer.getDescription(), returnedOffer.getDescription());
        assertEquals(sampleOffer.getArchived(), returnedOffer.getArchived());
        assertEquals(sampleOffer.getTags().size(), returnedOffer.getTags().size());
        assertEquals(sampleOffer.getTiers().size(), returnedOffer.getTiers().size());
        assertEquals(sampleOffer.getThumbnails().size(), returnedOffer.getThumbnails().size());
        Mockito.verify(offerRepository, Mockito.times(1)).get(offerToQueryId);
    }

    @Test
    public void requestingDetailsOfNonExistingOfferFails() {
        Mockito.when(offerRepository.get(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidOfferSpecificationException.class, () -> underTest.getOfferDetails(2L));

        Mockito.verify(offerRepository, Mockito.times(1)).get(2L);
    }
}
