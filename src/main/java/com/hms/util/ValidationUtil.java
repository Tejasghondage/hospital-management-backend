package com.hms.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern CONTACT_PATTERN =
            Pattern.compile("^\\d{10}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidContact(String contact) {
        return contact != null && CONTACT_PATTERN.matcher(contact).matches();
    }

    public static boolean isStrongPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}
