package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.dto.MovieDto;
import com.ftn.sbnz.service.dto.MovieSearchResultDto;
import com.ftn.sbnz.service.dto.PopularKeywordDto;
import com.ftn.sbnz.service.dto.RecommendationDto;
import com.ftn.sbnz.service.dto.RecommendationRequestDto;
import com.ftn.sbnz.service.service.MoviesService;
import com.ftn.sbnz.service.service.UserMovieStatusService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;
    private final UserMovieStatusService statusService;

    // GET /api/movies
    @GetMapping
    public List<MovieDto> getAllMovies() {
        return moviesService.getAllMovies();
    }

    // GET /api/movies/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return moviesService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/movies/search?term=avatar
    @GetMapping("/search")
    public List<MovieDto> search(@RequestParam String term) {
        return moviesService.search(term);
    }

    // POST /api/movies/{id}/like
    @PostMapping("/{id}/like")
    public ResponseEntity<MovieDto> likeMovie(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.react(id, true));
    }

    // POST /api/movies/{id}/dislike
    @PostMapping("/{id}/dislike")
    public ResponseEntity<MovieDto> dislikeMovie(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.react(id, false));
    }

    // POST /api/movies/{id}/watchlist
    @PostMapping("/{id}/watchlist")
    public ResponseEntity<MovieDto> toggleWatchlist(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.toggleWatchlist(id));
    }

    // POST /api/movies/{id}/watched
    @PostMapping("/{id}/watched")
    public ResponseEntity<MovieDto> markWatched(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.toggleWatched(id));
    }

    // DELETE /api/movies/{id} (admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        return moviesService.deleteMovie(id)
                ? ResponseEntity.ok("Movie deleted successfully")
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/browse")
    public MovieSearchResultDto browse(
            @RequestParam(required = false) String term,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String language,
            @RequestParam(defaultValue = "latest") String sortBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        return moviesService.browseMovies(term, genre, minRating, year, language, sortBy, page, pageSize);
    }

    @GetMapping("/recommendations/keywords/popular")
    public List<PopularKeywordDto> getPopularKeywords(
            @RequestParam(defaultValue = "20") int limit) {
        return moviesService.getPopularKeywords(limit);
    }

    // POST /api/movies/recommendations
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/recommendations")
    public ResponseEntity<RecommendationDto> recommend(@RequestBody RecommendationRequestDto request) {
        RecommendationDto rec = new RecommendationDto();
        rec.setMovies(moviesService.generateRecommendations(request.getKeywords(), request.getGenres(),
                request.isUseHistory()));
        return ResponseEntity.ok(rec);
    }
}