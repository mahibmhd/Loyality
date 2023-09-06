package com.loyalty.dxvalley.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loyalty.dxvalley.models.DashboardData;
import com.loyalty.dxvalley.services.UserChallengsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userChallenges")
public class UserChallengsController {
    @Autowired
    private final UserChallengsService userChallengsService;
  @GetMapping("/getByUsername/{username}")
  DashboardData getAll(@PathVariable String username) {
   return userChallengsService.getUserChallengesByUsername(username);
  }
}


