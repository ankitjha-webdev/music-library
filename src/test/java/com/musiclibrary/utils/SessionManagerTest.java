package com.musiclibrary.utils;

import com.musiclibrary.utils.SessionManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {

    @Test
    public void startSession_WithValidEmail_ShouldReturnTrue() {
        // Arrange
        String emailId = "test@example.com";

        // Act
        boolean result = SessionManager.startSession(emailId);

        // Assert
        assertTrue(result, "Session should start successfully with a valid email");
    }

    @Test
    public void startSession_WithNullEmail_ShouldReturnFalse() {
        // Arrange
        String emailId = null;

        // Act
        boolean result = SessionManager.startSession(emailId);

        // Assert
        assertFalse(result, "Session should not start with a null email");
    }

    @Test
    public void startSession_WithEmptyEmail_ShouldReturnFalse() {
        // Arrange
        String emailId = "";

        // Act
        boolean result = SessionManager.startSession(emailId);

        // Assert
        assertFalse(result, "Session should not start with an empty email");
    }

    @Test
    public void endSession_WithExistingEmail_ShouldReturnTrue() {
        // Arrange
        String emailId = "existing@example.com";
        SessionManager.startSession(emailId); // Ensure the session exists

        // Act
        boolean result = SessionManager.endSession(emailId);

        // Assert
        assertTrue(result, "Ending an existing session should return true");
    }

    @Test
    public void endSession_WithNonExistingEmail_ShouldReturnFalse() {
        // Arrange
        String emailId = "nonexisting@example.com";

        // Act
        boolean result = SessionManager.endSession(emailId);

        // Assert
        assertFalse(result, "Ending a non-existing session should return false");
    }
}
