package com.musiclibrary.services;

import com.musiclibrary.models.Playlist;
import com.musiclibrary.models.Song;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PlaylistServiceTest {

    private PlaylistService playlistService;

    @BeforeEach
    void setUp() {
        playlistService = new PlaylistService();
    }

    @Test
    void createPlaylistTest() {
        int userId = 4; // Assuming there is a user with ID 4
        String playlistName = "My New Playlist";

        playlistService.createPlaylist(userId, playlistName);
        List<Playlist> playlists = playlistService.getUserPlaylists(userId);

        assertTrue(playlists.stream().anyMatch(playlist -> playlistName.equals(playlist.getName())),
                "Playlist should be created with the specified name.");
    }

    @Test
    void addSongToPlaylistTest() {
        int userId = 4; // Assuming there is a user with ID 4
        int songId = 6; // Assuming there is a song with ID 6
        String playlistName = "My Playlist";

        playlistService.createPlaylist(userId, playlistName);
        List<Playlist> playlists = playlistService.getUserPlaylists(userId);
        Playlist playlist = playlists.stream().filter(p -> p.getName().equals(playlistName)).findFirst().orElse(null);
        assertNotNull(playlist, "Playlist should exist.");
        
        playlistService.addSongToPlaylist(playlist.getPlaylistId(), songId);
        List<Song> songs = playlistService.getSongsInPlaylist(playlist.getPlaylistId()); // Changed method name to getSongsInPlaylist
        
        assertTrue(songs.stream().anyMatch(song -> song.getSongId() == songId),
                "Song should be added to the playlist.");
    }

    @Test
    void removeSongFromPlaylistTest() {
        int userId = 4; // Assuming there is a user with ID 1
        int songId = 6; // Assuming there is a song with ID 2
        String playlistName = "My Playlist";

        playlistService.createPlaylist(userId, playlistName);
        List<Playlist> playlists = playlistService.getUserPlaylists(userId);
        Playlist playlist = playlists.stream().filter(p -> p.getName().equals(playlistName)).findFirst().orElse(null);
        assertNotNull(playlist, "Playlist should exist.");
        
        playlistService.addSongToPlaylist(playlist.getPlaylistId(), songId);
        playlistService.removeSongFromPlaylist(playlist.getPlaylistId(), songId);
        List<Song> songs = playlistService.searchSongInPlaylist(playlist.getPlaylistId(), "");
        
        assertFalse(songs.stream().anyMatch(song -> song.getSongId() == songId),
                "Song should be removed from the playlist.");
    }

    @Test
    void deletePlaylistTest() throws InterruptedException {
        int userId = 4; // Assuming there is a user with ID 1
        String playlistName = "My Playlist";
        final boolean[] isDeleted = {false};

        playlistService.createPlaylist(userId, playlistName);
        List<Playlist> playlists = playlistService.getUserPlaylists(userId);
        Playlist playlist = playlists.stream().filter(p -> p.getName().equals(playlistName)).findFirst().orElse(null);
        assertNotNull(playlist, "Playlist should exist.");
        
        CountDownLatch latch = new CountDownLatch(1);
        playlistService.deletePlaylist(playlist.getPlaylistId(), new PlaylistService.DeletionCallback() {
            @Override
            public void onSuccess() {
                isDeleted[0] = true;
                latch.countDown();
            }

            @Override
            public void onError(Exception e) {
                fail("Deletion failed with exception: " + e.getMessage());
                latch.countDown();
            }
        });
        
        latch.await(5, TimeUnit.SECONDS); // Wait for the callback to be called
        assertTrue(isDeleted[0], "Playlist should be deleted.");
    }

    @Test
    void getPlaylistByNameTest() {
        int userId = 4; // Assuming there is a user with ID 1
        String playlistName = "My Playlist";

        playlistService.createPlaylist(userId, playlistName);
        List<Playlist> playlists = playlistService.getUserPlaylists(userId);
        Playlist playlist = playlists.stream().filter(p -> p.getName().equals(playlistName)).findFirst().orElse(null);
        
        assertNotNull(playlist, "Should retrieve the playlist by name.");
        assertEquals(playlistName, playlist.getName(), "Playlist name should match the requested name.");
    }

    @Test
    void getUserPlaylistsTest() {
        int userId = 4; // Assuming there is a user with ID 1

        playlistService.createPlaylist(userId, "Playlist 1");
        playlistService.createPlaylist(userId, "Playlist 2");
        List<Playlist> playlists = playlistService.getUserPlaylists(userId);

        assertTrue(playlists.size() >= 2, "User should have at least 2 playlists.");
    }
}