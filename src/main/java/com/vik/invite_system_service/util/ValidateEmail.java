package com.vik.invite_system_service.util;

import java.util.regex.Pattern;

public class ValidateEmail {
    private static final String Email_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,7}$";
//    private static final String EMAIL_REGEX2 = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public static boolean isValid(String email) {
        Pattern pattern = Pattern.compile(Email_REGEX);
        return pattern.matcher(email).matches();
    }

}
