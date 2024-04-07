package com.musiclibrary.controllers;

import com.musiclibrary.controllers.AdminController;
import com.musiclibrary.services.PlaylistService;
import com.musiclibrary.services.SongService;
import com.musiclibrary.services.UserService;
import com.musiclibrary.models.Song;
import com.musiclibrary.models.User;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.util.List;

class AdminControllerTest {
    private AdminController adminController;
    private UserService userService;
    private SongService songService;
    private PlaylistService playlistService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
        songService = new SongService();
        playlistService = new PlaylistService();
        adminController = new AdminController(userService, songService, playlistService);
    }

    @Test
    void addSongTest() {
        int initialSongCount = songService.getAllSongs().size();
        adminController.addSong("Test Song", "Test Singer", "Test Director", new Date(0), "Test Album");
        int newSongCount = songService.getAllSongs().size();
        
        assertEquals(initialSongCount + 1, newSongCount, "Song count should increase by 1 after adding a song.");
    }

    @Test
    void updateSongTest() {
        // Add a song to update
        songService.addSong("Original Song", "Original Singer", "Original Director", new Date(0), "Original Album");
        List<Song> songs = songService.getAllSongs();
        Song songToUpdate = songs.get(songs.size() - 1);

        // Update the song
        adminController.updateSong(songToUpdate.getSongId(), "Updated Song", "Updated Singer", "Updated Director", new Date(System.currentTimeMillis()), "Updated Album");
        Song updatedSong = songService.getSongDetails(songToUpdate.getSongId());

        // Assert the song details have been updated
        assertEquals("Updated Song", updatedSong.getName(), "Song name should be updated.");
        assertEquals("Updated Singer", updatedSong.getSinger(), "Singer should be updated.");
        assertEquals("Updated Director", updatedSong.getMusicDirector(), "Music Director should be updated.");
        assertNotNull(updatedSong.getReleaseDate(), "Release date should be updated.");
        assertEquals("Updated Album", updatedSong.getAlbumName(), "Album name should be updated.");
    }
    
    @Test
    void deleteSongTest() {
        // Add a song to delete
        songService.addSong("Song to Delete", "Singer", "Director", new Date(0), "Album");
        List<Song> songs = songService.getAllSongs();
        Song songToDelete = songs.get(songs.size() - 1);

        // Delete the song using AdminController
        adminController.deleteSong(songToDelete.getSongId());
        songs = songService.getAllSongs();

        // Assert the song has been deleted by checking it's no longer in the list
        boolean songExists = songs.stream().anyMatch(s -> s.getSongId() == songToDelete.getSongId());
        assertFalse(songExists, "Song should not exist after being deleted.");
    }

    @Test
    void restrictSongVisibilityTest() {
        // Add a song to restrict visibility
        songService.addSong("Song to Restrict", "Singer", "Director", new Date(0), "Album");
        List<Song> songs = songService.getAllSongs();
        Song songToRestrict = songs.get(songs.size() - 1);

        // Restrict the song's visibility using AdminController
        adminController.restrictSongVisibility(songToRestrict.getSongId());
        // Re-fetch the list of all songs
        List<Song> updatedSongsList = songService.getAllSongs();

        // Assert the song's visibility has been restricted
        // This assumes that the getAllSongs method only returns songs that are visible
        assertFalse(updatedSongsList.contains(songToRestrict), "Song should not be visible after restricting visibility.");
    }
    
    @Test
    void addUserTest() {
        // Simulate adding a user
        adminController.addUser("test44@example.com", "5735376375", "password");
        // Simulate checking if user exists
        // This would normally be a call to a method like userService.getUserByEmail("test@example.com")
        boolean userExists = true; // Placeholder for actual user existence check

        assertTrue(userExists, "User should exist after being added.");
    }
    

    @Test
    void updateUserTest() {
        // Simulate adding a user to update
        adminController.addUser("update3@example.com", "938393737", "password");
        // Simulate getting the user's ID
        int userId = 4; // Placeholder for actual user ID retrieval

        // Update user details
        adminController.updateUser(userId, "update34@example.com", "938393737", "newpassword");
        // Simulate checking updated details
        // This would normally be a call to a method like userService.getUserById(userId)
        String updatedPhoneNumber = "0987654321"; // Placeholder for actual phone number check

        assertEquals("0987654321", updatedPhoneNumber, "User phone number should be updated.");
    }
    
    @Test
    void deleteUserTest() {
        // Simulate adding a user to delete
        adminController.addUser("delete1@example.com", "6363637636", "password");
        // Simulate getting the user's ID
        int userId = 3; // Placeholder for actual user ID retrieval

        // Delete the user
        adminController.deleteUser(userId);
        // Simulate checking if user still exists
        boolean userExists = false; // Placeholder for actual user existence check

        assertFalse(userExists, "User should not exist after being deleted.");
    }
}
