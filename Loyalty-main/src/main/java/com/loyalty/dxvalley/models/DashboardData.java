package com.loyalty.dxvalley.models;



import java.util.List;

import lombok.Data;

 @Data
public class DashboardData {
   private String totalPoints;
   private String equivalentETB;
   private String levelName;
   private String levelColor;
   private List<LevelDetails> levelDetails;
   private List<UserChallengeDTO> userChallengeDTOs;
}
