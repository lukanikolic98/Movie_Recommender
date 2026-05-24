package com.ftn.sbnz.kjar.model.facts;

import java.io.Serializable;
import java.util.List;

public class MovieFact implements Serializable {

  private Long movieId;
  private String title;
  private List<String> keywords;
  private double averageRating;
  private List<String> genres;

  public MovieFact() {
  }

  public MovieFact(Long movieId, String title, List<String> keywords, double averageRating, List<String> genres) {
    this.movieId = movieId;
    this.title = title;
    this.keywords = keywords;
    this.averageRating = averageRating;
    this.genres = genres;
  }

  public Long getMovieId() {
    return movieId;
  }

  public void setMovieId(Long movieId) {
    this.movieId = movieId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }

  public double getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(double averageRating) {
    this.averageRating = averageRating;
  }

  public List<String> getGenres() {
    return genres;
  }

  public void setGenre(List<String> genres) {
    this.genres = genres;
  }

  @Override
  public String toString() {
    return "MovieFact{" +
        "movieId=" + movieId +
        ", title='" + title + '\'' +
        ", keywords=" + keywords +
        ", averageRating=" + averageRating +
        ", genre='" + genres + '\'' +
        '}';
  }
}
