package com.vik.invite_system_service.util;

import java.util.regex.Pattern;

public class ValidatePhoneNumber {
    private static final String PHONENUMBER_REGEX = "^[0-9]{10}$";

    public static boolean isValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONENUMBER_REGEX);
        return pattern.matcher(phoneNumber).matches();
    }
}
