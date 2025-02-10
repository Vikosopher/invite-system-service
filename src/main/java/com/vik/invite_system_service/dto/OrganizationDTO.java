package com.vik.invite_system_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationDTO {
    private String id;
    private String name;
    private String email;
    private Long userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean active;
    private String domain;

}
