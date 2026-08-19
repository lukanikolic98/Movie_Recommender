package com.ftn.sbnz.kjar.model.facts;

import java.io.Serializable;
import java.util.List;

public class MovieFact implements Serializable {
  private Long id;
  private String title;
  private List<String> keywords;
  private List<String> genres;
  private Double tmdbVoteAverage;
  private Double reviewAverage;

  public MovieFact() {
  }

  public MovieFact(Long id, String title, List<String> keywords,
      List<String> genres, Double tmdbVoteAverage, Double reviewAverage) {
    this.id = id;
    this.title = title;
    this.keywords = keywords;
    this.genres = genres;
    this.tmdbVoteAverage = tmdbVoteAverage;
    this.reviewAverage = reviewAverage;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public List<String> getKeywords() {
    return keywords;
  }

  public List<String> getGenres() {
    return genres;
  }

  public Double getTmdbVoteAverage() {
    return tmdbVoteAverage;
  }

  public Double getReviewAverage() {
    return reviewAverage;
  }

  @Override
  public String toString() {
    return "MovieFact{\n" +
        "  id=" + id + "\n" +
        "  title='" + title + "'\n" +
        "  keywords=" + keywords + "\n" +
        "  genres=" + genres + "\n" +
        "  tmdbVoteAverage=" + tmdbVoteAverage + "\n" +
        "  reviewAverage=" + reviewAverage + "\n" +
        '}';
  }
}