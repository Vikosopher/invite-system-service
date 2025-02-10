package com.vik.invite_system_service.service;

import com.vik.invite_system_service.convertor.OrganizationConverter;
import com.vik.invite_system_service.dto.OrganizationDTO;
import com.vik.invite_system_service.entity.Organization;
import com.vik.invite_system_service.entity.OrganizationRelation;
import com.vik.invite_system_service.enums.Role;
import com.vik.invite_system_service.repository.OrganizationRelationRepo;
import com.vik.invite_system_service.util.ValidateEmail;
import com.vik.invite_system_service.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrganizationService {

  @Autowired private OrganizationRepo organizationRepo;

  @Autowired private OrganizationConverter organizationConverter;

  @Autowired private OrganizationRelationRepo organizationRelationRepo;

  @Transactional
  public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
    if (!ValidateEmail.isValid(organizationDTO.getEmail())) {
      throw new IllegalArgumentException("Invalid email address");
    }
    Organization organization;
    String domain = organizationDTO.getEmail().substring(organizationDTO.getEmail().indexOf('@') + 1);
    if (organizationRepo.existsByDomain(domain)) {
      Optional<Organization> organizationOptional = organizationRepo.findByDomain(domain);
      organization = organizationOptional.get();
      organization.setUserId(organizationDTO.getUserId());
      organizationRepo.save(organization);

      Optional<OrganizationRelation> organizationRelationOptional = organizationRelationRepo.findByIdOrganizationUuidAndIdUserId(organization.getId(), organizationDTO.getUserId());
      if (organizationRelationOptional.isPresent()) {
        OrganizationRelation organizationRelation = organizationRelationOptional.get();
        organizationRelation.setRole(Role.OWNER);

        organizationRelationRepo.save(organizationRelation);
      }

    }

    else {
      organization = organizationConverter.toOrganization(organizationDTO);
      organizationRepo.save(organization);
    }



    return organizationConverter.toDTO(organization);
  }
}
