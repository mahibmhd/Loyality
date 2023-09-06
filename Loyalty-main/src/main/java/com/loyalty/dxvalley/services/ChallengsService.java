package com.loyalty.dxvalley.services;

import java.util.List;
import com.loyalty.dxvalley.models.Challenge;


public interface ChallengsService {
    List<Challenge> getChallenges ();
    Challenge getChallengeById( Long challengeeId);
    Challenge addChallenge(Challenge challenge);
    Challenge editChallenge(Challenge challenge);

}
