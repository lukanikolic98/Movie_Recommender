package com.ftn.sbnz.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private Long id;
    private String title;
    private String overview;
    private String director;
    private String posterUrl;
    private String language;
    private LocalDate releaseDate;
    private Integer runtime;

    // user-specific status (null if not logged in or no interaction yet)
    private Boolean liked;       // true=liked, false=disliked, null=no reaction
    private Boolean watchlisted;
    private Boolean watched;

    // genres and keywords as plain strings for the frontend
    private Set<String> genres;
    private Set<String> keywords;
    private Set<String> actors;

    // ratings
    private Double reviewAverage;
    private Double tmdbVoteAverage;
    private Integer tmdbVoteCount;
    private int reviewCount;
}