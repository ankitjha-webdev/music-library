package com.musiclibrary;

import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.musiclibrary.controllers.AdminController;
import com.musiclibrary.controllers.UserController;
import com.musiclibrary.services.PlaylistService;
import com.musiclibrary.services.SongService;
import com.musiclibrary.services.UserService;
import com.musiclibrary.utils.SessionManager;

public class MusicLibraryApplication {
    private static final int MAX_THREADS = 10;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
    private static UserService userService = new UserService();
    private static SongService songService = new SongService();
    private static PlaylistService playlistService = new PlaylistService();
    private static UserController userController = new UserController(userService, songService, playlistService);
    private static AdminController adminController = new AdminController(userService, songService, playlistService);

    public static void main(String[] args) {
        System.out.println("Welcome to the Music Library Application!");
        // initialize();

        loginUser("test@example.com", "password");
        loginUser("update3@example.com", "password");

        try {
            Thread.sleep(5000);
            logoutUser("test44@example.com");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void initialize() {
        userController.createPlaylist(4, "Chill Vibes");
        userController.createPlaylist(2, "Workout Hits");
        // userController.createPlaylist(3, "Study Tunes");

        adminController.addSong("Summer Nights", "Liam Scott", "Anna Johnson", new Date(System.currentTimeMillis()),
                "Summer Hits");
        adminController.addSong("Winter Dreams", "Ella Rose", "Mark Smith",
                new Date(System.currentTimeMillis() - 1000000000), "Winter Collection");
        adminController.addSong("Rainy Evening", "Sophie Turner", "James Wilson",
                new Date(System.currentTimeMillis() - 500000000), "Rainy Season");
    }

    public static void loginUser(String emailId, String password) {
        threadPool.execute(() -> {
            boolean loginSuccess = userService.authenticateUser(emailId, password);
            if (loginSuccess) {
                SessionManager.startSession(emailId);
                System.out.println(emailId + " logged in successfully.");
            } else {
                System.out.println("Login attempt failed for " + emailId + ". Please check your credentials.");
            }
        });
    }

    public static void logoutUser(String emailId) {
        boolean logoutSuccess = SessionManager.endSession(emailId);
        if (logoutSuccess) {
            System.out.println(emailId + " logged out successfully.");
        } else {
            System.out.println("Logout failed for " + emailId + ". User session could not be found.");
        }
    }
}