package com.vik.invite_system_service.convertor;

import com.vik.invite_system_service.dto.OrganizationRelationDTO;
import com.vik.invite_system_service.entity.OrganizationRelation;
import com.vik.invite_system_service.entity.OrganizationRelationId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationRelationConverter {

    public OrganizationRelationDTO toDTO(OrganizationRelation organizationRelation) {
        return OrganizationRelationDTO.builder()
                .organizationUuid(organizationRelation.getId().getOrganizationUuid())
                .userId(organizationRelation.getId().getUserId())
                .role(organizationRelation.getRole())
                .createdAt(organizationRelation.getCreatedAt())
                .updatedAt(organizationRelation.getUpdatedAt())
                .build();
    }

    public OrganizationRelation toOrganizationRelation(OrganizationRelationDTO organizationRelationDTO) {
        return OrganizationRelation.builder()
                .id(new OrganizationRelationId(
                        organizationRelationDTO.getOrganizationUuid(),
                        organizationRelationDTO.getUserId()
                ))
                .build();
    }

    public List<OrganizationRelationDTO> toDTO(List<OrganizationRelation> organizationRelations) {
        return organizationRelations.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
