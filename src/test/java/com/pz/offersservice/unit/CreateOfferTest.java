package com.pz.offersservice.unit;

import com.pz.offersservice.utils.SampleOfferTestDataProvider;
import com.pz.offersservice.offers.domain.OfferRepository;
import com.pz.offersservice.offers.domain.OfferService;
import com.pz.offersservice.offers.domain.dto.OfferPostDTO;
import com.pz.offersservice.offers.domain.entity.Offer;
import com.pz.offersservice.offers.domain.entity.Tier;
import com.pz.offersservice.offers.domain.exception.InvalidOfferSpecificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CreateOfferTest {

    @Mock
    private OfferRepository offerRepository;

    private OfferService underTest;

    @BeforeEach
    public void setUp() {
        offerRepository = Mockito.mock(OfferRepository.class);
        Mockito.when(offerRepository.add(Mockito.any(Offer.class))).thenReturn(1L);
        Mockito.when(offerRepository.getTags()).thenReturn(SampleOfferTestDataProvider.tagsFromNames("Cpp", "Java", "JavaScript"));
        underTest = new OfferService(offerRepository);
    }

    @Test
    public void validOfferIsCreated() {
        List<String> tags = Arrays.asList("Java", "Cpp");
        List<Tier> tiers = SampleOfferTestDataProvider.sampleValidTiers(null, null, null);
        List<String> thumbnails = SampleOfferTestDataProvider.sampleValidThumbnailUrls();
        OfferPostDTO offerPostDTO = new OfferPostDTO(1L, "Example offer", "Example description", tags, tiers, thumbnails);

        Long createdOfferId = underTest.addOffer(offerPostDTO);

        assertEquals(1L, createdOfferId);
        Mockito.verify(offerRepository, Mockito.times(1)).getTags();
        Mockito.verify(offerRepository, Mockito.times(1)).add(Mockito.any(Offer.class));
    }

    @Test
    public void createOfferWithInvalidTagFails() {
        List<String> invalidTags = Arrays.asList("Java", "Invalid");
        List<Tier> tiers = SampleOfferTestDataProvider.sampleValidTiers(null, null, null);
        List<String> thumbnails = SampleOfferTestDataProvider.sampleValidThumbnailUrls();
        OfferPostDTO offerPostDTO = new OfferPostDTO(1L, "Example offer", "Example description", invalidTags, tiers, thumbnails);

        assertThrows(InvalidOfferSpecificationException.class, () -> underTest.addOffer(offerPostDTO));
    }
}
