package com.ftn.sbnz.kjar.model;

import java.io.Serializable;

public class DislikedKeyword implements Serializable {
  private Long userId;
  private String keyword;
  private double rating;

  public DislikedKeyword() {
  }

  public DislikedKeyword(Long userId, String keyword, double rating) {
    this.userId = userId;
    this.keyword = keyword;
    this.rating = rating;
  }

  public Long getUserId() {
    return userId;
  }

  public String getKeyword() {
    return keyword;
  }

  public double getRating() {
    return rating;
  }

  @Override
  public String toString() {
    return "DislikedKeyword{" +
        "userId=" + userId +
        ", keyword='" + keyword + '\'' +
        ", rating=" + rating +
        '}';
  }
}
