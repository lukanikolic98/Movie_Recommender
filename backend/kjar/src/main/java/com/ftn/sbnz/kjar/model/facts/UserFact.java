package com.ftn.sbnz.kjar.model.facts;

import java.io.Serializable;
import java.util.Set;

public class UserFact implements Serializable {
  private Long id;
  private String firstName;
  private String lastName;
  private Set<Long> likedMovieIds;
  private Set<Long> dislikedMovieIds;
  private Set<Long> watchedMovieIds;
  private Set<Long> watchlistedMovieIds;
  private Set<String> preferredKeywords;
  private Set<String> preferredGenres;

  public UserFact() {
  }

  public UserFact(Long id, String firstName, String lastName,
      Set<Long> likedMovieIds, Set<Long> dislikedMovieIds,
      Set<Long> watchedMovieIds, Set<Long> watchlistedMovieIds,
      Set<String> preferredKeywords, Set<String> preferredGenres) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.likedMovieIds = likedMovieIds;
    this.dislikedMovieIds = dislikedMovieIds;
    this.watchedMovieIds = watchedMovieIds;
    this.watchlistedMovieIds = watchlistedMovieIds;
    this.preferredKeywords = preferredKeywords;
    this.preferredGenres = preferredGenres;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Set<Long> getLikedMovieIds() {
    return likedMovieIds;
  }

  public Set<Long> getDislikedMovieIds() {
    return dislikedMovieIds;
  }

  public Set<Long> getWatchedMovieIds() {
    return watchedMovieIds;
  }

  public Set<Long> getWatchlistedMovieIds() {
    return watchlistedMovieIds;
  }

  public Set<String> getPreferredKeywords() {
    return preferredKeywords;
  }

  public Set<String> getPreferredGenres() {
    return preferredGenres;
  }

  @Override
  public String toString() {
    return "UserFact{\n" +
        "  id=" + id + "\n" +
        "  name='" + firstName + " " + lastName + "'\n" +
        "  likedMovieIds=" + likedMovieIds + "\n" +
        "  dislikedMovieIds=" + dislikedMovieIds + "\n" +
        "  watchedMovieIds=" + watchedMovieIds + "\n" +
        "  watchlistedMovieIds=" + watchlistedMovieIds + "\n" +
        "  preferredKeywords=" + preferredKeywords + "\n" +
        "  preferredGenres=" + preferredGenres + "\n" +
        '}';
  }
}