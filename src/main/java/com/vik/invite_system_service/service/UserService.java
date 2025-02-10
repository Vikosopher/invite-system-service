package com.vik.invite_system_service.service;

import com.vik.invite_system_service.convertor.UserConverter;
import com.vik.invite_system_service.dto.UserDTO;
import com.vik.invite_system_service.entity.Organization;
import com.vik.invite_system_service.entity.OrganizationRelation;
import com.vik.invite_system_service.entity.OrganizationRelationId;
import com.vik.invite_system_service.entity.User;
import com.vik.invite_system_service.enums.Role;
import com.vik.invite_system_service.exception.CustomException;
import com.vik.invite_system_service.util.ValidateEmail;
import com.vik.invite_system_service.repository.OrganizationRelationRepo;
import com.vik.invite_system_service.repository.OrganizationRepo;
import com.vik.invite_system_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired private UserRepo userRepo;

  @Autowired private OrganizationRepo organizationRepo;

  @Autowired private OrganizationRelationRepo organizationRelationRepo;

  @Autowired private UserConverter userConverter;

  public UserDTO createUser(UserDTO userDTO) {
    if (!ValidateEmail.isValid(userDTO.getEmail())) {
      throw new IllegalArgumentException("Invalid email address");
    }
    String domain = userDTO.getEmail().substring(userDTO.getEmail().indexOf('@') + 1);

    Organization organization = organizationRepo.findByDomain(domain)
            .orElseThrow(() -> new CustomException("Organization does not exist for the domain: " + domain));

    User user = userConverter.toUser(userDTO);
    user = userRepo.save(user);

    OrganizationRelation organizationRelation =
        OrganizationRelation.builder()
            .id(new OrganizationRelationId(organization.getId(), user.getId()))
                .role(Role.STAFF)
            .build();

    organizationRelationRepo.save(organizationRelation);

    return userConverter.toDTO(user);
  }
}
