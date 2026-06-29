package com.ftn.sbnz.kjar.model.level_4;

import java.io.Serializable;

public class Recommendation implements Serializable {

  private Long userId;
  private Long movieId;
  private String title;
  private double score;

  private boolean keywordBoosted;
  private boolean genreBoosted;
  private boolean watchlistBoosted;

  public Recommendation() {
  }

  public Recommendation(Long userId, Long movieId, String title, double score) {
    this.userId = userId;
    this.movieId = movieId;
    this.title = title;
    this.score = score;

    this.keywordBoosted = false;
    this.genreBoosted = false;
    this.watchlistBoosted = false;
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

  public boolean isKeywordBoosted() {
    return keywordBoosted;
  }

  public void setKeywordBoosted(boolean keywordBoosted) {
    this.keywordBoosted = keywordBoosted;
  }

  public boolean isGenreBoosted() {
    return genreBoosted;
  }

  public void setGenreBoosted(boolean genreBoosted) {
    this.genreBoosted = genreBoosted;
  }

  public boolean isWatchlistBoosted() {
    return watchlistBoosted;
  }

  public void setWatchlistBoosted(boolean watchlistBoosted) {
    this.watchlistBoosted = watchlistBoosted;
  }
}