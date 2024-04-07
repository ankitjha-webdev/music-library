package com.musiclibrary.utils;

public class AuthenticationUtil {
    // Method to authenticate user
    public static boolean authenticateUser(String emailId, String password) {
        return emailId != null && password != null;
    }
}
