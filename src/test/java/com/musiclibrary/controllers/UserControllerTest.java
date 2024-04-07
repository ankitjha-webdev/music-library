package com.musiclibrary.controllers;

import com.musiclibrary.controllers.UserController;
import com.musiclibrary.models.Playlist;
import com.musiclibrary.models.Song;
import com.musiclibrary.services.PlaylistService;
import com.musiclibrary.services.SongService;
import com.musiclibrary.services.UserService;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private SongService songService;
    private PlaylistService playlistService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        songService = new SongService();
        playlistService = new PlaylistService();
        userController = new UserController(userService, songService, playlistService);
    }
    
    @Test
    void viewAllSongsTest() {
        List<Song> songs = userController.viewAllSongs();
        assertNotNull(songs, "The list of songs should not be null.");
        // Additional assertions can be made based on the expected contents of the list
    }

    @Test
    void viewSongDetailsTest() {
        // Assuming there is a song with ID 1 in the database
        Song song = userController.viewSongDetails(1);
        assertNotNull(song, "Details for song with ID 1 should not be null.");
        // Additional assertions can be made based on the expected details of the song
    }

    @Test
    void searchSongTest() {
        // Assuming there is at least one song with the name "Test Song" in the database
        List<Song> songs = userController.searchSong("Test Song");
        assertFalse(songs.isEmpty(), "Search results for 'Test Song' should not be empty.");
        // Additional assertions can be made based on the expected search results
    }

    @Test
    void createPlaylistTest() {
        // Assuming there is a user with ID 1 in the database
        userController.createPlaylist(1, "My Playlist");
        List<Playlist> playlists = userController.viewUserPlaylists(1);
        assertFalse(playlists.isEmpty(), "User with ID 1 should have at least one playlist.");
        // Additional assertions can be made based on the expected contents of the playlist
    }

    @Test
    void addSongToPlaylistTest() {
        // Assuming there is a playlist with ID 1 and a song with ID 1 in the database
        userController.addSongToPlaylist(3, 5);
        List<Song> songs = userController.searchSongInPlaylist(1, "Test Song");
        assertFalse(songs.isEmpty(), "Playlist with ID 1 should contain at least one song after adding.");
    }

    @Test
    void searchSongInPlaylistTest() {
        // Assuming there is a playlist with ID 1 and it contains songs with "Test" in their name
        int playlistId = 1;
        String searchQuery = "Test";
        
        // Perform the search
        List<Song> searchResults = userController.searchSongInPlaylist(playlistId, searchQuery);
        
        // Assert that the search results are not empty and contain expected songs
        assertFalse(searchResults.isEmpty(), "Search results should not be empty.");
        
        // Assert that all songs in the search results contain the search query in their name
        assertTrue(searchResults.stream().allMatch(song -> song.getName().contains(searchQuery)),
                   "All songs in the search results should contain '" + searchQuery + "' in their name.");
    }

}
