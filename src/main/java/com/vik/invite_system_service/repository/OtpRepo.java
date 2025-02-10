package com.vik.invite_system_service.repository;

import com.vik.invite_system_service.entity.OTP;
import com.vik.invite_system_service.enums.IdentityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<OTP, Long> {
    Optional<OTP> findByIdentity(String identity);
    Optional<OTP> findByIdentityAndOtp(String identity, String otp);
}
