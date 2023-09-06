package com.loyalty.dxvalley.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.loyalty.dxvalley.models.Challenge;
import com.loyalty.dxvalley.models.CreateResponse;
import com.loyalty.dxvalley.models.UserChallenge;
import com.loyalty.dxvalley.repositories.UserRepository;
import com.loyalty.dxvalley.services.ChallengsService;
import com.loyalty.dxvalley.services.UserChallengsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challengs")
public class ChallengsController {

  @Autowired
  private final ChallengsService challengsService;
  private final UserRepository userRepository;
  private final UserChallengsService userChallengsService;

    @PostMapping("/addChallenge")
    public ResponseEntity<CreateResponse> addChallenge (@RequestBody Challenge challenge) {
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = new Date();
        Challenge newChallenge= challengsService.addChallenge(challenge);
        userRepository.findAll().stream().forEach(u->{
          UserChallenge userChallenge= new UserChallenge();
          userChallenge.setChallenge(newChallenge);
          userChallenge.setUsers(u);
          userChallenge.setIsEnabled(true);
          userChallenge.setJoinedAt(dateFormat.format(currentDate));
          userChallenge.setPoints(0.0);
          if(newChallenge.getProductCataloge().getPlaystoreLink()!=null)
          {
            userChallenge.setAffliateLink("https://play.google.com/store/apps/details?id="+newChallenge.getProductCataloge().getPlaystoreLink()+"&referrer=utm_content="+u.getUsername()+"&utm_source=coopLoyalityApp");
          }
          userChallengsService.addUserChallenge(userChallenge);
        });
        CreateResponse response = new CreateResponse("Success","Challenge created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 
    @PutMapping("/edit/{challengId}")
      Challenge editChallenge(@RequestBody Challenge tempChallenge, @PathVariable Long challengId) {
        Challenge challenge = this.challengsService.getChallengeById(challengId);
        challenge.setChallengeName(tempChallenge.getChallengeName());
        challenge.setDescription(tempChallenge.getDescription());
        challenge.setIcon(tempChallenge.getIcon()); 
        challenge.setIsEnabled(tempChallenge.getIsEnabled());
        challenge.setMaxPoints(tempChallenge.getMaxPoints());
        challenge.setPoints(tempChallenge.getPoints());
        challenge.setProductCataloge(tempChallenge.getProductCataloge());
        challenge.setCategory(tempChallenge.getCategory());
        return challengsService.editChallenge(challenge);
    }
  @GetMapping("/getAll")
  List<Challenge> getAll() {
   return challengsService.getChallenges();
  }

  @GetMapping("/{challengeId}")
  Challenge getChallenge(@PathVariable Long challengeId){
    return challengsService.getChallengeById(challengeId);
  }

}
