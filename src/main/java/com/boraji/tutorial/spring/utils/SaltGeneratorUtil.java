package com.boraji.tutorial.spring.utils;

public class SaltGeneratorUtil {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String getSalt() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String saltPassword(String password, String salt) {
        int mid = salt.length() / 2;
        return salt.substring(0, mid) + password + salt.substring(mid);
    }

//    public static void main(String[] args) {
//        String salt = SaltGeneratorUtil.getSalt();
//        String saltPass = saltPassword("aa", salt);
//        System.out.println(salt);
//        System.out.println(saltPass);
//        System.out.println(SHA256StringHashUtil.getSha256(saltPass));
//    }
}
