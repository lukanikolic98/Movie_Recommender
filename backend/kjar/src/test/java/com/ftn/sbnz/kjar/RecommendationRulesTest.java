package com.ftn.sbnz.kjar;

import com.ftn.sbnz.kjar.model.facts.*;
import com.ftn.sbnz.kjar.model.level_1.KeywordInteraction;
import com.ftn.sbnz.kjar.model.level_2.KeywordStats;
import com.ftn.sbnz.kjar.model.level_3.MovieStats;
import com.ftn.sbnz.kjar.model.level_4.Recommendation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RecommendationRulesTest {

    private KieSession kieSession;

    @Before
    public void setUp() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        kieSession = kieContainer.newKieSession("recommendationSession");
    }

    @After
    public void tearDown() {
        if (kieSession != null) {
            kieSession.dispose();
        }
    }

    @Test
    public void testRecommendationFromDocumentationExample() {

        UserFact user = new UserFact(
                1L, "Test", "User",
                Set.of(1L),
                Set.of(),
                Set.of(),
                Set.of(),
                Set.of("scary", "slasher", "serial killer"),
                Set.of("Horror", "Mystery", "Drama"));
        kieSession.insert(user);

        kieSession.insert(new ReviewFact(1L, 1L, 8));

        kieSession.insert(new MovieFact(1L, "Silence of the Lambs",
                List.of("scary", "horror", "mystery", "killer"),
                List.of("Horror"),
                7.5, null));

        kieSession.insert(new MovieFact(2L, "Inception",
                List.of("mystery", "dream", "suspenseful"),
                List.of("Mystery"),
                8.2, null));

        kieSession.insert(new MovieFact(3L, "Scary Movie",
                List.of("scary", "comedy", "killer"),
                List.of("Comedy"),
                7.9, null));

        // fire all layers
        kieSession.getAgenda().getAgendaGroup("A1").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("A2").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("A3").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("A4").setFocus();
        kieSession.fireAllRules();

        // KeywordInteraction (type-safe, no cast warning)
        List<KeywordInteraction> interactions = kieSession.getObjects().stream()
                .filter(KeywordInteraction.class::isInstance)
                .map(KeywordInteraction.class::cast)
                .collect(Collectors.toList());

        assertFalse(interactions.isEmpty());

        // KeywordStats
        List<KeywordStats> keywordStats = kieSession.getObjects().stream()
                .filter(KeywordStats.class::isInstance)
                .map(KeywordStats.class::cast)
                .collect(Collectors.toList());

        assertFalse(keywordStats.isEmpty());

        // MovieStats
        List<MovieStats> movieStats = kieSession.getObjects().stream()
                .filter(MovieStats.class::isInstance)
                .map(MovieStats.class::cast)
                .collect(Collectors.toList());

        List<Long> scoredMovieIds = movieStats.stream()
                .map(MovieStats::getMovieId)
                .collect(Collectors.toList());

        assertTrue(scoredMovieIds.contains(1L));
        assertTrue(scoredMovieIds.contains(2L));
        assertTrue(scoredMovieIds.contains(3L));

        // Recommendations
        List<Recommendation> recommendations = kieSession.getObjects().stream()
                .filter(Recommendation.class::isInstance)
                .map(Recommendation.class::cast)
                .collect(Collectors.toList());

        Recommendation inception = recommendations.stream()
                .filter(r -> r.getMovieId().equals(2L))
                .findFirst()
                .orElse(null);

        Recommendation scaryMovie = recommendations.stream()
                .filter(r -> r.getMovieId().equals(3L))
                .findFirst()
                .orElse(null);

        assertNotNull(inception);
        assertNotNull(scaryMovie);

        assertTrue(inception.getScore() > 0);
        assertTrue(scaryMovie.getScore() > inception.getScore());

        System.out.println("=== Recommendations ===");
        recommendations.stream()
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .forEach(r -> System.out.printf("%s: %.1f%n", r.getTitle(), r.getScore()));
    }

    @Test
    public void testWatchlistBoost() {

        UserFact user = new UserFact(
                2L, "Test", "User",
                Set.of(1L),
                Set.of(),
                Set.of(),
                Set.of(2L),
                Set.of(),
                Set.of());

        kieSession.insert(user);

        kieSession.insert(new MovieFact(1L, "Silence of the Lambs",
                List.of("scary", "mystery"),
                List.of("Horror"),
                7.5, null));

        kieSession.insert(new MovieFact(2L, "Inception",
                List.of("mystery", "dream"),
                List.of("Mystery"),
                8.2, null));

        kieSession.getAgenda().getAgendaGroup("A1").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("A2").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("A3").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("A4").setFocus();
        kieSession.fireAllRules();

        List<Recommendation> recommendations = kieSession.getObjects().stream()
                .filter(Recommendation.class::isInstance)
                .map(Recommendation.class::cast)
                .collect(Collectors.toList());

        Recommendation inception = recommendations.stream()
                .filter(r -> r.getMovieId().equals(2L))
                .findFirst()
                .orElse(null);

        assertNotNull(inception);
        assertTrue(inception.getScore() >= 3.0);

        System.out.println("Inception score with watchlist boost: " +
                inception.getScore());
    }
}