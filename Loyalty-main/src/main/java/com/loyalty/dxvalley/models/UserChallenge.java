package com.loyalty.dxvalley.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class UserChallenge {
    @Id 
    @GeneratedValue(strategy= GenerationType.AUTO) 
    private Long userChallengeId; 

    private Double points;
    @JsonFormat(pattern="yyyy-MM-dd",shape = Shape.STRING)
    @Column(name="joinedAt")
    private String  joinedAt;
    private Boolean isEnabled;
    private String affliateLink;

    @ManyToOne 
    @JoinColumn(name = "userId") 
    Users users;

    @ManyToOne 
    @JoinColumn(name = "challengeId") 
    Challenge challenge;

    @ManyToOne
    private Levelss level;
}
