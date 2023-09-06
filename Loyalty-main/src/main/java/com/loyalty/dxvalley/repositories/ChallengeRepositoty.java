package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.Challenge;

public interface ChallengeRepositoty extends JpaRepository<Challenge,Long>{

   Challenge findByChallengeId(Long challengeId); 
}
