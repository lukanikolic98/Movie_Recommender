package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

//   @Query("SELECT m FROM Movie m JOIN m.categories c " +
//       "WHERE c.name = :categoryName AND :keyword MEMBER OF m.keywords")
//   List<Movie> findByCategoryAndKeyword(@Param("categoryName") String categoryName,
//       @Param("keyword") String keyword);

//   @Query("SELECT m FROM Movie m JOIN m.categories c " +
//       "WHERE c.name = :categoryName OR :keyword MEMBER OF m.keywords")
//   List<Movie> findByCategoryOrKeyword(@Param("categoryName") String categoryName,
//       @Param("keyword") String keyword);

//   @Query("SELECT m FROM Movie m JOIN m.categories c " +
//       "WHERE c.name = :categoryName OR :keyword MEMBER OF m.keywords OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
//   List<Movie> findByCategoryOrKeywordOrTitle(@Param("categoryName") String categoryName,
//       @Param("keyword") String keyword, @Param("title") String title);

//   @Query("SELECT m FROM Movie m WHERE :keyword MEMBER OF m.keywords")
//   List<Movie> findByKeyword(@Param("keyword") String keyword);

//   @Query("SELECT m FROM Movie m JOIN m.categories c WHERE c.name = :categoryName")
//   List<Movie> findByCategory(@Param("categoryName") String categoryName);

    @Query("""
        SELECT DISTINCT m FROM Movie m
        LEFT JOIN m.genres g
        LEFT JOIN m.keywords k
        WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :term, '%'))
        OR LOWER(g.name)     LIKE LOWER(CONCAT('%', :term, '%'))
        OR LOWER(k.name)     LIKE LOWER(CONCAT('%', :term, '%'))
    """)
    List<Movie> search(@Param("term") String term);
    List<Movie> findAllByDeletedFalse();

    Optional<Movie> findByIdAndDeletedFalse(Long id);
}
