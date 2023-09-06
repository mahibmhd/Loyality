package com.loyalty.dxvalley.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loyalty.dxvalley.models.AdminDashboardDTO;
import com.loyalty.dxvalley.models.TopFiveDTO;
import com.loyalty.dxvalley.models.Transactionss;
import com.loyalty.dxvalley.models.UserChallenge;
import com.loyalty.dxvalley.models.Users;
import com.loyalty.dxvalley.repositories.UserRepository;
import com.loyalty.dxvalley.services.TransactionsService;
import com.loyalty.dxvalley.services.UserChallengsService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/dashboardData")
@RestController
@RequiredArgsConstructor
public class AdminDashboardController {
    @Autowired
    private final UserChallengsService userChallengsService;
    private final TransactionsService transactionsService;
    private final UserRepository userRepository;
    Double totalPoints = 0.0;
    Double totalTransactionAmount = 0.0;
    Integer totalUsers = 0;
    double userpoints = 0.0;

    @GetMapping("/getCounts")
    AdminDashboardDTO getCounts() {
        totalPoints = 0.0;
        totalTransactionAmount = 0.0;
        totalUsers = 0;

        ArrayList<Long> userIds = new ArrayList<>();
        List<UserChallenge> userChallenges = userChallengsService.getAll();
        List<Users> users = userRepository.findAll();
        userChallenges.stream().forEach(uc -> {
            totalPoints += uc.getPoints();
            if (uc.getPoints() > 0 && uc.getPoints() != null) {
                userIds.add(uc.getUsers().getUserId());
            }

        });
        List<Transactionss> transactionsses = transactionsService.getAll();
        transactionsses.stream().forEach(t -> {
            totalTransactionAmount += t.getAmount();
        });
        users.stream().forEach(u -> {
            // System.out.println(u.getRoles());
            u.getRoles().stream().forEach(r -> {
                if (r.getRoleName().equals("loyaltyAppUser")) {
                    totalUsers += 1;
                }
            });
        });
        AdminDashboardDTO adminDashboardDTO = new AdminDashboardDTO();
        adminDashboardDTO.setTotalPoints(totalPoints);
        adminDashboardDTO.setTotalTransactionAmount(totalTransactionAmount);
        adminDashboardDTO.setTotalTransactionCount(transactionsses.size());
        HashSet<Long> uniqueUserIds = new HashSet<>(userIds);

        // Clear the original ArrayList and add the unique IDs back
        userIds.clear();
        userIds.addAll(uniqueUserIds);
        adminDashboardDTO.setTotalActiveUsers(uniqueUserIds.size());
        adminDashboardDTO.setTotalUsers(totalUsers);

        return adminDashboardDTO;

    }

    @GetMapping("/getTopFive")
    List<TopFiveDTO> getTopFive() {
        ArrayList<Long> userIds = new ArrayList<>();
        ArrayList<Double> userPointsArray = new ArrayList<>();
        List<UserChallenge> userChallenges = userChallengsService.getAll();
        userChallenges.stream().forEach(uc -> {
            if (uc.getPoints() > 0 && uc.getPoints() != null) {
                userIds.add(uc.getUsers().getUserId());
            }

        });
        HashSet<Long> uniqueUserIds = new HashSet<>(userIds);
        // Clear the original ArrayList and add the unique IDs back
        userIds.clear();
        userIds.addAll(uniqueUserIds);
        userIds.stream().forEach(u -> {
            Users users = userRepository.findByUserId(u);
            userpoints = 0.0;
            List<UserChallenge> challenges = userChallengsService.getUserChallengesByuser(users);
            challenges.stream().forEach(c -> {
                userpoints += c.getPoints();
            });
            userPointsArray.add(userpoints);
        });
        ArrayList<IndexedValue> indexedValues = new ArrayList<>();

        // Step 1: Create instances of IndexedValue containing value and index
        for (int i = 0; i < userPointsArray.size(); i++) {
            indexedValues.add(new IndexedValue(userPointsArray.get(i), i));
        }

        // Step 2: Sort the indexedValues based on value (descending order)
        Collections.sort(indexedValues, Comparator.comparing(IndexedValue::getValue).reversed());

        // Step 3: Get the indexes of the top five instances
        ArrayList<Integer> topFiveIndexes = new ArrayList<>();
        for (int i = 0; i < Math.min(5, indexedValues.size()); i++) {
            topFiveIndexes.add(indexedValues.get(i).getIndex());
        }

        // Print the indexes of the top five values
        System.out.println("Indexes of the top five values: " + topFiveIndexes);
        List<TopFiveDTO> topFiveDTOs= new ArrayList<TopFiveDTO>();
        topFiveIndexes.stream().forEach(t->{
            TopFiveDTO topFiveDTO= new TopFiveDTO();
            topFiveDTO.setFullName(userRepository.findByUserId(userIds.get(t)).getFullName());
            topFiveDTO.setTotalPoints(userPointsArray.get(t));
            topFiveDTO.setUsername(userRepository.findByUserId(userIds.get(t)).getUsername());
            topFiveDTOs.add(topFiveDTO);
        });
        return topFiveDTOs;
    }
    

}

class IndexedValue {
    private double value;
    private int index;

    public IndexedValue(double value, int index) {
        this.value = value;
        this.index = index;
    }

    public double getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}
