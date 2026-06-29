package com.ftn.sbnz.kjar.model.facts;

import java.io.Serializable;

public class ReviewFact implements Serializable {
  private Long userId;
  private Long movieId;
  private int rating;

  public ReviewFact() {
  }

  public ReviewFact(Long userId, Long movieId, int rating) {
    this.userId = userId;
    this.movieId = movieId;
    this.rating = rating;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getMovieId() {
    return movieId;
  }

  public int getRating() {
    return rating;
  }
}