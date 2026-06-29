package com.ftn.sbnz.kjar.model.level_2;

import java.io.Serializable;

public class KeywordStats implements Serializable {
  private Long userId;
  private String keyword;
  private int likes;
  private int dislikes;
  private double reviewAvg;
  private int reviewCount;

  public KeywordStats() {
  }

  public KeywordStats(Long userId, String keyword,
      int likes, int dislikes,
      double reviewAvg, int reviewCount) {
    this.userId = userId;
    this.keyword = keyword;
    this.likes = likes;
    this.dislikes = dislikes;
    this.reviewAvg = reviewAvg;
    this.reviewCount = reviewCount;
  }

  // net sentiment score for this keyword
  public double score() {
    double likeScore = (likes - dislikes) * 5.0;
    double reviewScore = reviewCount > 0 ? (reviewAvg - 5.0) * reviewCount : 0;
    return likeScore + reviewScore;
  }

  public Long getUserId() {
    return userId;
  }

  public String getKeyword() {
    return keyword;
  }

  public int getLikes() {
    return likes;
  }

  public int getDislikes() {
    return dislikes;
  }

  public double getReviewAvg() {
    return reviewAvg;
  }

  public int getReviewCount() {
    return reviewCount;
  }
}