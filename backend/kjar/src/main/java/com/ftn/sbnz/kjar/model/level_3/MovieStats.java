package com.ftn.sbnz.kjar.model.level_3;

import java.io.Serializable;

public class MovieStats implements Serializable {
  private Long userId;
  private Long movieId;
  private String title;
  private double score;

  public MovieStats() {
  }

  public MovieStats(Long userId, Long movieId, String title, double score) {
    this.userId = userId;
    this.movieId = movieId;
    this.title = title;
    this.score = score;
  }

  public void addScore(double delta) {
    this.score += delta;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getMovieId() {
    return movieId;
  }

  public String getTitle() {
    return title;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }
}