package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.dto.ReviewDto;
import com.ftn.sbnz.service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies/{movieId}/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewService reviewService;

    // GET /api/movies/{movieId}/reviews
    @GetMapping
    public List<ReviewDto> getReviews(@PathVariable Long movieId) {
        return reviewService.getReviewsForMovie(movieId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    // POST /api/movies/{movieId}/reviews
    @PostMapping
    public ResponseEntity<ReviewDto> addReview(
            @PathVariable Long movieId,
            @RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.addReview(movieId, dto));
    }

    // DELETE /api/movies/{movieId}/reviews/{reviewId}
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Long movieId,
            @PathVariable Long reviewId) {

        return reviewService.deleteReview(movieId, reviewId)
                ? ResponseEntity.ok("Review deleted")
                : ResponseEntity.notFound().build();

    }
}