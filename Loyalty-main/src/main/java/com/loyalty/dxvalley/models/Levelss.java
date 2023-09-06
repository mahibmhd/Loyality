package com.loyalty.dxvalley.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Levelss {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long levelId;
    private String levelName;
    private String description;
    private Integer minValue;
    private Integer maxValue;
    private Boolean isEnabled;
    private String icon;
    private String colour;
}
