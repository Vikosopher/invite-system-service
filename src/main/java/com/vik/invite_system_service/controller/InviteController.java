package com.vik.invite_system_service.controller;

import com.vik.invite_system_service.dto.InviteRequestDto;
import com.vik.invite_system_service.response.VerifyInviteResponse;
import com.vik.invite_system_service.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("invite")
public class InviteController {

    @Autowired private InviteService inviteService;

    @PostMapping("/send/{userId}")
    public String sendInvite(@PathVariable Long userId, @RequestBody InviteRequestDto inviteRequestDto) {
        return inviteService.invite(userId, inviteRequestDto);
    }

    @PostMapping("/verify")
    public VerifyInviteResponse verifyInvite(@RequestParam("authToken") String authToken) {
        return inviteService.verify(authToken);

    }
}
