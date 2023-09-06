package com.loyalty.dxvalley.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.Transactionss;
import com.loyalty.dxvalley.models.Users;

public interface TransactionsRepository extends JpaRepository<Transactionss,Long> {
    List<Transactionss> findTransactionssByUser(Users users);
}
