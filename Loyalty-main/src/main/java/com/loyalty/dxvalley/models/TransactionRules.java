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
@Entity
@RequiredArgsConstructor
public class TransactionRules {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long transactionRuleId;
    private Double  minValue;
    private Double maxValue;
    private Double point;
    @ManyToOne
    private Challenge challenge;
}
