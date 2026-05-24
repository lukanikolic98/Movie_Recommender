package com.ftn.sbnz.service.dto;

import com.ftn.sbnz.model.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

  private Long id;
  private String title;
  private String description;
  private String director;
  private String posterurl;
  private Boolean liked;
  private Boolean disliked;

  private Set<String> categories;

  private Set<String> keywords;

  private int reviewCount;
  private double averageRating;

}
