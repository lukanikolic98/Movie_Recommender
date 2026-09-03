# Movie Recommender

- SW64/2017 — Luka Nikolić

A movie database and recommendation app built for the Knowledge Based Systems (SBNZ) course at FTN Novi Sad. Recommendations aren't collaborative-filtering-style black boxes — they're produced by an explicit Drools rule engine that reasons over keywords, genres, and (optionally) a user's watch history, so every score is traceable back to the rules that produced it.

## Features

- **Browse & search** — filter the catalog by genre, minimum rating, release year, language, and sort order, with pagination and title search. Filter state lives in the URL, so results survive back-navigation.
- **Movie details** — poster, overview, director, top cast, genres, keywords, TMDB rating alongside the community review average.
- **Personal status tracking** — like/dislike, watchlist, and watched, per user per movie.
- **Reviews** — 1–10 rating plus comment, one review per user per movie, factored into both the movie's review average and the recommendation engine.
- **Recommendations** — pick keywords (typed or chosen from popularity-ranked suggestions) and/or genres, optionally letting watch history influence the result, and get back a scored, ranked list of movies.
- **Popular keyword suggestions** — surfaced on the recommendations page, computed by running the same rule engine over every user's likes.
- **Auth** — JWT access/refresh tokens, registration, profile editing, password change.

## Tech Stack

**Backend** — Java, Spring Boot, Spring Security (JWT), Spring Data JPA, [Drools / KIE](https://www.drools.org/) rule engine.

**Frontend** — Angular (standalone components, signals-based state), Angular Material (M3 theming), RxJS.

## Architecture

### Backend modules

The `com.ftn.sbnz.*` package layout reflects a multi-module structure typical of Drools-based Spring Boot projects:

| Module | Contains |
|---|---|
| `model` | JPA entities (`Movie`, `User`, `Genre`, `Keyword`, `Actor`, `Review`, `UserMovieStatus`, …) |
| `kjar` | Drools rules and the fact model they operate on (`facts.MovieFact`, `facts.UserFact`, `facts.ReviewFact`, and the `level_1`–`level_4` fact types produced as rules fire) |
| `service` | The Spring Boot REST API — controllers, services, repositories, DTOs |

### Recommendation engine

Rules are grouped into four agenda groups, fired in order **A1 → A2 → A3 → A4**, each consuming facts produced by the one before it:

| Layer | Produces | What it does |
|---|---|---|
| A1 | `KeywordInteraction` | Turns a user's likes/dislikes/reviews into per-keyword interaction facts |
| A2 | `KeywordStats` | Accumulates interactions into a weight per keyword: `(likes - dislikes) * 5 + (reviewAvg - 5) * reviewCount` |
| A3 | `MovieStats` | Filters out watched movies; scores every remaining movie by summing the weights of its matching keywords |
| A4 | `Recommendation` | Wraps each `MovieStats` into a final scored recommendation, with `+10` per matching preferred genre and `+3` if watchlisted |

Two entry points reuse this pipeline differently:

- **Recommendations, with history on** — all four layers fire from the user's real like/dislike/review data.
- **Recommendations, with explicit keywords and no history** — A1/A2 are skipped; each entered keyword is inserted directly as a synthetic `KeywordStats` (`likes=5`, giving it a weight of 25), then only A3/A4 fire.
- **Popular keyword suggestions** — every user's liked movies are inserted as facts, only A1/A2 fire, and the resulting per-user `KeywordStats` are summed by keyword across all users and ranked.

The full write-up, with a worked numeric example for both recommendation modes, is in [Project Proposal](Documentation/Project%20proposal.pdf).

### Frontend structure

```
src/app/
├── core/
│   ├── models/        # MovieDto, ReviewDto, RecommendationDto, ...
│   ├── services/       # MoviesService, ReviewsService, RecommendationsService, AuthService
│   ├── constants/       # genre/language/sort option lists
│   └── guards/          # authGuard
├── features/
│   ├── home/
│   ├── browse/
│   ├── movie-details/
│   ├── movie-recommendation/
│   ├── auth/            # login, register
│   └── profile/
└── shared/
    └── utils/            # tmdbImageUrl, initialsFromName
```

## Getting Started

### Prerequisites

- JDK 17+
- Maven
- Node.js 22+
- npm

### Backend

```bash
# from the parent/aggregator module
mvn clean install

.\mvnw spring-boot:run
```

API served at `http://localhost:8080` by default (Spring Boot's default port).

### Frontend

```bash
cd movie-recommender
npm install
ng serve
```

App served at `http://localhost:4200`. Confirm `environment.development.ts`'s `apiUrl` points at the backend above.

## API Reference

Not exhaustive — see the controllers for full detail.

**Auth** (`/auth`, no `/api` prefix)

| Method | Path | Notes |
|---|---|---|
| POST | `/auth/login` | |
| POST | `/auth/register` | |
| GET | `/auth/me` | requires auth |
| POST | `/auth/refresh` | |
| PUT | `/auth/profile` | requires auth |
| PUT | `/auth/change-password` | requires auth |

**Movies** (`/api/movies`)

| Method | Path | Notes |
|---|---|---|
| GET | `/api/movies` | all movies |
| GET | `/api/movies/{id}` | |
| GET | `/api/movies/search?term=` | flat, unpaginated |
| GET | `/api/movies/browse?term=&genre=&minRating=&year=&language=&sortBy=&page=&pageSize=` | filtered, sorted, paginated |
| POST | `/api/movies/{id}/like` \| `/dislike` \| `/watchlist` \| `/watched` | requires auth |
| DELETE | `/api/movies/{id}` | admin only |

**Reviews** (`/api/movies/{movieId}/reviews`)

| Method | Path | Notes |
|---|---|---|
| GET | `/api/movies/{movieId}/reviews` | |
| POST | `/api/movies/{movieId}/reviews` | requires auth; rating 1–10; one per user per movie |
| DELETE | `/api/movies/{movieId}/reviews/{reviewId}` | requires auth; author only |

**Recommendations** (`/api/movies/recommendations`)

| Method | Path | Notes |
|---|---|---|
| POST | `/api/movies/recommendations` | body: `{ keywords, genres, useHistory }` — requires auth |
| GET | `/api/movies/recommendations/keywords/popular?limit=` | public |

## Documentation

[Project Proposal](Documentation/Project%20proposal.pdf) — the full project write-up (in Serbian): motivation, related work, the complete rule-engine breakdown, and worked examples for both recommendation modes.

## Academic Context

Built for the Knowledge Based Systems (Sistemi bazirani na znanju) course, Faculty of Technical Sciences, University of Novi Sad.
