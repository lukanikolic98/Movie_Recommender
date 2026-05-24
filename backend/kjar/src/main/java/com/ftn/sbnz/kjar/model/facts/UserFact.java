package com.ftn.sbnz.kjar.model.facts;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserFact implements Serializable {

  private Long id;
  private String firstname;
  private String lastname;
  private Set<Long> likedMovieIds = new HashSet<>();
  private Set<Long> dislikedMovieIds = new HashSet<>();
  private Set<Long> watchedMovieIds = new HashSet<>();

  public UserFact() {
  }

  public UserFact(Long id, String firstname, String lastname,
      Set<Long> likedMovieIds, Set<Long> dislikedMovieIds, Set<Long> watchedMovieIds) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.likedMovieIds = likedMovieIds != null ? likedMovieIds : new HashSet<>();
    this.dislikedMovieIds = dislikedMovieIds != null ? dislikedMovieIds : new HashSet<>();
    this.watchedMovieIds = watchedMovieIds != null ? watchedMovieIds : new HashSet<>();
  }

  public Long getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
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

  @Override
  public String toString() {
    return "UserFact{" +
        "id=" + id +
        ", firstname='" + firstname + '\'' +
        ", likedMovies=" + likedMovieIds +
        ", dislikedMovies=" + dislikedMovieIds +
        ", watchedMovies=" + watchedMovieIds +
        '}';
  }
}
