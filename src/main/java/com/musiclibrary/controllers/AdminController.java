package com.musiclibrary.controllers;

import java.sql.Date;

import com.musiclibrary.services.SongService;
import com.musiclibrary.services.UserService;
import com.musiclibrary.services.PlaylistService;

public class AdminController {
    private final UserService userService;
    private final SongService songService;
	private PlaylistService playlistService;

    public AdminController(UserService userService, SongService songService, PlaylistService playlistService) {
        this.userService = userService;
        this.songService = songService;
        this.playlistService = playlistService;
    }

    // Method to add a new song
    public void addSong(String name, String singer, String musicDirector, Date releaseDate, String albumName) {
        songService.addSong(name, singer, musicDirector, releaseDate, albumName);
    }

    // Method to update an existing song
    public void updateSong(int songId, String name, String singer, String musicDirector, Date releaseDate, String albumName) {
        songService.updateSong(songId, name, singer, musicDirector, releaseDate, albumName);
    }

    // Method to delete an existing song
    public void deleteSong(int songId) {
        songService.deleteSong(songId);
    }

    // Method to restrict visibility of a song
    public void restrictSongVisibility(int songId) {
        songService.restrictSongVisibility(songId, true);
    }

    // Method to add a new user
    public void addUser(String emailId, String phoneNumber, String password) {
        userService.addUser(emailId, phoneNumber, password);
    }

    // Method to update user details
    public void updateUser(int userId, String emailId, String phoneNumber, String password) {
        userService.updateUser(userId, emailId, phoneNumber, password);
    }

    // Method to delete a user
    public void deleteUser(int userId) {
        userService.deleteUser(userId);
    }
}
