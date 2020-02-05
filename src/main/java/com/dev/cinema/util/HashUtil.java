package com.dev.cinema.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class HashUtil {
    private static final Logger LOGGER = LogManager.getLogger(HashUtil.class);

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        }
        return hashPassword.toString();
    }
}
