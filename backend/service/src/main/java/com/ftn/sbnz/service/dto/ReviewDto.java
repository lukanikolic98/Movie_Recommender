package com.ftn.sbnz.service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

  private Long id;
  private String comment;
  private int rating;
  private String userName;
  private LocalDateTime createdAt;
}
