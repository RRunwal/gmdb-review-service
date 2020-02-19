package com.galvanize.Services;

import com.galvanize.Models.Review;
import com.galvanize.Repositories.ReviewRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class GmdbReviewServiceTests {
    @Autowired
    GmdbReviewService gmdbReviewService;

    //NOTE: test data has been loaded into the review table in gmdb database.
    //please see file "download.sql" in gmdb-movies-project
    @Test
    void retrieveReviewByUserAndMovie() {
        List<Review> reviews = gmdbReviewService.retrieveReviews(7l, 88l);
        System.out.println("\n");
        reviews.forEach(System.out::println);
        System.out.println("\n");
        assertTrue(reviews.size()>1);
    }

    @Test
    void retrieveReviewByMovie() {
        List<Review> reviews = gmdbReviewService.retrieveReviews(null, 88l);
        System.out.println("\n");
        reviews.forEach(System.out::println);
        System.out.println("\n");
        assertTrue(reviews.size()>2);
    }

    @Test
    void retrieveReviewNoneFound() {
        List<Review> reviews = gmdbReviewService.retrieveReviews(null, 108l);
        System.out.println("\n");
        reviews.forEach(System.out::println);
        System.out.println("\n");
        assertTrue(reviews.size()==0);
    }

    @Test
    void createReviewSuccess() {
        Review review = new Review();
        review.setReviewText("Controller test for review service.");
        review.setReviewTitle("Controller Test");
        review.setMovieId(69l);
        review.setReviewerId(7l);
        review.setLastUpdated(new Date());

        Review createReviewResult = gmdbReviewService.createReview(review);
        System.out.println("\n");
        System.out.println(createReviewResult);
        System.out.println("\n");
        assertNotNull(createReviewResult);
    }

    @Test
    void updateReviewSuccess() {
        Review review = new Review();
        review.setId(11l);
        review.setReviewText("I'm updating the review text for review id 11");
        review.setReviewTitle("Service Test");
        review.setLastUpdated(new Date());

        Review updateReviewResult = gmdbReviewService.updateReview(review, 11l);
        System.out.println("\n");
        System.out.println(updateReviewResult);
        System.out.println("\n");
        assertNotNull(updateReviewResult);
    }

    @Test
    void updateReviewFailure() throws RuntimeException{
        Review review = new Review();
        review.setId(111l);
        review.setReviewText("I'm updating the review text for review id 111");
        review.setReviewTitle("Service Test");
        review.setLastUpdated(new Date());

        try {
            Review updateReviewResult = gmdbReviewService.updateReview(review, 111l);
            System.out.println("\n");
            System.out.println(updateReviewResult);
            System.out.println("\n");
            fail("Expected exception");
        } catch (RuntimeException re) {
            String message = "Review Not Found";
            assertEquals(message, re.getMessage());
        }
    }
}
