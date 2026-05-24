package com.ftn.sbnz.kjar.model.facts;

import java.io.Serializable;

public class ReviewFact implements Serializable {

  private Long movieId;
  private Long userId;
  private double rating;

  public ReviewFact() {
  }

  public ReviewFact(Long movieId, Long userId, double rating, String comment) {
    this.movieId = movieId;
    this.userId = userId;
    this.rating = rating;
  }

  public Long getMovieId() {
    return movieId;
  }

  public void setMovieId(Long movieId) {
    this.movieId = movieId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  @Override
  public String toString() {
    return "ReviewFact{" +
        "movieId=" + movieId +
        ", userId=" + userId +
        ", rating=" + rating +
        ", comment='" + '\'' +
        '}';
  }
}
