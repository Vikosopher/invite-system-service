package com.vik.invite_system_service.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.vik.invite_system_service.convertor.OrganizationRelationConverter;
import com.vik.invite_system_service.dto.AuthData;
import com.vik.invite_system_service.dto.InviteRequestDto;
import com.vik.invite_system_service.dto.OrganizationRelationDTO;
import com.vik.invite_system_service.entity.*;
import com.vik.invite_system_service.enums.IdentityType;
import com.vik.invite_system_service.enums.Role;
import com.vik.invite_system_service.enums.Status;
import com.vik.invite_system_service.exception.CustomException;
import com.vik.invite_system_service.repository.OrganizationRelationRepo;
import com.vik.invite_system_service.repository.OrganizationRepo;
import com.vik.invite_system_service.repository.UserInviteRepo;
import com.vik.invite_system_service.repository.UserRepo;
import com.vik.invite_system_service.response.VerifyInviteResponse;
import com.vik.invite_system_service.security.JwtUtil;
import com.vik.invite_system_service.util.MailSender;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InviteService {

  @Autowired private UserInviteRepo userInviteRepo;

  @Autowired private OrganizationRepo organizationRepo;

  @Autowired private UserRepo userRepo;

  @Autowired private OrganizationRelationRepo organizationRelationRepo;

  @Autowired private OrganizationRelationConverter organizationRelationConverter;

  @Autowired private JwtUtil jwtUtil;

  public String invite(Long userId, InviteRequestDto inviteRequestDto) {
    User user = new User();
    String orgDomain = getDomainFromEmail(inviteRequestDto.getInvitedBy());
    Organization organization =
        organizationRepo
            .findByDomain(orgDomain)
            .orElseThrow(
                () ->
                    new CustomException(
                        "Organization does not exist for the domain: " + orgDomain));

    UserInvite userInvite =
        UserInvite.builder()
            .invitedByUuid(organization.getId())
            .invitedByUserId(userId)
            .invitedToRefType(IdentityType.EMAIL)
            .invitedToRefId(inviteRequestDto.getInvitedTo())
            .status(Status.CREATED)
            .build();

    userInvite = userInviteRepo.save(userInvite);

    String token = jwtUtil.generateInviteToken(userInvite.getId());

    return MailSender.sendInviteMail(token);
  }

  private String getDomainFromEmail(String email) {
    return email.substring(email.indexOf('@') + 1);
  }

  public VerifyInviteResponse verify(String authToken) {

    try {
      AuthData claims = jwtUtil.verifyToken(authToken);
      Long inviteId = claims.getInviteId();

      UserInvite userInvite =
              userInviteRepo
                      .findById(inviteId)
                      .orElseThrow(() -> new CustomException("Invalid invite ID"));

      userInvite.setStatus(Status.VERIFIED);
      userInviteRepo.save(userInvite);

      String email = userInvite.getInvitedToRefId();
      User user = userRepo.findByEmail(email).orElseGet(() -> onboardNewUser(email));

      String token = jwtUtil.generateInviteToken(inviteId);

      List<OrganizationRelation> organizationRelations =
              organizationRelationRepo.findById_UserId(user.getId());
      List<OrganizationRelationDTO> organizationRelationDTOS =
              organizationRelationConverter.toDTO(organizationRelations);

      return VerifyInviteResponse.builder()
              .userId(user.getId())
              .email(user.getEmail())
              .token(token)
              .tokenType("Bearer")
              .organizationRelations(organizationRelationDTOS)
              .build();
    } catch (TokenExpiredException e) {
      Long inviteId = jwtUtil.extractAllClaims(authToken).getInviteId();

      Optional<UserInvite> userInvite = userInviteRepo.findById(inviteId);

      if (userInvite.isPresent()) {
        UserInvite userInvite1 = userInvite.get();
        userInvite1.setStatus(Status.EXPIRED);
        userInviteRepo.save(userInvite1);
      }
      throw new RuntimeException(e);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  private User onboardNewUser(String email) {
    String domain = getDomainFromEmail(email);
    Organization organization =
        organizationRepo
            .findByDomain(domain)
            .orElseThrow(
                () -> new CustomException("Organization does not exist for the domain: " + domain));

    User user = User.builder().email(email).active(true).build();
    userRepo.save(user);

    OrganizationRelation organizationRelation =
        OrganizationRelation.builder()
            .id(new OrganizationRelationId(organization.getId(), user.getId()))
            .role(Role.STAFF)
            .build();

    organizationRelationRepo.save(organizationRelation);

    return user;
  }
}
