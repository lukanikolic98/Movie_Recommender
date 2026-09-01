package com.ftn.sbnz.service.service;

import com.ftn.sbnz.kjar.model.facts.MovieFact;
import com.ftn.sbnz.kjar.model.facts.ReviewFact;
import com.ftn.sbnz.kjar.model.facts.UserFact;
import com.ftn.sbnz.kjar.model.level_1.KeywordInteraction;
import com.ftn.sbnz.kjar.model.level_2.KeywordStats;
import com.ftn.sbnz.kjar.model.level_3.MovieStats;
import com.ftn.sbnz.kjar.model.level_4.Recommendation;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.dto.MovieDto;
import com.ftn.sbnz.service.dto.MovieSearchResultDto;
import com.ftn.sbnz.service.dto.PopularKeywordDto;
import com.ftn.sbnz.service.dto.RecommendationDto;
import com.ftn.sbnz.service.repository.MovieRepository;
import com.ftn.sbnz.service.repository.UserMovieStatusRepository;
import com.ftn.sbnz.service.repository.UserRepository;
import com.ftn.sbnz.service.repository.spec.MovieSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.ftn.sbnz.service.dto.MovieSearchResultDto;
import lombok.RequiredArgsConstructor;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoviesService {

        private final MovieRepository movieRepository;
        private final UserMovieStatusRepository userMovieStatusRepository;
        private final UserService userService;
        private final KieContainer kieContainer;
        private final UserRepository userRepository;

        // -- Get all movies
        public List<MovieDto> getAllMovies() {
                User currentUser = userService.getCurrentUser();
                return movieRepository.findAll()
                                .stream()
                                .map(movie -> toDto(movie, currentUser))
                                .collect(Collectors.toList());
        }

        // -- Get movie by ID
        public Optional<MovieDto> getMovieById(Long id) {
                User currentUser = userService.getCurrentUser();
                return movieRepository.findById(id)
                                .map(movie -> toDto(movie, currentUser));
        }

        // -- Search by title, genre, or keyword
        public List<MovieDto> search(String term) {
                User currentUser = userService.getCurrentUser();
                return movieRepository.search(term)
                                .stream()
                                .map(movie -> toDto(movie, currentUser))
                                .collect(Collectors.toList());
        }

        // -- Soft delete (admin only)
        public boolean deleteMovie(Long id) {
                return movieRepository.findById(id).map(movie -> {
                        movie.setDeleted(true);
                        movieRepository.save(movie);
                        return true;
                }).orElse(false);
        }

        public MovieSearchResultDto browseMovies(
                        String term,
                        String genre,
                        Double minRating,
                        Integer year,
                        String language,
                        String sortBy,
                        int page,
                        int pageSize) {

                User currentUser = userService.getCurrentUser();

                Specification<Movie> spec = Specification
                                .where(MovieSpecifications.titleContains(term))
                                .and(MovieSpecifications.hasGenre(genre))
                                .and(MovieSpecifications.hasMinRating(minRating))
                                .and(MovieSpecifications.releasedInYear(year))
                                .and(MovieSpecifications.hasLanguage(language));

                int safePageSize = Math.min(Math.max(pageSize, 1), 100);
                int safePage = Math.max(page - 1, 0); // frontend is 1-based, Pageable is 0-based

                Pageable pageable = PageRequest.of(safePage, safePageSize, resolveSort(sortBy));

                Page<Movie> result = movieRepository.findAll(spec, pageable);

                List<MovieDto> movies = result.getContent().stream()
                                .map(movie -> toDto(movie, currentUser))
                                .collect(Collectors.toList());

                return new MovieSearchResultDto(
                                movies,
                                page,
                                result.getTotalPages(),
                                result.getTotalElements());
        }

        private Sort resolveSort(String sortBy) {
                if (sortBy == null)
                        sortBy = "latest";

                return switch (sortBy) {
                        case "oldest" -> Sort.by(Sort.Direction.ASC, "releaseDate");
                        case "rating_desc" -> Sort.by(Sort.Direction.DESC, "tmdbVoteAverage");
                        case "rating_asc" -> Sort.by(Sort.Direction.ASC, "tmdbVoteAverage");
                        case "popularity_desc" -> Sort.by(Sort.Direction.DESC, "popularity");
                        case "popularity_asc" -> Sort.by(Sort.Direction.ASC, "popularity");
                        default -> Sort.by(Sort.Direction.DESC, "releaseDate"); // "latest"
                };
        }

        // -- Map Movie entity ---> MovieDto
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
                                ? movie.getDirector().getName()
                                : null);

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

        public List<PopularKeywordDto> getPopularKeywords(int limit) {
                KieSession kieSession = kieContainer.newKieSession("recommendationSession");

                try {
                        // -- UserFacts: one per user, seeded only with what they liked.
                        // Dislikes/watched/watchlisted/reviews/preferred keywords & genres
                        // don't factor into global keyword popularity, so left empty.
                        userRepository.findAll().forEach(user -> {
                                Set<Long> liked = user.getMovieStatuses().stream()
                                                .filter(s -> Boolean.TRUE.equals(s.getReaction()))
                                                .map(s -> s.getMovie().getId())
                                                .collect(Collectors.toSet());

                                if (liked.isEmpty()) {
                                        return; // nothing to contribute
                                }

                                kieSession.insert(new UserFact(
                                                user.getId(), user.getFirstName(), user.getLastName(),
                                                liked, Set.of(), Set.of(), Set.of(),
                                                Set.of(), Set.of()));
                        });

                        // -- MovieFacts (shared catalog, same as generateRecommendations)
                        movieRepository.findAll().forEach(movie -> kieSession.insert(new MovieFact(
                                        movie.getId(),
                                        movie.getTitle(),
                                        movie.getKeywords().stream().map(Keyword::getName).collect(Collectors.toList()),
                                        movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList()),
                                        movie.getTmdbVoteAverage(),
                                        movie.getReviewAverage())));

                        // ── Fire rules A1 → A2 only ──────────────────────────────────
                        kieSession.getAgenda().getAgendaGroup("A1").setFocus();
                        kieSession.fireAllRules();

                        kieSession.getAgenda().getAgendaGroup("A2").setFocus();
                        kieSession.fireAllRules();

                        // -- Aggregate every user's per-keyword KeywordStats into global counts
                        Map<String, Integer> likesByKeyword = kieSession.getObjects(o -> o instanceof KeywordStats)
                                        .stream()
                                        .map(o -> (KeywordStats) o)
                                        .collect(Collectors.groupingBy(
                                                        KeywordStats::getKeyword,
                                                        Collectors.summingInt(KeywordStats::getLikes)));

                        return likesByKeyword.entrySet().stream()
                                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                                        .limit(limit)
                                        .map(e -> new PopularKeywordDto(e.getKey(), e.getValue()))
                                        .collect(Collectors.toList());

                } finally {
                        kieSession.dispose();
                }
        }

        public List<RecommendationDto.RecommendedMovieDto> generateRecommendations(
                        List<String> preferredKeywords, List<String> preferredGenres, boolean useHistory) {

                User user = userService.getCurrentUser();
                KieSession kieSession = kieContainer.newKieSession("recommendationSession");

                try {
                        // -- UserFact
                        Set<Long> liked = Set.of();
                        Set<Long> disliked = Set.of();
                        Set<Long> watched = Set.of();
                        Set<Long> watchlisted = Set.of();

                        if (useHistory) {
                                liked = user.getMovieStatuses().stream()
                                                .filter(s -> Boolean.TRUE.equals(s.getReaction()))
                                                .map(s -> s.getMovie().getId())
                                                .collect(Collectors.toSet());

                                disliked = user.getMovieStatuses().stream()
                                                .filter(s -> Boolean.FALSE.equals(s.getReaction()))
                                                .map(s -> s.getMovie().getId())
                                                .collect(Collectors.toSet());

                                watched = user.getMovieStatuses().stream()
                                                .filter(UserMovieStatus::isWatched)
                                                .map(s -> s.getMovie().getId())
                                                .collect(Collectors.toSet());

                                watchlisted = user.getMovieStatuses().stream()
                                                .filter(UserMovieStatus::isWatchlisted)
                                                .map(s -> s.getMovie().getId())
                                                .collect(Collectors.toSet());
                        }

                        Set<String> prefKeywords = preferredKeywords != null
                                        ? new HashSet<>(preferredKeywords)
                                        : new HashSet<>();
                        Set<String> prefGenres = preferredGenres != null
                                        ? new HashSet<>(preferredGenres)
                                        : new HashSet<>();

                        kieSession.insert(new UserFact(
                                        user.getId(), user.getFirstName(), user.getLastName(),
                                        liked, disliked, watched, watchlisted,
                                        prefKeywords, prefGenres));

                        // -- ReviewFacts
                        if (useHistory) {
                                user.getReviews().forEach(review -> kieSession.insert(new ReviewFact(
                                                user.getId(),
                                                review.getMovie().getId(),
                                                review.getRating())));
                        }

                        // -- MovieFacts
                        movieRepository.findAll().forEach(movie -> kieSession.insert(new MovieFact(
                                        movie.getId(),
                                        movie.getTitle(),
                                        movie.getKeywords().stream().map(Keyword::getName).collect(Collectors.toList()),
                                        movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList()),
                                        movie.getTmdbVoteAverage(),
                                        movie.getReviewAverage())));

                        System.out.println("------------Input Facts------------");
                        System.out.println("MovieFacts facts: "
                                        + kieSession.getObjects(o -> o instanceof MovieFact).size());
                        System.out.println("UserFact facts: "
                                        + kieSession.getObjects(o -> o instanceof UserFact).size());
                        System.out.println("ReviewFact facts: "
                                        + kieSession.getObjects(o -> o instanceof ReviewFact).size());
                        // ── Fire rules in order A1 → A2 → A3 → A4 ───────────────────────────
                        kieSession.getAgenda().getAgendaGroup("A1").setFocus();
                        kieSession.fireAllRules();
                        // ------------------------------------------------------
                        System.out.println("\n Layer 1 facts");
                        System.out.println("KeywordInteraction facts: "
                                        + kieSession.getObjects(o -> o instanceof KeywordInteraction).size());
                        // ------------------------------------------------------
                        UserFact userFact = kieSession.getObjects(o -> o instanceof UserFact)
                                        .stream()
                                        .map(o -> (UserFact) o)
                                        .findFirst()
                                        .orElse(null);

                        System.out.println("UserFact: " + userFact.toString());

                        MovieFact movieFact = kieSession.getObjects(o -> o instanceof MovieFact)
                                        .stream()
                                        .map(o -> (MovieFact) o)
                                        .filter(m -> m.getId().equals(1L))
                                        .findFirst()
                                        .orElse(null);
                        System.out.println("MovieFact: " + movieFact.toString());

                        kieSession.getAgenda().getAgendaGroup("A2").setFocus();
                        kieSession.fireAllRules();

                        // ------------------------------------------------------
                        System.out.println("\n Layer 2 facts");
                        prefKeywords.forEach(keyword -> {
                                KeywordStats existing = kieSession.getObjects(
                                                o -> o instanceof KeywordStats
                                                                && ((KeywordStats) o).getKeyword()
                                                                                .equalsIgnoreCase(keyword))
                                                .stream()
                                                .map(o -> (KeywordStats) o)
                                                .findFirst()
                                                .orElse(null);

                                if (existing != null) {
                                        existing.setLikes(existing.getLikes() + 5);

                                        kieSession.update(kieSession.getFactHandle(existing), existing);
                                } else {
                                        kieSession.insert(new KeywordStats(user.getId(), keyword, 5, 0, 0, 0));
                                }
                        });

                        System.out.println("KeywordStats facts: " +
                                        kieSession.getObjects(o -> o instanceof KeywordStats).size());

                        kieSession.getAgenda().getAgendaGroup("A3").setFocus();
                        kieSession.fireAllRules();

                        // ------------------------------------------------------
                        System.out.println("\n Layer 3 facts");
                        System.out.println("MovieStats facts: "
                                        + kieSession.getObjects(o -> o instanceof MovieStats).size());
                        kieSession.getAgenda().getAgendaGroup("A4").setFocus();
                        kieSession.fireAllRules();

                        // -- Collect and sort results
                        return kieSession.getObjects(obj -> obj instanceof Recommendation)
                                        .stream()
                                        .map(obj -> (Recommendation) obj)
                                        .filter(r -> r.getUserId().equals(user.getId()))
                                        .sorted(Comparator.comparingDouble(Recommendation::getScore).reversed())
                                        .map(r -> movieRepository.findById(r.getMovieId())
                                                        .map(movie -> new RecommendationDto.RecommendedMovieDto(
                                                                        toDto(movie, user), r.getScore()))
                                                        .orElse(null))
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList());

                } finally {
                        kieSession.dispose();
                }
        }
}