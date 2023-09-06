package com.loyalty.dxvalley.services;


import java.util.List;
import com.loyalty.dxvalley.models.Transactionss;

public interface TransactionsService {
    List<Transactionss> getTransactionsByUsername (String username);
    List<Transactionss> getAll();
    Transactionss addTransactionss(Transactionss transactionss);
}
