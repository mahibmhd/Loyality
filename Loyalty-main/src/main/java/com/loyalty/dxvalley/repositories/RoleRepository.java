package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.loyalty.dxvalley.models.Role;


public interface RoleRepository extends JpaRepository <Role, Integer> {
    Role findByRoleName (String roleName);
}
