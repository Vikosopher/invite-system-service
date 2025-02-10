package com.vik.invite_system_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vik.invite_system_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationRelationDTO {
    private String organizationUuid;
    private Long userId;
    private Role role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
