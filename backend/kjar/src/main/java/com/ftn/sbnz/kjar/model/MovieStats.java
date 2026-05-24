package com.ftn.sbnz.kjar.model;

import java.io.Serializable;

public class MovieStats implements Serializable {
  private Long userId;
  private Long movieId;
  private double score;

  public MovieStats() {
  }

  public MovieStats(Long userId, Long movieId, double score) {
    this.userId = userId;
    this.movieId = movieId;
    this.score = score;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getMovieId() {
    return movieId;
  }

  public double getScore() {
    return score;
  }

  @Override
  public String toString() {
    return "MovieStats{" +
        "userId=" + userId +
        ", movieId=" + movieId +
        ", score=" + score +
        '}';
  }
}
