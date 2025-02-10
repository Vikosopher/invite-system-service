package com.vik.invite_system_service.convertor;

import com.vik.invite_system_service.dto.OrganizationDTO;
import com.vik.invite_system_service.entity.Organization;
import org.springframework.stereotype.Service;

@Service
public class OrganizationConverter {

  public OrganizationDTO toDTO(Organization organization) {
    return OrganizationDTO.builder()
        .id(organization.getId())
        .name(organization.getName())
        .email(organization.getEmail())
        .userId(organization.getUserId())
        .createdAt(organization.getCreatedAt())
        .updatedAt(organization.getUpdatedAt())
        .active(organization.isActive())
        .domain(organization.getDomain())
        .build();
  }

  public Organization toOrganization(OrganizationDTO organizationDTO) {
    return Organization.builder()
        .name(organizationDTO.getName())
        .email(organizationDTO.getEmail())
        .userId(organizationDTO.getUserId())
        .build();
  }
}
