package com.ftn.sbnz.service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Keyword;


public interface KeywordRepository extends JpaRepository<Keyword, Long>{
    
}
