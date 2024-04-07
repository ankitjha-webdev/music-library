package com.musiclibrary.services;

import com.musiclibrary.dao.UserDao;
import com.musiclibrary.dao.PlaylistDao;
import com.musiclibrary.models.User;
import com.musiclibrary.exceptions.DatabaseOperationException;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;
    private final PlaylistDao playlistDao;

    public UserService() {
        this.userDao = new UserDao();
        this.playlistDao = new PlaylistDao();
    }

    // Method to add a new user
    public void addUser(String emailId, String phoneNumber, String password) {
        if (!emailId.contains("@")) {
            throw new IllegalArgumentException("Invalid email ID.");
        }
        try {
            if (userDao.getUserByEmail(emailId) != null) {
                throw new IllegalArgumentException("Email ID already exists.");
            }
            if (userDao.getUserByPhoneNumber(phoneNumber) != null) {
                throw new IllegalArgumentException("Phone number already exists.");
            }
            User user = new User();
            user.setEmailId(emailId);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            userDao.addUser(user);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to add user", e);
        }
    }

    // Method to update user details
    public void updateUser(int userId, String emailId, String phoneNumber, String password) {
        if (!emailId.contains("@")) {
            throw new IllegalArgumentException("Invalid email ID.");
        }
        if (!doesUserExist(userId)) {
            throw new IllegalArgumentException("User ID does not exist.");
        }
        try {
            User user = new User();
            user.setUserId(userId);
            user.setEmailId(emailId);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            userDao.updateUser(user);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update user", e);
        }
    }

    // Method to delete a user
    public void deleteUser(int userId) {
        if (!doesUserExist(userId)) {
            throw new IllegalArgumentException("User ID does not exist.");
        }
        try {
            playlistDao.deletePlaylistsByUserId(userId);
            userDao.deleteUser(userId);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete user", e);
        }
    }

    // Method to retrieve a user by email, returns null if user does not exist
    public User getUserByEmail(String emailId) {
        try {
            return userDao.getUserByEmail(emailId);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve user by email", e);
        }
    }
    /**
     * Retrieves a user by their ID.
     * @param userId The ID of the user to retrieve.
     * @return The User object if found, null otherwise.
     */
    public User getUserById(int userId) {
        try {
            User user = userDao.getUserById(userId);
            if (user != null) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve user by ID", e);
        }
    }

    // Method to check if a user exists
    public boolean doesUserExist(int userId) {
        try {
            User user = userDao.getUserById(userId);
            return user != null;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to check if user exists", e);
        }
    }

    // Method to register a new user
    public boolean registerUser(String emailId, String phoneNumber, String password) {
        try {
            User user = new User();
            user.setEmailId(emailId);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            userDao.addUser(user);
            return true;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to register user", e);
        }
    }

    public boolean authenticateUser(String emailId, String password) {
        try {
            User user = userDao.getUserByEmail(emailId);
            if (user != null && user.getPassword().equals(password)) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to authenticate user", e);
        }
    }
}
