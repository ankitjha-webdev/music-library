package com.musiclibrary.models;

public class User {
 private int userId;
 private String emailId;
 private String phoneNumber;
 public String password;

 // Getters and setters

 public int getUserId() {
     return userId;
 }

 public void setUserId(int userId) {
     this.userId = userId;
 }

 public String getEmailId() {
     return emailId;
 }

 public void setEmailId(String emailId) {
     this.emailId = emailId;
 }

 public String getPhoneNumber() {
     return phoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
     this.phoneNumber = phoneNumber;
 }

 public String getPassword() {
     return password;
 }

 public void setPassword(String password) {
     this.password = password;
 }

 @Override
 public String toString() {
     return "User{" +
             "userId=" + userId +
             ", emailId='" + emailId + '\'' +
             ", phoneNumber='" + phoneNumber + '\'' +
             ", password='" + password + '\'' +
             '}';
 }
}
