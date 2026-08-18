package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.models.Movie;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.dto.ReviewDto;
import com.ftn.sbnz.service.repository.MovieRepository;
import com.ftn.sbnz.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;

    // -- Get all reviews for a movie
    public List<ReviewDto> getReviewsForMovie(Long movieId) {
        Movie movie = findMovie(movieId);
        return reviewRepository.findByMovie(movie)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // -- Add a review
    @Transactional
    public ReviewDto addReview(Long movieId, ReviewDto dto) {
        if (dto.getRating() < 1 || dto.getRating() > 10) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Rating must be between 1 and 10");
        }

        User user = userService.getCurrentUser();
        Movie movie = findMovie(movieId);
        Review review = reviewRepository
        .findByUserAndMovie(user, movie)
        .orElseGet(() -> {
            Review newReview = new Review();
            newReview.setUser(user);
            newReview.setMovie(movie);
            newReview.setCreatedAt(LocalDateTime.now());
            return newReview;
        });

        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review = reviewRepository.save(review);

        // update cached average on the movie
        updateReviewAverage(movie);

        return toDto(review);
    }

    // -- Update average rating on the Movie entity
    private void updateReviewAverage(Movie movie) {
        Double avg = reviewRepository.calculateAverageRating(movie);
        movie.setReviewAverage(avg != null ? Math.round(avg * 10.0) / 10.0 : null);
        movieRepository.save(movie);
    }

    // -- Delete a review
    @Transactional
    public Boolean deleteReview(Long movieId, Long reviewId) {

        User currentUser = userService.getCurrentUser();

        Review review = reviewRepository
                .findByIdAndMovieIdAndUserId(
                        reviewId,
                        movieId,
                        currentUser.getId())
                .orElse(null);

        if (review == null) {
            return false;
        }

        reviewRepository.delete(review);

        return true;
    }

    // -- Map Review ---> ReviewDto
    private ReviewDto toDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserId(review.getUser().getId());
        dto.setMovieId(review.getMovie().getId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUserName(review.getUser().getFirstName()
                + " " + review.getUser().getLastName());
        return dto;
    }

    private Movie findMovie(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + id));
    }
}