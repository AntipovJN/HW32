package com.boraji.tutorial.spring.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

public class SHA256StringHashUtil {

    private static final Logger logger = Logger.getLogger(SHA256StringHashUtil.class);

    public static String getSha256(String value) {
        try {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("Empty string");
            }
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(value.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (Exception ex) {
            logger.error("Hashing error", ex);
            throw new RuntimeException(ex);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes)
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}

