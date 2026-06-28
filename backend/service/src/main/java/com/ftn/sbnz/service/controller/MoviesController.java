package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.dto.MovieDto;
import com.ftn.sbnz.service.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;

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

    // DELETE /api/movies/{id}  (admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        return moviesService.deleteMovie(id)
                ? ResponseEntity.ok("Movie deleted successfully")
                : ResponseEntity.notFound().build();
    }
}