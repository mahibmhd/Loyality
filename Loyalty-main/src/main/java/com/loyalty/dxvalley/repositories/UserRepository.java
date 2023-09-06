package com.loyalty.dxvalley.repositories;
import org.springframework.data.jpa.repository.JpaRepository;


import com.loyalty.dxvalley.models.Users;

public interface UserRepository extends JpaRepository<Users,Long>{
    Users findByUsername(String username);
    Users findByUserId (Long userId);
}
