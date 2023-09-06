package com.loyalty.dxvalley.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loyalty.dxvalley.models.CreateResponse;
import com.loyalty.dxvalley.models.TransactionRules;
import com.loyalty.dxvalley.services.TransactionRulesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactionRules")
@RequiredArgsConstructor

public class TransactionRulesController {
    @Autowired
    private final TransactionRulesService transactionRulesService;


    @PostMapping("/addTransactionRules")
    public ResponseEntity<CreateResponse> addTransactionRules (@RequestBody TransactionRules transactionRules) {
    transactionRulesService.addTransactionRules(transactionRules);
    CreateResponse response = new CreateResponse("Success","TransactionRules created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     
    @GetMapping("/getTransactionRules")
    List <TransactionRules> getTransactionRules() {
        return this.transactionRulesService.getTransactionRules();
    }

    @GetMapping("/{transactionRuleId}")
    TransactionRules getTransactionRules(@PathVariable Long transactionRuleId) {
        return transactionRulesService.getTransactionRuleById(transactionRuleId);
    }    

    @PutMapping("edit/{transactionRuleId}")
    TransactionRules editTransactionRules(@RequestBody TransactionRules temptransactionRules, @PathVariable Long transactionRuleId) {
       TransactionRules transactionRules = this.transactionRulesService.getTransactionRuleById(transactionRuleId);
       transactionRules.setMinValue(temptransactionRules.getMinValue());
       transactionRules.setMaxValue(temptransactionRules.getMaxValue());
       transactionRules.setPoint(temptransactionRules.getPoint());
       transactionRules.setChallenge(temptransactionRules.getChallenge());
       return transactionRulesService.editTransactionRules(transactionRules);
    }

   

}
