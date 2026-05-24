package com.ftn.sbnz.kjar.model;

public class KeywordStats {
  private int userId;
  private String keyword;
  private int count;
  private double averageRating;

  public KeywordStats() {
  } // no-arg constructor required by Drools

  public KeywordStats(int userId, String keyword, int count, double averageRating) {
    this.userId = userId;
    this.keyword = keyword;
    this.count = count;
    this.averageRating = averageRating;
  }

  // Getters and setters
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public double getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(double averageRating) {
    this.averageRating = averageRating;
  }

  @Override
  public String toString() {
    return "KeywordStats{" +
        "userId=" + userId +
        ", keyword='" + keyword + '\'' +
        ", count=" + count +
        ", averageRating=" + averageRating +
        '}';
  }
}
