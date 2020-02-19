package com.galvanize.Services;

import com.galvanize.Models.Review;
import com.galvanize.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GmdbReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public GmdbReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> retrieveReviews(Long reviewerId, Long movieId) {
        List<Review> reviews = null;
        if (reviewerId != null && movieId != null) {
            reviews = reviewRepository.findReviewsByReviewerIdAndMovieId(reviewerId, movieId);
        } else if (movieId != null) {
            reviews = reviewRepository.findAllByMovieId(movieId);
        }
        return reviews;
    }

    public Review createReview (Review review) {
        review.setLastUpdated(new Date());
        //saveAndFlush are defaults - do not need to create in your repo
        return reviewRepository.saveAndFlush(review);
    }

    public Review updateReview (Review review, Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (!optionalReview.isPresent()) {
            throw new RuntimeException("Review Not Found");
        } else {
            review.setId(id);
            review.setLastUpdated(new Date());
            return reviewRepository.save(review);
        }
    }
}
