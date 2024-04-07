package com.musiclibrary.utils;

import com.musiclibrary.utils.AuthenticationUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationUtilTest {

    @Test
    public void authenticateUser_ShouldReturnTrueForNonNullCredentials() {
        // Arrange
        String emailId = "user@example.com";
        String password = "password123";

        // Act
        boolean result = AuthenticationUtil.authenticateUser(emailId, password);

        // Assert
        assertTrue(result, "Authentication should succeed for non-null credentials.");
    }

    @Test
    public void authenticateUser_ShouldReturnFalseForNullEmailId() {
        // Arrange
        String emailId = null;
        String password = "password123";

        // Act
        boolean result = AuthenticationUtil.authenticateUser(emailId, password);

        // Assert
        assertFalse(result, "Authentication should fail for null email ID.");
    }

    @Test
    public void authenticateUser_ShouldReturnFalseForNullPassword() {
        // Arrange
        String emailId = "user@example.com";
        String password = null;

        // Act
        boolean result = AuthenticationUtil.authenticateUser(emailId, password);

        // Assert
        assertFalse(result, "Authentication should fail for null password.");
    }

    @Test
    public void authenticateUser_ShouldReturnFalseForNullCredentials() {
        // Arrange
        String emailId = null;
        String password = null;

        // Act
        boolean result = AuthenticationUtil.authenticateUser(emailId, password);

        // Assert
        assertFalse(result, "Authentication should fail for null credentials.");
    }
}
