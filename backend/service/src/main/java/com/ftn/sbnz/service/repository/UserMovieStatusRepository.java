package com.ftn.sbnz.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Movie;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.models.UserMovieStatus;


public interface UserMovieStatusRepository extends JpaRepository<UserMovieStatus, Long> {
    Optional<UserMovieStatus> findByUserAndMovie(User user, Movie movie);
}
