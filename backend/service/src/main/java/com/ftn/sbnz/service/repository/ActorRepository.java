package com.ftn.sbnz.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long>{
    
}
