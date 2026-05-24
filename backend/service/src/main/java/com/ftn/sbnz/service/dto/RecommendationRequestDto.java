package com.ftn.sbnz.service.dto;

import java.util.List;
import lombok.Data;

@Data
public class RecommendationRequestDto {
  private List<String> keywords;
  private List<String> categories;
}
