package com.loyalty.dxvalley.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
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
public class Transactionss {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private String naration;
    private Double amount;
    private String transactionType;
    private String status;
     @JsonFormat(pattern="yyyy-MM-dd",shape = Shape.STRING)
    @Column(name="generatedDate")
    private String  generatedDate;
    @ManyToOne
    private Users user;
    
}
