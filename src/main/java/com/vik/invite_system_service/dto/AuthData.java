package com.vik.invite_system_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthData {
    private String sub;
    private String iss;
    private Long inviteId;
    private Long exp;
    private Long iat;
}
