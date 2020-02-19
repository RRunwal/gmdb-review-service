package com.galvanize.Repositories;

import com.galvanize.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByReviewerIdAndMovieId(Long reviewerId, Long movieId);
    List<Review> findAllByMovieId(Long movieId);
}
