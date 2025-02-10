package com.vik.invite_system_service.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRelationId implements Serializable {
    private String organizationUuid;
    private Long userId;
}
