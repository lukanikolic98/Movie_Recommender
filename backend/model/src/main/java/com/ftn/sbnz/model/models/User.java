package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(name = "firstname")
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Column(name = "activation_token")
  private String activationToken;
  private boolean activated;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Review> reviews;

  // Watched movies
  @ManyToMany
  @JoinTable(name = "user_watched", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private List<Movie> watched = new ArrayList<>();

  // Liked movies
  @ManyToMany
  @JoinTable(name = "user_liked", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private List<Movie> liked = new ArrayList<>();

  // Disliked movies
  @ManyToMany
  @JoinTable(name = "user_disliked", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private List<Movie> disliked = new ArrayList<>();

  // Watchlist
  @ManyToMany
  @JoinTable(name = "user_watchlist", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private List<Movie> watchlist = new ArrayList<>();

}
