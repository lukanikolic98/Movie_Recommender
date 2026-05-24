package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.kjar.model.Recommendation;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.service.dto.MovieDto;
import com.ftn.sbnz.service.dto.MovieReviewsDto;
import com.ftn.sbnz.service.dto.RecommendationRequestDto;
import com.ftn.sbnz.service.dto.ReviewDto;
import com.ftn.sbnz.service.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

  private final MoviesService moviesService;

  @Autowired
  public MoviesController(MoviesService moviesService) {
    this.moviesService = moviesService;
  }

  // Get all movies
  @GetMapping
  public List<MovieDto> getAllMovies() {
    return moviesService.getAllMovies();
  }

  @PostMapping("/recommendation")
  public ResponseEntity<Recommendation> getRecommendations(@RequestBody RecommendationRequestDto dto) {
    Recommendation rec = moviesService.generateRecommendations(1l, dto.getKeywords(), dto.getCategories());
    return ResponseEntity.ok(rec);
  }

  // Get by category or keyword
  @GetMapping("/search")
  public List<MovieDto> searchMovies(@RequestParam String searchTerm) {
    System.out.println("CALLING SEARCH " + searchTerm);
    return moviesService.findByCategoryOrKeywordOrTitle(searchTerm, searchTerm, searchTerm);
  }

  // Get movie by ID
  @GetMapping("/{id}")
  public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
    return moviesService.getMovieById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // Get like
  @GetMapping("/{id}/like")
  public ResponseEntity<MovieDto> likeMovieById(@PathVariable Long id) {
    return moviesService.likeMovieById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // Get dislike
  @GetMapping("/{id}/dislike")
  public ResponseEntity<MovieDto> dislikeMovieById(@PathVariable Long id) {
    return moviesService.dislikeMovieById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // Get reviews
  @GetMapping("/{id}/reviews")
  public ResponseEntity<MovieReviewsDto> getReviewsById(@PathVariable Long id) {
    List<ReviewDto> reviews = moviesService.getReviewsById(id);
    MovieReviewsDto dto = new MovieReviewsDto();
    dto.setReviews(reviews);
    return ResponseEntity.ok().body(dto);
  }

  // Add review
  @PostMapping("/{id}/reviews")
  public ResponseEntity<ReviewDto> addReviewForMovieId(@PathVariable Long id, @RequestBody ReviewDto dto) {
    ReviewDto review = moviesService.addReviewForMovie(dto, id);
    return ResponseEntity.ok().body(review);
  }

  // Create a new movie
  @PostMapping
  public MovieDto createMovie(@RequestBody MovieDto movieDto) {
    // You might want to convert DTO to entity before saving, e.g.,
    // Movie movie = moviesService.dtoToEntity(movieDto);
    // return moviesService.createMovie(movie);
    // For simplicity, assuming front sends full Movie entity:
    throw new UnsupportedOperationException("Convert DTO to entity before saving");
  }

  @GetMapping("/hi")
  public String getMethodName(@RequestParam String param) {
    System.out.println("Now: " + Instant.now());
    return new String("Now: " + Instant.now());
  }

  // Delete a movie
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
    boolean deleted = moviesService.deleteMovie(id);
    if (deleted) {
      return ResponseEntity.ok("Movie deleted successfully");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
