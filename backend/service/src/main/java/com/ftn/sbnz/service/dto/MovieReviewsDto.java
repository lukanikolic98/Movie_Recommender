package com.ftn.sbnz.service.dto;

import java.util.List;

import com.ftn.sbnz.model.models.Movie;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewsDto {
  private List<ReviewDto> reviews;
}
