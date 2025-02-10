package com.vik.invite_system_service.service;

import com.vik.invite_system_service.convertor.OrganizationRelationConverter;
import com.vik.invite_system_service.dto.OrganizationRelationDTO;
import com.vik.invite_system_service.entity.OrganizationRelation;
import com.vik.invite_system_service.repository.OrganizationRelationRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRelationService {
  @Autowired private OrganizationRelationRepo organizationRelationRepo;

  @Autowired private OrganizationRelationConverter organizationRelationConverter;

  public List<OrganizationRelationDTO> getAllOrganizationRelations() {
    List<OrganizationRelation> organizationRelations = organizationRelationRepo.findAll();

    List<OrganizationRelationDTO> organizationRelationDTOS =
        organizationRelationConverter.toDTO(organizationRelations);

    return organizationRelationDTOS;
  }
}
