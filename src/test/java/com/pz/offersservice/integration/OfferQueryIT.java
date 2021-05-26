package com.pz.offersservice.integration;

import com.pz.offersservice.OffersServiceApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = OffersServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class OfferQueryIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void canPerformOfferQueryWithFiltersSortingAndPagination() throws Exception {

        // get JSON representation of three example offers
        String firstOfferJson = getFirstOfferAsJson();
        String secondOfferJson = getSecondOfferAsJson();
        String thirdOfferJson = getThirdOfferAsJson();

        // send request to create first offer
        MvcResult firstOfferCreationResult = mockMvc
                .perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(firstOfferJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        long firstOfferId = Long.parseLong(firstOfferCreationResult.getResponse().getContentAsString());

        // send request to create second offer
        MvcResult secondOfferCreationResult = mockMvc
                .perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(secondOfferJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        long secondOfferId = Long.parseLong(secondOfferCreationResult.getResponse().getContentAsString());

        // send request to create third offer
        MvcResult thirdOfferCreationResult = mockMvc
                .perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(thirdOfferJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        long thirdOfferId = Long.parseLong(thirdOfferCreationResult.getResponse().getContentAsString());

        // send request to acquire all offers (no filtering, sorted by creation timestamp)
        mockMvc
                .perform(get("/offers?limit=8&offset=0&order_by=asc:creation_timestamp"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.totalNumberOfOffers").value(3))
                .andExpect(jsonPath("$.offers", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.offers[0].id").value(firstOfferId))
                .andExpect(jsonPath("$.offers[1].id").value(secondOfferId))
                .andExpect(jsonPath("$.offers[2].id").value(thirdOfferId))
                .andReturn();

        // send request to acquire all offers owned by user with ID equal to 1
        mockMvc
                .perform(get("/offers?limit=8&offset=0&order_by=asc:creation_timestamp&owner_id=eq:1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.totalNumberOfOffers").value(2))
                .andExpect(jsonPath("$.offers", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.offers[0].id").value(firstOfferId))
                .andExpect(jsonPath("$.offers[1].id").value(thirdOfferId))
                .andReturn();

        // send request to acquire all offers with minimal price greater than 30
        mockMvc
                .perform(get("/offers?limit=8&offset=0&order_by=asc:creation_timestamp&min_price=gt:30"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.totalNumberOfOffers").value(1))
                .andExpect(jsonPath("$.offers", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.offers[0].id").value(firstOfferId))
                .andExpect(jsonPath("$.offers[0].minimalPrice", is(closeTo(50.50, 0.01))))
                .andReturn();

        // send request to acquire all offers with tags backend and java
        mockMvc
                .perform(get("/offers?limit=8&offset=0&order_by=asc:creation_timestamp&tags=webapp,business"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.totalNumberOfOffers").value(2))
                .andExpect(jsonPath("$.offers[0].id").value(firstOfferId))
                .andExpect(jsonPath("$.offers[1].id").value(thirdOfferId))
                .andReturn();

        // send request to acquire only two oldest offers (using sorting by creation timestamp and pagination)
        mockMvc
                .perform(get("/offers?limit=2&offset=0&order_by=asc:creation_timestamp"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.totalNumberOfOffers").value(3))
                .andExpect(jsonPath("$.offers", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.offers[0].id").value(firstOfferId))
                .andExpect(jsonPath("$.offers[1].id").value(secondOfferId))
                .andReturn();
    }

    private String getFirstOfferAsJson() {
        return
                """
                    {
                        "title": "Offer 1",
                        "description": "Offer 1 description",
                        "ownerId": 1,
                        "tags": ["creation", "webapp", "business"],
                        "thumbnails": [
                            "https://en.wikipedia.org/wiki/Google_logo#/media/File:First-google-logo.gif",
                            "https://en.wikipedia.org/wiki/Google_logo#/media/File:Google_2015_logo.svg"
                        ],
                        "tiers": [
                            {
                                "title": "Offer 1 tier 1",
                                "description": "Tier 1 description",
                                "price": 50.50,
                                "deliveryTime": 2
                            },
                            {
                                "title": "Offer 1 tier 2",
                                "description": "Tier 2 description",
                                "price": 70.50,
                                "deliveryTime": 3
                            }
                        ]
                    }       
                """;
    }

    private String getSecondOfferAsJson() {
        return
                """
                    {
                        "title": "Offer 2",
                        "description": "Offer 2 description",
                        "ownerId": 2,
                        "tags": ["modification", "website", "game"],
                        "thumbnails": [
                            "https://en.wikipedia.org/wiki/Google_logo#/media/File:First-google-logo.gif",
                            "https://en.wikipedia.org/wiki/Google_logo#/media/File:Google_2015_logo.svg"
                        ],
                        "tiers": [
                            {
                                "title": "Offer 2 tier 1",
                                "description": "Tier 1 description",
                                "price": 20.50,
                                "deliveryTime": 7
                            }
                        ]
                    }       
                """;
    }

    private String getThirdOfferAsJson() {
        return
                """
                    {
                        "title": "Offer 3",
                        "description": "Offer 3 description",
                        "ownerId": 1,
                        "tags": ["webapp", "business"],
                        "thumbnails": [
                            "https://en.wikipedia.org/wiki/Google_logo#/media/File:First-google-logo.gif",
                            "https://en.wikipedia.org/wiki/Google_logo#/media/File:Google_2015_logo.svg"
                        ],
                        "tiers": [
                            {
                                "title": "Offer 3 tier 1",
                                "description": "Tier 1 description",
                                "price": 60.50,
                                "deliveryTime": 7
                            },
                            {
                                "title": "Offer 3 tier 2",
                                "description": "Tier 2 description",
                                "price": 40.50,
                                "deliveryTime": 6
                            },
                            {
                                "title": "Offer 3 tier 3",
                                "description": "Tier 3 description",
                                "price": 10.50,
                                "deliveryTime": 4
                            }
                        ]
                    }       
                """;
    }
}
