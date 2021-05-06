package com.pz.offersservice;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = OffersServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class CreateOfferIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void canCreateValidOffer() throws Exception {
        var offerPostDtoJson = """
                {
                    "title": "Example offer 1",
                    "description": "Example description",
                    "ownerId": 0,
                    "tags": ["cpp", "java", "backend"],
                    "thumbnails": ["https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"],
                    "tiers": [
                        {
                            "title": "tier1",
                            "description": "tier1 description",
                            "price": 5.50,
                            "deliveryTime": 2
                        },
                        {
                            "title": "tier2",
                            "description": "tier2 description",
                            "price": 7.50,
                            "deliveryTime": 3
                        }
                    ]
                }
                """;

        MvcResult result = mockMvc
                .perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(offerPostDtoJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        long createdOfferId = Long.parseLong(result.getResponse().getContentAsString());

        mockMvc
                .perform(get("/offers/" + createdOfferId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(createdOfferId))
                .andExpect(jsonPath("$.title").value("Example offer 1"))
                .andExpect(jsonPath("$.description").value("Example description"))
                .andExpect(jsonPath("$.tiers", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.tags", Matchers.hasSize(3)));
    }
}
