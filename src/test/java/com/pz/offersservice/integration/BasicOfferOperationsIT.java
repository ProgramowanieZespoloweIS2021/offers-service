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

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = OffersServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class BasicOfferOperationsIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void canCreateQueryAndDeleteValidOffer() throws Exception {

        // json with details of offer that should be created
        String offerPostDtoJson = """
                {
                    "title": "Example offer 1",
                    "description": "Example description",
                    "ownerId": 1,
                    "tags": ["creation", "webapp", "business"],
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

        // send request to create offer
        MvcResult offerCreationResult = mockMvc
                .perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(offerPostDtoJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // get ID of created offer from the HTTP response
        long createdOfferId = Long.parseLong(offerCreationResult.getResponse().getContentAsString());

        // send request to acquire created offer details
        mockMvc
                .perform(get("/offers/" + createdOfferId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(createdOfferId))
                .andExpect(jsonPath("$.ownerId").value(1))
                .andExpect(jsonPath("$.title").value("Example offer 1"))
                .andExpect(jsonPath("$.description").value("Example description"))
                .andExpect(jsonPath("$.archived").value(false))
                .andExpect(jsonPath("$.tiers", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.tags", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.thumbnails", Matchers.hasSize(1)))
                .andReturn();

        // send request to delete offer - the expected behaviour is that the offer will be marked as archived
        mockMvc
                .perform(delete("/offers/" + createdOfferId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // send request to acquire archived offer details
        mockMvc
                .perform(get("/offers/" + createdOfferId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.archived").value(true))
                .andReturn();
    }


    @Test
    public void canCreateAndEditValidOffer() throws Exception {

        // json with details of offer that should be created
        String offerToCreateJson = """
                {
                    "title": "Example offer 1",
                    "description": "Example description",
                    "ownerId": 1,
                    "tags": ["creation", "webapp", "business"],
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

        String offerEditJson = """
                {
                    "title": "Example edited offer title",
                    "description": "Example edited description",
                    "ownerId": 1,
                    "tags": ["creation", "webapp", "business"],
                    "thumbnails": ["https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"],
                    "tiers": [
                        {
                            "title": "only one tier",
                            "description": "there is only one tier after edit",
                            "price": 7.50,
                            "deliveryTime": 3
                        }
                    ]
                }
                """;

        // send request to create offer
        MvcResult offerCreationResult = mockMvc
                .perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(offerToCreateJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // get ID of created offer from the HTTP response
        long createdOfferId = Long.parseLong(offerCreationResult.getResponse().getContentAsString());

        // send request to edit offer
        MvcResult offerEditResult = mockMvc
                .perform(post("/offers/" + createdOfferId).contentType(MediaType.APPLICATION_JSON).content(offerEditJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // get ID of the offer after edit (it should be different than the one returned after creation)
        long offerAfterEditId = Long.parseLong(offerEditResult.getResponse().getContentAsString());

        // check if the IDs are different - offer edit should create new offer and mark the old one as archived
        assertNotEquals(createdOfferId, offerAfterEditId);

        // send request to acquire offer after edit details
        mockMvc
                .perform(get("/offers/" + offerAfterEditId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(offerAfterEditId))
                .andExpect(jsonPath("$.ownerId").value(1))
                .andExpect(jsonPath("$.title").value("Example edited offer title"))
                .andExpect(jsonPath("$.description").value("Example edited description"))
                .andExpect(jsonPath("$.archived").value(false))
                .andExpect(jsonPath("$.tiers", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.tags", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.thumbnails", Matchers.hasSize(1)))
                .andReturn();

        // send request to check if the original offer has been archived
        mockMvc
                .perform(get("/offers/" + createdOfferId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.archived").value(true))
                .andReturn();
    }
}
