package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.Transactionss;
import com.loyalty.dxvalley.models.Users;
import com.loyalty.dxvalley.repositories.TransactionsRepository;
import com.loyalty.dxvalley.repositories.UserRepository;
import com.loyalty.dxvalley.services.TransactionsService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {
    @Autowired
    private final UserRepository userRepository;
    private final TransactionsRepository transactionsRepository;
    @Override
    public List<Transactionss> getTransactionsByUsername(String username) {
        Users users= userRepository.findByUsername(username);
        return transactionsRepository.findTransactionssByUser(users);
    }
    @Override
    public List<Transactionss> getAll() {
        return transactionsRepository.findAll();
    }
    @Override
    public Transactionss addTransactionss(Transactionss transactionss) {
       return transactionsRepository.save(transactionss);
    }
    

}
