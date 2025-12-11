package core.security;

import java.security.MessageDigest;

public class PasswordUtil {

    public static String sha256(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(original.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Cannot hash password", e);
        }
    }
}
