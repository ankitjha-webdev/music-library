package com.musiclibrary.controllers;

import com.musiclibrary.models.Playlist;
import com.musiclibrary.models.Song;
import com.musiclibrary.services.PlaylistService;
import com.musiclibrary.services.SongService;
import com.musiclibrary.services.UserService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserController {
    private final UserService userService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final ExecutorService threadPool;

    public UserController(UserService userService, SongService songService, PlaylistService playlistService) {
        this.userService = userService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.threadPool = Executors.newFixedThreadPool(10); // Assuming a fixed thread pool size for simplicity
    }

    // Method to view all songs
    public List<Song> viewAllSongs() {
        return songService.getAllSongs();
    }

    // Method to view details of a song
    public Song viewSongDetails(int songId) {
        return songService.getSongDetails(songId);
    }

    // Method to search for a song
    public List<Song> searchSong(String name) {
        return songService.searchSongByName(name);
    }

    // Method to create a new play-list
    public void createPlaylist(int userId, String name) {
        playlistService.createPlaylist(userId, name);
    }

    // Method to view all play-lists of a user
    public List<Playlist> viewUserPlaylists(int userId) {
        return playlistService.getUserPlaylists(userId);
    }

    // Method to add a song to a play-list
    public void addSongToPlaylist(int playlistId, int songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
    }

    // Method to search for a song in a play-list
    public List<Song> searchSongInPlaylist(int playlistId, String name) {
        return playlistService.searchSongInPlaylist(playlistId, name);
    }

    // User Registration
    public boolean registerUser(String emailId, String phoneNumber, String password) {
        return userService.registerUser(emailId, phoneNumber, password);
    }

    // User Login
    public void loginUser(String emailId, String password) {
        threadPool.execute(() -> {
            boolean loginSuccess = userService.authenticateUser(emailId, password);
            if (loginSuccess) {
                System.out.println(emailId + " logged in.");
                // User session actions here
            } else {
                System.out.println("Login failed for " + emailId);
            }
        });
    }

    // User Logout
    // Assuming a method to handle user logout, terminating the user's session thread
    public void logoutUser(String emailId) {
        // Implementation to terminate the specific thread associated with the user's session
    }
}