package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.Levelss;

public interface LevelssRepository extends JpaRepository<Levelss, Long> {
   Levelss findByLevelId (Long levelId); 
   
    
}
