package com.ftn.sbnz.service.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final MovieRepository      movieRepository;
    private final GenreRepository      genreRepository;
    private final KeywordRepository    keywordRepository;
    private final ActorRepository      actorRepository;
    private final DirectorRepository   directorRepository;

    @Value("${app.seed.movies-csv}")
    private Resource moviesCsv;

    @Value("${app.seed.credits-csv}")
    private Resource creditsCsv;

    // How many top-billed cast members to import per movie
    private static final int MAX_CAST = 10;

    private final ObjectMapper mapper = new ObjectMapper();

    // In-memory caches so we don't hit the DB for every lookup
    private final Map<String, Genre>    genreCache    = new HashMap<>();
    private final Map<String, Keyword>  keywordCache  = new HashMap<>();
    private final Map<String, Actor>    actorCache    = new HashMap<>();
    private final Map<String, Director> directorCache = new HashMap<>();

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (movieRepository.count() > 0) {
            log.info("Database already seeded — skipping.");
            return;
        }

        log.info("Seeding database from CSV files …");

        // ── 1. load credits into memory keyed by tmdb movie id ────────────────
        Map<Integer, CreditData> credits = loadCredits();

        // ── 2. parse and insert movies ────────────────────────────────────────
        try (
            InputStreamReader reader = new InputStreamReader(moviesCsv.getInputStream());
            CSVParser csv = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)

        ) {
            int count = 0;
            for (CSVRecord row : csv) {
                try {
                    insertMovie(row, credits);
                    count++;
                    if (count % 100 == 0) log.info("  {} movies seeded …", count);
                } catch (Exception e) {
                    log.warn("Skipping row '{}': {}", row.get("title"), e.getMessage());
                }
            }
            log.info("Seeding complete — {} movies inserted.", count);
        }
    }

    private void insertMovie(CSVRecord row, Map<Integer, CreditData> credits) throws Exception {
        int tmdbId = Integer.parseInt(row.get("id"));

        Movie movie = new Movie();
        movie.setTitle(row.get("title"));
        movie.setOverview(row.get("overview"));
        movie.setLanguage(row.get("original_language"));
        movie.setPopularity(parseDouble(row.get("popularity")));
        movie.setTmdbVoteAverage(parseDouble(row.get("vote_average")));
        movie.setTmdbVoteCount(parseInt(row.get("vote_count")));
        movie.setRuntime(parseInt(row.get("runtime")));
        movie.setReleaseDate(parseDate(row.get("release_date")));
        movie.setPosterUrl(row.get("poster_path"));

        // ── director ──────────────────────────────────────────────────────────
        CreditData credit = credits.getOrDefault(tmdbId, new CreditData());
        credit.crew.stream()
            .filter(c -> "Director".equals(c.get("job")))
            .findFirst()
            .ifPresent(c -> {
                String name = (String) c.get("name");
                movie.setDirector(directorCache.computeIfAbsent(name, n -> {
                    Director d = new Director();
                    d.setName(n);
                    return directorRepository.save(d);
                }));
            });

        // ── genres ────────────────────────────────────────────────────────────
        List<Map<String, Object>> genres = parseJsonList(row.get("genres"));
        for (Map<String, Object> g : genres) {
            String name = (String) g.get("name");
            movie.getGenres().add(genreCache.computeIfAbsent(name, n -> {
                Genre genre = new Genre();
                genre.setName(n);
                return genreRepository.save(genre);
            }));
        }

        // ── keywords ──────────────────────────────────────────────────────────
        List<Map<String, Object>> keywords = parseJsonList(row.get("keywords"));
        for (Map<String, Object> k : keywords) {
            String name = (String) k.get("name");
            movie.getKeywords().add(keywordCache.computeIfAbsent(name, n -> {
                Keyword kw = new Keyword();
                kw.setName(n);
                return keywordRepository.save(kw);
            }));
        }

        // ── actors (top MAX_CAST billed) ──────────────────────────────────────
        credit.cast.stream()
            .sorted(Comparator.comparingInt(c -> ((Number) c.getOrDefault("order", 999)).intValue()))
            .limit(MAX_CAST)
            .forEach(c -> {
                String name = (String) c.get("name");
                movie.getActors().add(actorCache.computeIfAbsent(name, n -> {
                    Actor a = new Actor();
                    a.setName(n);
                    return actorRepository.save(a);
                }));
            });

        movieRepository.save(movie);
    }

    // ── credits loader ────────────────────────────────────────────────────────
    private Map<Integer, CreditData> loadCredits() throws Exception {
        Map<Integer, CreditData> map = new HashMap<>();
        try (
            InputStreamReader reader = new InputStreamReader(creditsCsv.getInputStream());
            CSVParser csv = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)

        ) {
            for (CSVRecord row : csv) {
                int id = Integer.parseInt(row.get("movie_id"));
                CreditData data = new CreditData();
                data.cast = parseJsonList(row.get("cast"));
                data.crew = parseJsonList(row.get("crew"));
                map.put(id, data);
            }
        }
        return map;
    }

    // ── parsing helpers ───────────────────────────────────────────────────────
    private List<Map<String, Object>> parseJsonList(String json) {
        try {
            return mapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private Double parseDouble(String s) {
        try { return s == null || s.isBlank() ? null : Double.parseDouble(s); }
        catch (Exception e) { return null; }
    }

    private Integer parseInt(String s) {
        try { return s == null || s.isBlank() ? null : (int) Double.parseDouble(s); }
        catch (Exception e) { return null; }
    }

    private LocalDate parseDate(String s) {
        try { return s == null || s.isBlank() ? null : LocalDate.parse(s); }
        catch (Exception e) { return null; }
    }

    // ── simple holder for credits data ────────────────────────────────────────
    private static class CreditData {
        List<Map<String, Object>> cast = new ArrayList<>();
        List<Map<String, Object>> crew = new ArrayList<>();
    }
}