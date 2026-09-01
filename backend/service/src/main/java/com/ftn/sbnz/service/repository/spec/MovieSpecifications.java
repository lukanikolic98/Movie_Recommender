package com.ftn.sbnz.service.repository.spec;

import com.ftn.sbnz.model.models.Genre;
import com.ftn.sbnz.model.models.Movie;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MovieSpecifications {

    public static Specification<Movie> titleContains(String term) {
        return (root, query, cb) -> {
            if (term == null || term.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("title")), "%" + term.toLowerCase() + "%");
        };
    }

    public static Specification<Movie> hasGenre(String genre) {
        return (root, query, cb) -> {
            if (genre == null || genre.isBlank()) {
                return cb.conjunction();
            }
            Join<Movie, Genre> genres = root.join("genres");
            return cb.equal(cb.lower(genres.get("name")), genre.toLowerCase());
        };
    }

    public static Specification<Movie> hasMinRating(Double minRating) {
        return (root, query, cb) -> {
            if (minRating == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("tmdbVoteAverage"), minRating);
        };
    }

    public static Specification<Movie> releasedInYear(Integer year) {
        return (root, query, cb) -> {
            if (year == null) {
                return cb.conjunction();
            }
            return cb.between(
                    root.get("releaseDate"),
                    LocalDate.of(year, 1, 1),
                    LocalDate.of(year, 12, 31));
        };
    }

    public static Specification<Movie> hasLanguage(String language) {
        return (root, query, cb) -> {
            if (language == null || language.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("language")), language.toLowerCase());
        };
    }
}