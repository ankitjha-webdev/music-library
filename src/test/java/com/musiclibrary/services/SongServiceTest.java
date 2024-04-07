package com.musiclibrary.services;

import com.musiclibrary.models.Song;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

class SongServiceTest {
    private SongService songService;

    @BeforeEach
    void setUp() {
        songService = new SongService();
    }

    @Test
    void addSongTest() {
        String name = "New Song";
        songService.addSong(name, "Singer", "Director", new Date(System.currentTimeMillis()), "Album");
        List<Song> songs = songService.getAllSongs();

        assertTrue(songs.stream().anyMatch(song -> name.equals(song.getName())),
                "New song should be added with the specified name.");
    }

    @Test
    void updateSongTest() {
        int songId = 2;
        String updatedName = "Updated Song";
        songService.updateSong(songId, updatedName, "Updated Singer", "Updated Director",
                new Date(System.currentTimeMillis()), "Updated Album");
        Song song = songService.getSongDetails(songId);

        assertNotNull(song, "Song should not be null.");
        assertEquals(updatedName, song.getName(), "Song name should be updated.");
    }

    @Test
    void deleteSongTest() {
        // Assuming there is a song with ID 1
        int songId = 2;
        songService.deleteSong(songId);
        Song song = songService.getSongDetails(songId);

        assertNull(song, "Song should be deleted and no longer retrievable.");
    }

    @Test
    void getAllSongsTest() {
        List<Song> songs = songService.getAllSongs();
        assertNotNull(songs, "Should return a list of songs, not null.");
    }

    @Test
    void searchSongByNameTest() {
        String searchQuery = "Test Song";
        List<Song> songs = songService.searchSongByName(searchQuery);
        assertFalse(songs.isEmpty(), "Search results should not be empty.");
    }

    @Test
    void restrictSongVisibilityTest() {
        int songId = 7;
        songService.restrictSongVisibility(songId, true);
        Song song = songService.getSongDetails(songId);

        assertTrue(song.isRestricted(), "Song visibility should be restricted.");
    }

    @Test
    void unrestrictSongVisibilityTest() {
        int songId = 7;
        songService.restrictSongVisibility(songId, false); // Test case for making a song visible again
        Song song = songService.getSongDetails(songId);

        assertFalse(song.isRestricted(), "Song visibility should be unrestricted.");
    }

    @Test
    void getSongDetailsTest() {
        // Assuming there is a song with ID 1
        int songId = 2;
        Song song = songService.getSongDetails(songId);
        assertNotNull(song, "Should be able to retrieve details of a song.");
    }

    @Test
    void addSongWithMissingDetailsTest() {
        // Test case for adding a song with missing details
        assertThrows(IllegalArgumentException.class, () -> {
            songService.addSong("New Song", null, "Director", new Date(System.currentTimeMillis()), "Album");
        }, "Adding a song with missing name should throw IllegalArgumentException.");
    }

    @Test
    void updateNonExistentSongTest() {
        // Test case for updating a non-existent song
        int nonExistentSongId = 999; // Assuming this ID does not exist
        assertThrows(IllegalArgumentException.class, () -> {
            songService.updateSong(nonExistentSongId, "Non-existent Song", "Singer", "Director",
                    new Date(System.currentTimeMillis()), "Album");
        }, "Updating a non-existent song should throw IllegalArgumentException.");
    }

    @Test
    void deleteNonExistentSongTest() {
        // Test case for deleting a non-existent song
        int nonExistentSongId = 999; // Assuming this ID does not exist
        assertThrows(IllegalArgumentException.class, () -> {
            songService.deleteSong(nonExistentSongId);
        }, "Deleting a non-existent song should throw IllegalArgumentException.");
    }
}
