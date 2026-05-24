package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.models.Movie;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.kjar.model.facts.MovieFact;
import com.ftn.sbnz.kjar.model.PreferredKeyword;
import com.ftn.sbnz.kjar.model.Recommendation;
import com.ftn.sbnz.kjar.model.facts.UserFact;
import com.ftn.sbnz.model.models.Category;
import com.ftn.sbnz.service.dto.MovieDto;
import com.ftn.sbnz.service.dto.ReviewDto;
import com.ftn.sbnz.service.repository.MovieRepository;
import com.ftn.sbnz.service.repository.ReviewRepository;
import com.ftn.sbnz.service.repository.UserRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoviesService {

  private final UserRepository userRepository;
  private final UserService userService;
  private final MovieRepository movieRepository;
  private final KieContainer kieContainer;
  private final ReviewRepository reviewRepository;

  @Autowired
  public MoviesService(UserRepository userRepository, UserService userService, MovieRepository movieRepository,
      KieContainer kieContainer, ReviewRepository reviewRepository) {
    this.userRepository = userRepository;
    this.movieRepository = movieRepository;
    this.kieContainer = kieContainer;
    this.userService = userService;
    this.reviewRepository = reviewRepository;
  }

  // Get all movies (excluding deleted)
  public List<MovieDto> getAllMovies() {
    User currentUser = userService.getCurrentUser();
    return movieRepository.findAllByDeletedFalse()
        .stream()
        .map(movie -> fromEntity(movie, currentUser))
        .collect(Collectors.toList());
  }

  // Get movie by ID (excluding deleted)
  public Optional<MovieDto> getMovieById(Long id) {
    User currentUser = userService.getCurrentUser();
    return movieRepository.findByIdAndDeletedFalse(id)
        .map(movie -> fromEntity(movie, currentUser));
  }

  public List<ReviewDto> getReviewsById(Long id) {
    Movie movie = movieRepository.findById(id).get();
    User currentUser = userService.getCurrentUser();
    return movie.getReviews().stream().map(review -> fromEntity(review, currentUser)).collect(Collectors.toList());

  }

  public ReviewDto addReviewForMovie(ReviewDto reviewDto, Long id) {
    User currentUser = userService.getCurrentUser();

    Movie movie = movieRepository.findById(id).get();

    Review review = new Review();
    review.setComment(reviewDto.getComment());
    review.setMovie(movie);
    review.setUser(currentUser);
    review.setRating(reviewDto.getRating());

    review = reviewRepository.save(review);

    currentUser.getReviews().add(review);
    userRepository.save(currentUser);

    movie.getReviews().add(review);
    movieRepository.save(movie);

    reviewDto.setId(review.getId());
    return reviewDto;
  }

  public Optional<MovieDto> likeMovieById(Long id) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Movie not found"));

    // Remove from dislikes if present
    if (movie.getDislikedBy().remove(currentUser)) {
      currentUser.getDisliked().remove(movie);
    }

    // Toggle like
    if (movie.getLikedBy().contains(currentUser)) {
      // Remove like
      movie.getLikedBy().remove(currentUser);
      currentUser.getLiked().remove(movie);
    } else {
      // Add like
      movie.getLikedBy().add(currentUser);
      currentUser.getLiked().add(movie);
    }

    // Save both sides (optional if cascade is set)
    movie = movieRepository.save(movie);
    userRepository.save(currentUser);

    return Optional.of(fromEntity(movie, currentUser));
  }

  public Optional<MovieDto> dislikeMovieById(Long id) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Movie not found"));

    // Remove from likes if present
    if (movie.getLikedBy().remove(currentUser)) {
      currentUser.getLiked().remove(movie);
    }

    // Toggle dislike
    if (movie.getDislikedBy().contains(currentUser)) {
      // Remove dislike
      movie.getDislikedBy().remove(currentUser);
      currentUser.getDisliked().remove(movie);
    } else {
      // Add dislike
      movie.getDislikedBy().add(currentUser);
      currentUser.getDisliked().add(movie);
    }

    // Save both sides (optional if cascade is set)
    movie = movieRepository.save(movie);
    userRepository.save(currentUser);

    return Optional.of(fromEntity(movie, currentUser));
  }

  // Create a new movie
  public MovieDto createMovie(Movie movie) {
    Movie saved = movieRepository.save(movie);
    User currentUser = userService.getCurrentUser();
    return fromEntity(saved, currentUser);
  }

  // Logical delete
  public boolean deleteMovie(Long id) {
    Optional<Movie> movieOpt = movieRepository.findById(id);
    if (movieOpt.isPresent()) {
      Movie movie = movieOpt.get();
      movie.setDeleted(true);
      movieRepository.save(movie);
      return true;
    }
    return false;
  }

  // Find movies by category name
  public List<MovieDto> findByCategory(String categoryName) {
    User currentUser = userService.getCurrentUser();
    return movieRepository.findByCategory(categoryName)
        .stream()
        .filter(m -> !m.isDeleted())
        .map(movie -> fromEntity(movie, currentUser))
        .collect(Collectors.toList());
  }

  // Find movies by keyword
  public List<MovieDto> findByKeyword(String keyword) {
    User currentUser = userService.getCurrentUser();
    return movieRepository.findByKeyword(keyword)
        .stream()
        .filter(m -> !m.isDeleted())
        .map(movie -> fromEntity(movie, currentUser))
        .collect(Collectors.toList());
  }

  // Find movies by category and keyword
  public List<MovieDto> findByCategoryAndKeyword(String categoryName, String keyword) {
    User currentUser = userService.getCurrentUser();
    return movieRepository.findByCategoryAndKeyword(categoryName, keyword)
        .stream()
        .filter(m -> !m.isDeleted())
        .map(movie -> fromEntity(movie, currentUser))
        .collect(Collectors.toList());
  }

  // Find movies by category or keyword
  public List<MovieDto> findByCategoryOrKeyword(String categoryName, String keyword) {
    User currentUser = userService.getCurrentUser();
    return movieRepository.findByCategoryOrKeyword(categoryName, keyword)
        .stream()
        .filter(m -> !m.isDeleted())
        .map(movie -> fromEntity(movie, currentUser))
        .collect(Collectors.toList());
  }

  // Find movies by category or keyword or title
  public List<MovieDto> findByCategoryOrKeywordOrTitle(String categoryName, String keyword, String title) {
    User currentUser = userService.getCurrentUser();
    return movieRepository.findByCategoryOrKeywordOrTitle(categoryName, keyword, title)
        .stream()
        .filter(m -> !m.isDeleted())
        .map(movie -> fromEntity(movie, currentUser))
        .collect(Collectors.toList());
  }

  // Add a category to a movie
  public MovieDto addCategory(Movie movie, Category category) {
    movie.getCategories().add(category);
    User currentUser = userService.getCurrentUser();
    return fromEntity(movieRepository.save(movie), currentUser);
  }

  // Map entity to DTO
  public static MovieDto fromEntity(Movie movie, User currentUser) {
    MovieDto dto = new MovieDto();
    dto.setId(movie.getId());
    dto.setTitle(movie.getTitle());
    dto.setDescription(movie.getDescription());
    dto.setDirector(movie.getDirector());
    dto.setPosterurl(movie.getPosterurl());

    dto.setCategories(movie.getCategories().stream()
        .map(Category::getName)
        .collect(Collectors.toSet()));

    dto.setKeywords(movie.getKeywords());

    dto.setReviewCount(movie.getReviews().size());
    dto.setAverageRating(movie.getReviews().stream()
        .mapToInt(r -> r.getRating())
        .average()
        .orElse(0.0));

    if (currentUser != null) {
      boolean liked = movie.getLikedBy().stream()
          .anyMatch(user -> user.getId().equals(currentUser.getId()));

      boolean disliked = movie.getDislikedBy().stream()
          .anyMatch(user -> user.getId().equals(currentUser.getId()));
      dto.setLiked(liked);
      dto.setDisliked(disliked);
    } else {
      dto.setLiked(false);
      dto.setDisliked(false);
    }

    return dto;
  }

  // Map entity to DTO
  public static ReviewDto fromEntity(Review review, User currentUser) {
    ReviewDto dto = new ReviewDto();
    dto.setComment(review.getComment());
    dto.setRating(review.getRating());
    dto.setUserName(currentUser.getEmail());
    return dto;
  }

  // Generate recommendations (unchanged)
  public Recommendation generateRecommendations(Long userId, List<String> keywords, List<String> categories) {
    KieSession kieSession = kieContainer.newKieSession("recommendationSession");
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Insert user fact
    UserFact userFact = new UserFact(
        user.getId(),
        user.getFirstname(),
        user.getLastname(),
        user.getLiked().stream().map(Movie::getId).collect(Collectors.toSet()),
        user.getDisliked().stream().map(Movie::getId).collect(Collectors.toSet()),
        user.getWatched().stream().map(Movie::getId).collect(Collectors.toSet()));
    kieSession.insert(userFact);

    // Insert movies
    getAllMovies().forEach(movie -> {
      List<String> keywordList = new ArrayList<>(movie.getKeywords());
      List<String> categoryNames = new ArrayList<>(movie.getCategories());
      kieSession.insert(new MovieFact(
          movie.getId(),
          movie.getTitle(),
          keywordList,
          movie.getAverageRating(),
          categoryNames));
    });

    // Insert preferred keywords
    if (keywords != null) {
      keywords.forEach(keyword -> kieSession.insert(new PreferredKeyword(keyword)));
    }

    Recommendation recommendation = new Recommendation(userId);
    kieSession.insert(recommendation);

    kieSession.fireAllRules();
    return recommendation;
  }
}
