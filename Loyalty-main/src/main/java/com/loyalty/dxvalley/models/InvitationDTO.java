package com.loyalty.dxvalley.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class InvitationDTO {
    private String inviter;
    private String invitee;
}
