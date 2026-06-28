package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.dto.MovieDto;
import com.ftn.sbnz.service.repository.MovieRepository;
import com.ftn.sbnz.service.repository.UserMovieStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoviesService {

    private final MovieRepository movieRepository;
    private final UserMovieStatusRepository userMovieStatusRepository;
    private final UserService userService;

    // ── Get all movies ────────────────────────────────────────────────────────
    public List<MovieDto> getAllMovies() {
        User currentUser = userService.getCurrentUser();
        return movieRepository.findAll()
                .stream()
                .map(movie -> toDto(movie, currentUser))
                .collect(Collectors.toList());
    }

    // ── Get movie by ID ───────────────────────────────────────────────────────
    public Optional<MovieDto> getMovieById(Long id) {
        User currentUser = userService.getCurrentUser();
        return movieRepository.findById(id)
                .map(movie -> toDto(movie, currentUser));
    }

    // ── Search by title, genre, or keyword ───────────────────────────────────
    public List<MovieDto> search(String term) {
        User currentUser = userService.getCurrentUser();
        return movieRepository.search(term)
                .stream()
                .map(movie -> toDto(movie, currentUser))
                .collect(Collectors.toList());
    }

    // ── Soft delete (admin only) ──────────────────────────────────────────────
    public boolean deleteMovie(Long id) {
        return movieRepository.findById(id).map(movie -> {
            movie.setDeleted(true);
            movieRepository.save(movie);
            return true;
        }).orElse(false);
    }

    // ── Map Movie entity → MovieDto ───────────────────────────────────────────
    public MovieDto toDto(Movie movie, User currentUser) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setOverview(movie.getOverview());
        dto.setLanguage(movie.getLanguage());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setRuntime(movie.getRuntime());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setReviewAverage(movie.getReviewAverage());
        dto.setTmdbVoteAverage(movie.getTmdbVoteAverage());
        dto.setTmdbVoteCount(movie.getTmdbVoteCount());

        dto.setDirector(movie.getDirector() != null
                ? movie.getDirector().getName() : null);

        dto.setGenres(movie.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toSet()));

        dto.setKeywords(movie.getKeywords().stream()
                .map(Keyword::getName)
                .collect(Collectors.toSet()));

        dto.setActors(movie.getActors().stream()
                .map(Actor::getName)
                .collect(Collectors.toSet()));

        dto.setReviewCount(movie.getReviews().size());

        // user-specific status
        if (currentUser != null) {
            userMovieStatusRepository
                    .findByUserAndMovie(currentUser, movie)
                    .ifPresentOrElse(status -> {
                        dto.setLiked(status.getReaction());
                        dto.setWatchlisted(status.isWatchlisted());
                        dto.setWatched(status.isWatched());
                    }, () -> {
                        dto.setLiked(null);
                        dto.setWatchlisted(false);
                        dto.setWatched(false);
                    });
        }

        return dto;
    }
}