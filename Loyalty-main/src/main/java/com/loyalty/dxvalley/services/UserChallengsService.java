package com.loyalty.dxvalley.services;

import java.util.List;

import com.loyalty.dxvalley.models.DashboardData;
import com.loyalty.dxvalley.models.UserChallenge;
import com.loyalty.dxvalley.models.Users;



public interface UserChallengsService {
    UserChallenge addUserChallenge(UserChallenge userChallenge);
    DashboardData getUserChallengesByUsername (String username);
    List<UserChallenge> getUserChallengesByuser(Users users);
    List<UserChallenge> getAll();
}
