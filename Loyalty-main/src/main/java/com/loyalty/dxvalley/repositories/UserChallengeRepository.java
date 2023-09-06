package com.loyalty.dxvalley.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.UserChallenge;
import com.loyalty.dxvalley.models.Users;

public interface UserChallengeRepository extends JpaRepository<UserChallenge,Long>{
    List<UserChallenge> findUserChallengeByUsers(Users users);
}
