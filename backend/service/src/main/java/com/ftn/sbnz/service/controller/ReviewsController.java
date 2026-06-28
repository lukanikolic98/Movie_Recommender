package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.dto.ReviewDto;
import com.ftn.sbnz.service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    // POST /api/movies/{movieId}/reviews
    @PostMapping
    public ResponseEntity<ReviewDto> addReview(
            @PathVariable Long movieId,
            @RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.addReview(movieId, dto));
    }
}