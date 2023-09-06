package com.loyalty.dxvalley.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.loyalty.dxvalley.models.Transactionss;
import com.loyalty.dxvalley.services.TransactionsService;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionsController {
     @Autowired
    private final TransactionsService transactionsService;
    @GetMapping("/getByUsername/{username}")
     List<Transactionss> getAll(@PathVariable String username) {
   return transactionsService.getTransactionsByUsername(username);
  }

}
