package com.musiclibrary.services;

import com.musiclibrary.models.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void addUserTest() {
        String emailId = "test" + (int)(Math.random() * 1000) + "@example.com";
        String phoneNumber = "123456" + (int)(Math.random() * 10000);
        userService.addUser(emailId, phoneNumber, "password");
        User user = userService.getUserByEmail(emailId);

        assertNotNull(user, "User should be added and retrievable by email.");
        assertEquals(emailId, user.getEmailId(), "Email ID should match the one provided.");
    }

    @Test
    void updateUserTest() {
        int userId = 2; // Assuming there is a user with ID 2
        String updatedEmailId = "update@example.com";
        userService.updateUser(userId, updatedEmailId, "0927654321", "newpassword");
        User user = userService.getUserById(userId);

        assertEquals(updatedEmailId, user.getEmailId(), "User email ID should be updated.");
    }

    @Test
    void deleteUserTest() {
        int userId = 2; // Assuming there is a user with ID 2
        userService.deleteUser(userId);
        boolean userExists = userService.doesUserExist(userId);

        assertFalse(userExists, "User should be deleted and no longer exist.");
    }

    @Test
    void addUserWithInvalidEmailTest() {
        String invalidEmailId = "invalid";
        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(invalidEmailId, "1234567890", "password"),
                "Should throw IllegalArgumentException for invalid email ID.");
    }

    @Test
    void updateUserWithNonExistentIdTest() {
        int nonExistentUserId = 999; // Assuming this ID does not exist
        assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(nonExistentUserId, "nonexistent@example.com", "1234567890", "password"),
                "Should throw IllegalArgumentException for non-existent user ID.");
    }

    @Test
    void deleteUserWithNonExistentIdTest() {
        int nonExistentUserId = 999; // Assuming this ID does not exist
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(nonExistentUserId),
                "Should throw IllegalArgumentException for non-existent user ID.");
    }

    @Test
    void getUserByEmailWithNonExistentEmailTest() {
        String nonExistentEmail = "nonexistent@example.com";
        User user = userService.getUserByEmail(nonExistentEmail);

        assertNull(user, "Should return null for non-existent email.");
    }

    @Test
    void getUserByIdWithNonExistentIdTest() {
        int nonExistentUserId = 999; // Assuming this ID does not exist
        User user = userService.getUserById(nonExistentUserId);

        assertNull(user, "Should return null for non-existent user ID.");
    }
}
