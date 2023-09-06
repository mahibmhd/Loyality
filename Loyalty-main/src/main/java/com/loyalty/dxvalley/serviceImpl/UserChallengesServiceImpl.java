package com.loyalty.dxvalley.serviceImpl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.loyalty.dxvalley.models.DashboardData;
import com.loyalty.dxvalley.models.LevelDetails;
import com.loyalty.dxvalley.models.Levelss;
import com.loyalty.dxvalley.models.UserChallenge;
import com.loyalty.dxvalley.models.UserChallengeDTO;
import com.loyalty.dxvalley.models.Users;
import com.loyalty.dxvalley.repositories.LevelssRepository;
import com.loyalty.dxvalley.repositories.UserChallengeRepository;
import com.loyalty.dxvalley.repositories.UserRepository;
import com.loyalty.dxvalley.services.SettingsService;
import com.loyalty.dxvalley.services.UserChallengsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserChallengesServiceImpl implements UserChallengsService {
    @Autowired
    private final UserChallengeRepository userChallengeRepository;
    private final UserRepository userRepository;
    private final LevelssRepository levelssRepository;
    private final SettingsService settingsService;
    Double points = 0.0;
    String level = "0";
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public DashboardData getUserChallengesByUsername(String username) {
        Users users = userRepository.findByUsername(username);
        List<UserChallenge> userChallenges = userChallengeRepository.findUserChallengeByUsers(users);
        DashboardData dashboardData = new DashboardData();

        List<UserChallengeDTO> userChallengeDTOs = new ArrayList<UserChallengeDTO>();
        points = 0.0;

        userChallenges.stream().forEach(uc -> {
            System.out.println(uc.getChallenge().getChallengeName());
            UserChallengeDTO userChallengeDTO = new UserChallengeDTO();
            userChallengeDTO.setChallengeName(uc.getChallenge().getChallengeName());
            File imageFile = new File(uploadDir,
                    uc.getChallenge().getProductCataloge().getLogoMetadata().getFileName());
            if (imageFile.exists() && imageFile.isFile()) {
                String encodedFileName = UriUtils.encode(
                        uc.getChallenge().getProductCataloge().getLogoMetadata().getFileName(), StandardCharsets.UTF_8);
                String imageUrl = "http://10.1.177.123:9000/api/images/" + encodedFileName;
                userChallengeDTO.setChallengeLogo(imageUrl);
            }

            userChallengeDTO
                    .setAffliateLink(uc.getAffliateLink());
            userChallengeDTO.setPointsEarned(uc.getPoints().toString());
            userChallengeDTO.setAwardPoints(uc.getChallenge().getPoints().toString());
            points += uc.getPoints();
            userChallengeDTOs.add(userChallengeDTO);
        });
        dashboardData.setUserChallengeDTOs(userChallengeDTOs);
        dashboardData.setTotalPoints(points.toString());
        Double rate = settingsService.getSettings().get(0).getExchangeRate();
        Double result = (1 * points) / rate;
        dashboardData.setEquivalentETB(result.toString());
        List<Levelss> levelsses = levelssRepository.findAll();
        List<LevelDetails> levelDetailsArray = new ArrayList<LevelDetails>();
        List<Levelss> sortedLevelsses = levelsses.stream()
                .sorted(Comparator.comparing(Levelss::getMaxValue))
                .collect(Collectors.toList());
        sortedLevelsses.stream().forEach(l -> {
            LevelDetails levelDetails = new LevelDetails();
            if (points < l.getMinValue()) {
                levelDetails.setStatus("0");
                //   dashboardData.setLevelColor(l.getColour());
                //   dashboardData.setLevelName(l.getLevelName());
            } else if (l.getMinValue() <= points && points < l.getMaxValue()) {
                levelDetails.setStatus("1");
                dashboardData.setLevelColor(l.getColour());
                  dashboardData.setLevelName(l.getLevelName());
            }

            else if (points >= l.getMaxValue()) {
                levelDetails.setStatus("2");
                dashboardData.setLevelColor(l.getColour());
                  dashboardData.setLevelName(l.getLevelName());
            }
            levelDetails.setLevelName(l.getLevelName());
            levelDetails.setPoints(l.getMaxValue().toString());
            levelDetailsArray.add(levelDetails);
        });
      

        dashboardData.setLevelDetails(levelDetailsArray);
        return dashboardData;
    }

    @Override
    public List<UserChallenge> getAll() {
        return userChallengeRepository.findAll();
    }

    @Override
    public List<UserChallenge> getUserChallengesByuser(Users users) {
        return userChallengeRepository.findUserChallengeByUsers(users);
    }

    @Override
    public UserChallenge addUserChallenge(UserChallenge userChallenge) {
        return userChallengeRepository.save(userChallenge);
    }

}