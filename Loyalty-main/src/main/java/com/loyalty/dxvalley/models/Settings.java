package com.loyalty.dxvalley.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Settings {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long settingId;
    private Double  exchangeRate;
    private Double withdrawalLimit;
}
