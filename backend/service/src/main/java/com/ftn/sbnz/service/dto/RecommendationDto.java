package com.ftn.sbnz.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDto {
    private List<RecommendedMovieDto> movies;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecommendedMovieDto {
        private Long movieId;
        private String title;
        private double score;
    }
}