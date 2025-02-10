package com.vik.invite_system_service.util;

import org.springframework.stereotype.Component;

@Component
public class MailSender {
    public static String sendEmail(String email) {
        return "Successfully sent email to :- " + email;
    }

    public static String sendInviteMail(String token) {
        return "You are invited! Please use the token to get onboarded: " + token;
    }
}
