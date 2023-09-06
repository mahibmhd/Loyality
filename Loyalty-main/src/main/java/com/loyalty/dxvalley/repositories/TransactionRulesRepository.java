package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.TransactionRules;

public interface TransactionRulesRepository extends JpaRepository<TransactionRules , Long> {


     TransactionRules findByTransactionRuleId(Long transactionRuleId);

    
}
