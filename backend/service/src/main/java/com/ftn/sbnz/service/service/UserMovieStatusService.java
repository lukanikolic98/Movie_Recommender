package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.models.Movie;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.models.UserMovieStatus;
import com.ftn.sbnz.service.dto.MovieDto;
import com.ftn.sbnz.service.repository.MovieRepository;
import com.ftn.sbnz.service.repository.UserMovieStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserMovieStatusService {

    private final UserMovieStatusRepository statusRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final MoviesService moviesService;

    // -- find or create a status row for (user, movie)
    private UserMovieStatus getOrCreate(User user, Movie movie) {
        return statusRepository.findByUserAndMovie(user, movie)
                .orElseGet(() -> {
                    UserMovieStatus s = new UserMovieStatus();
                    s.setUser(user);
                    s.setMovie(movie);
                    return s;
                });
    }

    private Movie findMovie(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + id));
    }

    // -- like / dislike
    // reaction: true = like, false = dislike
    // calling the same reaction twice toggles it off (null)
    @Transactional
    public MovieDto react(Long movieId, boolean isLike) {
        User user = userService.getCurrentUser();
        Movie movie = findMovie(movieId);
        UserMovieStatus status = getOrCreate(user, movie);

        Boolean current = status.getReaction();
        Boolean next = isLike ? true : false;

        // toggle off if same reaction clicked again
        status.setReaction(next.equals(current) ? null : next);
        status.setUpdatedAt(LocalDateTime.now());
        statusRepository.save(status);

        return moviesService.toDto(movie, user);
    }

    // -- watchlist
    // works as
    @Transactional
    public MovieDto toggleWatchlist(Long movieId) {
        User user = userService.getCurrentUser();
        Movie movie = findMovie(movieId);
        UserMovieStatus status = getOrCreate(user, movie);

        status.setWatchlisted(!status.isWatchlisted());
        status.setUpdatedAt(LocalDateTime.now());
        statusRepository.save(status);

        return moviesService.toDto(movie, user);
    }

    // -- mark watched
    // works as a toggle
    @Transactional
    public MovieDto toggleWatched(Long movieId) {
        User user = userService.getCurrentUser();
        Movie movie = findMovie(movieId);
        UserMovieStatus status = getOrCreate(user, movie);

        status.setWatched(!status.isWatched());
        status.setUpdatedAt(LocalDateTime.now());
        statusRepository.save(status);

        return moviesService.toDto(movie, user);
    }
}