package com.galvanize.Repositories;

import com.galvanize.Models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    ReviewRepository reviewRepository;

    //NOTE: test data has been loaded into the review table in gmdb database.
    //please see file "download.sql" in gmdb-movies-project
    @Test
    void findReviewsByReviewerIdAndMovieId() {
        List<Review> reviews = reviewRepository.findReviewsByReviewerIdAndMovieId(7l, 88l);
        System.out.println("\n");
        reviews.forEach(System.out::println);
        System.out.println("\n");
        assertTrue(reviews.size()>1);
    }

    @Test
    void findAllByMovieId() {
        List<Review> reviews = reviewRepository.findAllByMovieId(88l);
        System.out.println("\n");
        reviews.forEach(System.out::println);
        System.out.println("\n");
        assertTrue(reviews.size()>2);
    }
}
