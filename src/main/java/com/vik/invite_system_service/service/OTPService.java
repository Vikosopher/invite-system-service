package com.vik.invite_system_service.service;

import com.vik.invite_system_service.convertor.OTPConverter;
import com.vik.invite_system_service.convertor.OrganizationRelationConverter;
import com.vik.invite_system_service.dto.OTPDTO;
import com.vik.invite_system_service.dto.OrganizationRelationDTO;
import com.vik.invite_system_service.entity.*;
import com.vik.invite_system_service.enums.IdentityType;
import com.vik.invite_system_service.enums.Role;
import com.vik.invite_system_service.enums.Status;
import com.vik.invite_system_service.exception.CustomException;
import com.vik.invite_system_service.repository.OrganizationRelationRepo;
import com.vik.invite_system_service.repository.OrganizationRepo;
import com.vik.invite_system_service.repository.OtpRepo;
import com.vik.invite_system_service.repository.UserRepo;
import com.vik.invite_system_service.response.VerifyOTPResponse;
import com.vik.invite_system_service.security.JwtUtil;
import com.vik.invite_system_service.util.MailSender;
import com.vik.invite_system_service.util.ValidateEmail;
import com.vik.invite_system_service.util.ValidatePhoneNumber;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
  @Autowired private OtpRepo otpRepo;

  @Autowired private OrganizationRepo organizationRepo;

  @Autowired private UserRepo userRepo;

  @Autowired private OrganizationRelationRepo organizationRelationRepo;

  @Autowired private OrganizationRelationConverter organizationRelationConverter;

  @Autowired private OTPConverter otpConverter;

  public String generate(OTPDTO otpdto) {
    validateIdentity(otpdto);
    String email = otpdto.getIdentity();
    String domain = getDomainFromEmail(email);
    validateOrganizationDomain(domain);

    OTP otp =
        otpRepo
            .findByIdentity(email)
            .map(this::updateExistingOTP)
            .orElseGet(() -> createNewOTP(otpdto));

    otpRepo.save(otp);
    return MailSender.sendEmail(email);
  }

  public VerifyOTPResponse verify(OTPDTO otpdto) {
    OTP otp =
        otpRepo
            .findByIdentityAndOtp(otpdto.getIdentity(), otpdto.getOtp())
            .orElseThrow(() -> new CustomException("Invalid OTP"));
    if (otp.getExpiry().before(new Timestamp(System.currentTimeMillis()))) {
      otp.setStatus(Status.EXPIRED);
      otpRepo.save(otp);
      throw new CustomException("OTP has expired");
    }
    otp.setStatus(Status.VERIFIED);
    otpRepo.save(otp);

    User user = userRepo.findByEmail(otpdto.getIdentity()).orElseGet(() -> onboardNewUser(otpdto));

    String token = JwtUtil.generateToken(user);
    List<OrganizationRelation> organizationRelations =
        organizationRelationRepo.findById_UserId(user.getId());
    List<OrganizationRelationDTO> organizationRelationDTOS =
        organizationRelationConverter.toDTO(organizationRelations);

    return VerifyOTPResponse.builder()
        .userId(user.getId())
        .email(user.getEmail())
        .token(token)
        .tokenType("Bearer")
        .organizationRelations(organizationRelationDTOS)
        .build();
  }

  private String generateOTPCode() {
    SecureRandom secureRandom = new SecureRandom();
    int otp = 100000 + secureRandom.nextInt(900000);
    return String.valueOf(otp);
  }

  private String getDomainFromEmail(String email) {
    return email.substring(email.indexOf('@') + 1);
  }

  private void validateOrganizationDomain(String domain) {
    if (!organizationRepo.existsByDomain(domain)) {
      throw new CustomException("Organization does not exist for the domain: " + domain);
    }
  }

  private void validateIdentity(OTPDTO otpdto) {
    if (otpdto.getIdentityType() == IdentityType.EMAIL) {
      if (!ValidateEmail.isValid(otpdto.getIdentity())) {
        throw new IllegalArgumentException("Invalid email address");
      }
    } else if (otpdto.getIdentityType() == IdentityType.PHONE_NUMBER) {
      if (!ValidatePhoneNumber.isValid(otpdto.getIdentity())) {
        throw new IllegalArgumentException("Invalid phone number");
      }
    } else {
      throw new IllegalArgumentException("Unknown identity type");
    }
  }

  private OTP updateExistingOTP(OTP existingOtp) {
    existingOtp.setOtp(generateOTPCode());
    existingOtp.setStatus(Status.CREATED);
    existingOtp.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    existingOtp.setExpiry(new Timestamp(System.currentTimeMillis() + (10 * 60 * 1000)));
    existingOtp.setActive(true);
    return existingOtp;
  }

  private OTP createNewOTP(OTPDTO otpdto) {
    OTP otp = otpConverter.toOTP(otpdto);
    otp.setOtp(generateOTPCode());
    return otp;
  }

  private User onboardNewUser(OTPDTO otpdto) {
    String domain = getDomainFromEmail(otpdto.getIdentity());
    Organization organization =
        organizationRepo
            .findByDomain(domain)
            .orElseThrow(
                () -> new CustomException("Organization does not exist for the domain: " + domain));

    User user = User.builder().email(otpdto.getIdentity()).active(true).build();
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
