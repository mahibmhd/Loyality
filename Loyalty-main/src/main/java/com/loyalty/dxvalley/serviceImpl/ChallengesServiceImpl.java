package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.Challenge;
import com.loyalty.dxvalley.repositories.ChallengeRepositoty;
import com.loyalty.dxvalley.services.ChallengsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChallengesServiceImpl implements ChallengsService{
    @Autowired
    private final ChallengeRepositoty challengeRepositoty;
    @Override
    public List<Challenge> getChallenges() {
        return challengeRepositoty.findAll();
    }
    @Override
    public Challenge getChallengeById(Long challengeId) {
       
        return challengeRepositoty.findByChallengeId(challengeId);
    }
    @Override
    public Challenge addChallenge(Challenge challenge) {
        return challengeRepositoty.save(challenge);
    }
    @Override
    public Challenge editChallenge(Challenge challenge) {
        return challengeRepositoty.save(challenge);
    }
    
}
