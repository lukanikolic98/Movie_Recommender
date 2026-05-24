package com.ftn.sbnz.kjar.model.facts;

import java.io.Serializable;

public class LikeFact implements Serializable {
  private Long userId;
  private Long movieId;

  public LikeFact() {
  }

  public LikeFact(Long userId, Long movieId) {
    this.userId = userId;
    this.movieId = movieId;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getMovieId() {
    return movieId;
  }

  @Override
  public String toString() {
    return "LikeFact{" +
        "userId=" + userId +
        ", movieId=" + movieId +
        '}';
  }
}
