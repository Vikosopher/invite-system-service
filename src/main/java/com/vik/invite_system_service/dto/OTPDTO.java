package com.vik.invite_system_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vik.invite_system_service.enums.IdentityType;
import com.vik.invite_system_service.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTPDTO {

    private Long id;
    private String otp;
    private IdentityType identityType;
    private String identity;
    private Status status;
    private Timestamp createdAt;
    private boolean active;
    private Timestamp expiry;

}
