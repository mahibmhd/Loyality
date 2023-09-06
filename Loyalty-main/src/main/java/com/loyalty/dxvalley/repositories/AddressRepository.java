package com.loyalty.dxvalley.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.Address;


public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByAddressId (Long addressId);
    Address findAddressByPhoneNumber(String phoneNumber);
    // List<Address> findAddressByUnion(Unions union);
    // List<Address> findAddressByPrCooperative(PrCooperative prCooperative);
    
}
