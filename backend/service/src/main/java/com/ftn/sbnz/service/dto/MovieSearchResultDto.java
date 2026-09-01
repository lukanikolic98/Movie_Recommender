package com.ftn.sbnz.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearchResultDto {
    private List<MovieDto> movies;
    private int page;
    private int totalPages;
    private long totalResults;
}