package com.galvanize.Controllers;

import com.galvanize.Models.Review;
import com.galvanize.Services.GmdbReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/reviews")
public class GmdbRestReviewController {
    private GmdbReviewService gmdbReviewService;

    @Autowired
    public GmdbRestReviewController(GmdbReviewService gmdbReviewService) {
        this.gmdbReviewService = gmdbReviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(@RequestParam(required = false) Long reviewerId,
                                                               @RequestParam(required = false) Long movieId){
        Optional<List<Review>> reviews = Optional.ofNullable(gmdbReviewService.retrieveReviews(reviewerId, movieId));
        String errMsg = "";
        if (reviewerId == null && movieId == null) {
            errMsg = "Invalid Review Search";
        } else if (reviews.get().size()==0) {
            errMsg = "No Reviews Found";
        }
        return (reviews.isPresent() && reviews.get().size()>0 ? ResponseEntity.ok(reviews.get())
                : ResponseEntity.badRequest().header("errorMessage", errMsg).build());
    }

    @PostMapping
    public ResponseEntity<Review> postReview(@RequestBody Review review) {
//        System.out.println(review);
        if (review.getReviewText() == null && review.getReviewTitle() == null){
            return ResponseEntity.badRequest().build();
        }
        else {
            Review newReview = gmdbReviewService.createReview(review);
            return ResponseEntity.ok(newReview);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> putReview(@RequestBody Review review, @PathVariable Long id) {
        if (review.getReviewText() == null || id == null){
            return ResponseEntity.badRequest().build();
        }
        else {
            Review newReview = gmdbReviewService.updateReview(review, id);
            return ResponseEntity.ok(newReview);
        }
    }

}
