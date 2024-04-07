package com.musiclibrary.models;

public class Playlist {
 private int playlistId;
 private int userId;
 private String name;

 // Getters and setters

 public int getPlaylistId() {
     return playlistId;
 }

 public void setPlaylistId(int playlistId) {
     this.playlistId = playlistId;
 }

 public int getUserId() {
     return userId;
 }

 public void setUserId(int userId) {
     this.userId = userId;
 }

 public String getName() {
     return name;
 }

 public void setName(String name) {
     this.name = name;
 }

 @Override
 public String toString() {
     return "Playlist{" +
             "playlistId=" + playlistId +
             ", userId=" + userId +
             ", name='" + name + '\'' +
             '}';
 }
}
