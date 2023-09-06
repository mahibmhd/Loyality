package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.InvitedUsers;
import com.loyalty.dxvalley.repositories.InvitedUsersRepository;
import com.loyalty.dxvalley.services.InvitedUsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitedUsersServiceImpl implements InvitedUsersService{
    @Autowired
    private final InvitedUsersRepository invitedUsersRepository;
    @Override
    public List<InvitedUsers> getInvitedUsers() {
       return invitedUsersRepository.findAll();
    }
    
}
