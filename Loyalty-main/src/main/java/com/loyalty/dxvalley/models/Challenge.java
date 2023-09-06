package com.loyalty.dxvalley.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Challenge {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeId;
    private String challengeName;
    private String description;
    private Double points;
    private Long maxPoints;
    private String icon;
    private Boolean isEnabled;

    @ManyToOne
    private Category category;

     @ManyToOne
    private ProductCataloge productCataloge;
}
