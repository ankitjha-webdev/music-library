package com.musiclibrary.dao;

import com.musiclibrary.models.User;
import com.musiclibrary.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {
 private final Connection connection;

 public UserDao() {
     this.connection = DatabaseUtil.getConnection();
 }

 // Method to add a new user
 public void addUser(User user) throws SQLException {
     String query = "INSERT INTO User (emailId, phoneNumber, password) VALUES (?, ?, ?)";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, user.getEmailId());
         statement.setString(2, user.getPhoneNumber());
         statement.setString(3, user.getPassword());
         statement.executeUpdate();
     }
 }

 // Method to update user details
 public void updateUser(User user) throws SQLException {
     String query = "UPDATE User SET emailId = ?, phoneNumber = ?, password = ? WHERE userId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, user.getEmailId());
         statement.setString(2, user.getPhoneNumber());
         statement.setString(3, user.getPassword());
         statement.setInt(4, user.getUserId());
         statement.executeUpdate();
     }
 }

 // Method to delete a user
 public void deleteUser(int userId) throws SQLException {
     String query = "DELETE FROM User WHERE userId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setInt(1, userId);
         int rowsAffected = statement.executeUpdate();
         if (rowsAffected == 0) {
             throw new SQLException("Deleting user failed, no rows affected.");
         }
     }
 }

 // Method to fetch user by emailId
 public User getUserByEmail(String emailId) throws SQLException {
     String query = "SELECT * FROM User WHERE emailId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, emailId);
         try (ResultSet resultSet = statement.executeQuery()) {
             if (resultSet.next()) {
                 return extractUserFromResultSet(resultSet);
             }
         }
     }
     return null;
 }

 // Method to fetch user by emailId
 public User getUserByPhoneNumber(String phoneNumber) throws SQLException {
     String query = "SELECT * FROM User WHERE phoneNumber = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, phoneNumber);
         try (ResultSet resultSet = statement.executeQuery()) {
             if (resultSet.next()) {
                 return extractUserFromResultSet(resultSet);
             }
         }
     }
     return null;
 }

 // Method to extract User object from ResultSet
 private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
     User user = new User();
     user.setUserId(resultSet.getInt("userId"));
     user.setEmailId(resultSet.getString("emailId"));
     user.setPhoneNumber(resultSet.getString("phoneNumber"));
     user.setPassword(resultSet.getString("password"));
     return user;
 }
 
 // Method to fetch user by userId
 public User getUserById(int userId) throws SQLException {
     String query = "SELECT * FROM User WHERE userId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setInt(1, userId);
         try (ResultSet resultSet = statement.executeQuery()) {
             if (resultSet.next()) {
                 return extractUserFromResultSet(resultSet);
             }
         }
     }
     return null;
 }

 // Method to check if a user exists by userId
 public boolean doesUserExist(int userId) throws SQLException {
     String query = "SELECT COUNT(1) FROM User WHERE userId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setInt(1, userId);
         try (ResultSet resultSet = statement.executeQuery()) {
             if (resultSet.next()) {
                 return resultSet.getInt(1) > 0;
             }
         }
     }
     return false;
 }

}
