package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Movie;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.model.models.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovie(Movie movie);

    Optional<Review> findByUserAndMovie(User user, Movie movie);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie = :movie")
    Double calculateAverageRating(@Param("movie") Movie movie);

    Optional<Review> findByIdAndMovieIdAndUserId(Long id, Long movieId, Long userId);
}
