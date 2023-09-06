package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.TransactionRules;
import com.loyalty.dxvalley.repositories.TransactionRulesRepository;
import com.loyalty.dxvalley.services.TransactionRulesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionRulesServiceImpl  implements TransactionRulesService{

    @Autowired
    private final TransactionRulesRepository transactionRulesRepository;

    @Override
    public TransactionRules addTransactionRules(TransactionRules transactionRules) {
        return transactionRulesRepository.save(transactionRules);
    }

    @Override
    public TransactionRules editTransactionRules(TransactionRules transactionRules) {
        return transactionRulesRepository.save(transactionRules);
    }

    @Override
    public List<TransactionRules> getTransactionRules() {
       return transactionRulesRepository.findAll();
    }

    @Override
    public TransactionRules getTransactionRuleById(Long transactionRuleId) {
        return transactionRulesRepository.findByTransactionRuleId(transactionRuleId);
    }
    
}
