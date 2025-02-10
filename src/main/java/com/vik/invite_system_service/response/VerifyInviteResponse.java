package com.vik.invite_system_service.response;

import com.vik.invite_system_service.dto.OrganizationRelationDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VerifyInviteResponse {
    private Long userId;
    private String email;
    private String token;
    private String tokenType;
    private List<OrganizationRelationDTO> organizationRelations;
}
