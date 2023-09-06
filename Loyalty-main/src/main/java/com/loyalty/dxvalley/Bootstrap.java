package com.loyalty.dxvalley;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.loyalty.dxvalley.models.Address;
import com.loyalty.dxvalley.models.Role;
import com.loyalty.dxvalley.models.Users;
import com.loyalty.dxvalley.repositories.AddressRepository;
import com.loyalty.dxvalley.repositories.RoleRepository;
import com.loyalty.dxvalley.repositories.UserRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "database", name = "seed", havingValue = "true")
public class Bootstrap {

  
    Role superAdmin = new Role("superAdmin", "Admin with all power");
    Role loyaltyAppUser = new Role("loyaltyAppUser", "Loyalty app user");
    Address address1= new Address("924385314","elshadayt@coopbankoromia.com.et","Ethiopia","Bole","bole","10","bole","01");
    Users user1 = new Users("924385314", "elshu13", "Elshaday Tamire", "elshadayt@coopbankoromia.com.et", true, "MALE",
            "07-09-1999", "/image.png", "198.1.13.2", "01-09-2022", null, 1, 0, false, true);
  
    Collection<Role> roles = new ArrayList<>();

    void setUp() {
        roles.add(superAdmin);
        roles.add(loyaltyAppUser);
        user1.setRoles(roles);
        user1.setAddress(address1);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository,
            AddressRepository addressRepo) {
        setUp();
        return args -> {
            log.info("Preloading " + roleRepository.save(loyaltyAppUser));
            log.info("Preloading " + roleRepository.save(superAdmin)); 
            log.info("Preloading " + userRepository.save(user1));  
        };
    }
}