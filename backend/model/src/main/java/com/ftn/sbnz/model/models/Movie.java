package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "movies")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(name = "posterurl")
  private String posterurl;

  @Column(name = "director")
  private String director;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean deleted = false;

  @ManyToMany
  @JoinTable(name = "movie_categories", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "movie_keywords", joinColumns = @JoinColumn(name = "movie_id"))
  @Column(name = "keyword")
  private Set<String> keywords = new HashSet<>();

  // Reviews for this movie
  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  // Bidirectional Many-to-Many
  @ManyToMany(mappedBy = "watched")
  private List<User> watchedBy = new ArrayList<>();

  @ManyToMany(mappedBy = "liked")
  private List<User> likedBy = new ArrayList<>();

  @ManyToMany(mappedBy = "disliked")
  private List<User> dislikedBy = new ArrayList<>();

  @ManyToMany(mappedBy = "watchlist")
  private List<User> onWatchlistOf = new ArrayList<>();

}
