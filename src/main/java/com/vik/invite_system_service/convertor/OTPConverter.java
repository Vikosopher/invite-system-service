package com.vik.invite_system_service.convertor;

import com.vik.invite_system_service.dto.OTPDTO;
import com.vik.invite_system_service.entity.OTP;
import org.springframework.stereotype.Service;

@Service
public class OTPConverter {
    public OTPDTO toDTO(OTP otp) {
        return OTPDTO.builder()
                .id(otp.getId())
                .otp(otp.getOtp())
                .identityType(otp.getIdentityType())
                .identity(otp.getIdentity())
                .status(otp.getStatus())
                .createdAt(otp.getCreatedAt())
                .active(otp.isActive())
                .expiry(otp.getExpiry())
                .build();
    }

    public OTP toOTP(OTPDTO otpdto) {
        return OTP.builder()
                .identityType(otpdto.getIdentityType())
                .identity(otpdto.getIdentity())
                .build();
    }
}
