package com.ftn.sbnz.service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ftn.sbnz.model.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}