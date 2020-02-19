package com.galvanize.Controllers;

//DO NOT FORGET to add these in order to use get(), pritn() etc. methods in your MockMcv tests
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.galvanize.Repositories.ReviewRepository;
import com.galvanize.Services.GmdbReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GmdbRestReviewControllerTests {
    @Autowired
    GmdbReviewService gmdbReviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MockMvc mvc;

    //NOTE: test data has been loaded into the review table in gmdb database.
    //please see file "download.sql" in gmdb-movies-project
    @Test
    void testGetReviews() throws Exception {
        mvc.perform(get("/reviews"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("errorMessage", "Invalid Review Search"));
        mvc.perform(get("/reviews?reviewerId=7&movieId=88"))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(get("/reviews?movieId=88"))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(get("/reviews?movieId=108"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("errorMessage", "No Reviews Found"));

    }

    @Test
    void testPostReviewSuccess() throws Exception {
        String json = "{\"reviewText\":\"Controller post test for review service.\",\"reviewTitle\":\"Controller Post Test\",\"movieId\":69,\"reviewerId\":7}";
        mvc.perform(post("/reviews")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testPostReviewFailure() throws Exception {
        String json = "{\"reviewText\":null,\"reviewTitle\":null,\"movieId\":69,\"reviewerId\":7}";
        mvc.perform(post("/reviews")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testPutReviewSuccess() throws Exception {
        String json = "{\"reviewText\":\"Controller put test for review service.\",\"reviewTitle\":\"Controller Put Test\",\"movieId\":67,\"reviewerId\":7}";
        long id = 11;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/reviews/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json);
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Controller Put Test")))
                .andDo(print());
    }

    @Test
    void testPutReviewFailure() throws Exception {
        String json = "{\"reviewText\":null,\"reviewTitle\":null,\"movieId\":69,\"reviewerId\":7}";
        long id = 111;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/reviews/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json);
        mvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
