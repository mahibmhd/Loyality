package com.loyalty.dxvalley.services;

import java.util.List;

import com.loyalty.dxvalley.models.TransactionRules;

public interface TransactionRulesService {

    TransactionRules addTransactionRules (TransactionRules transactionRules);
    TransactionRules editTransactionRules (TransactionRules transactionRules);
    List<TransactionRules> getTransactionRules();
    TransactionRules getTransactionRuleById(Long transactionRuleId);
    
}
