package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.Packagess;

public interface PackagessRepository extends JpaRepository<Packagess,Long> {
     Packagess findByPackageId (Long packageId);
    
}
