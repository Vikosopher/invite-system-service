package com.vik.invite_system_service.controller;

import com.vik.invite_system_service.dto.OTPDTO;
import com.vik.invite_system_service.response.VerifyOTPResponse;
import com.vik.invite_system_service.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("OTP")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @PostMapping("/generate")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Successfully fetched items", response = Item.class),
//            @ApiResponse(code = 404, message = "Items not found"),
//            @ApiResponse(code = 500, message = "Internal server error")
//    })
    public String generateOTP(@RequestBody OTPDTO otpdto) {
        return otpService.generate(otpdto);
    }

    @PostMapping("/verify")
    public VerifyOTPResponse verifyOTP(@RequestBody OTPDTO otpdto) {
        return otpService.verify(otpdto);
    }
}
