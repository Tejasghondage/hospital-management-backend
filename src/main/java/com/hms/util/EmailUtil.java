package com.hms.util;

public class EmailUtil {

    // For demo purposes this just prints to console.
    // You can plug JavaMail here if you want real emails.
    public static void sendEmail(String to, String subject, String body) {
        System.out.println("=== Sending Email ===");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("=====================");
    }
}
