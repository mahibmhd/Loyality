package com.loyalty.dxvalley.models;

import lombok.Data;

@Data
public class AdminDashboardDTO {
    private Double totalPoints;
    private Double totalTransactionAmount;
    private Integer totalTransactionCount;
    private Integer totalActiveUsers;
    private Integer totalUsers;
}
