package com.ftn.sbnz.kjar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recommendation implements Serializable {
  private Long userId;
  private List<MovieStats> recommended = new ArrayList<>();

  public Recommendation() {
  }

  public Recommendation(Long userId) {
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }

  public List<MovieStats> getRecommended() {
    return recommended;
  }

  public void add(MovieStats stat) {
    recommended.add(stat);
  }
}
