package com.musiclibrary.services;

import com.musiclibrary.dao.PlaylistDao;
import com.musiclibrary.models.Playlist;
import com.musiclibrary.models.Song;
import com.musiclibrary.exceptions.DatabaseOperationException;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.CountDownLatch;

public class PlaylistService {
    private final PlaylistDao playlistDao;
    private final UserService userService;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public PlaylistService() {
        this.playlistDao = new PlaylistDao();
        this.userService = new UserService();
    }

    // Method to create a new playlist
    public void createPlaylist(int userId, String name) {
        threadPool.execute(() -> {
            try {
                if (!userService.doesUserExist(userId)) {
                    throw new SQLException("User does not exist with ID: " + userId);
                }
                Playlist playlist = new Playlist();
                playlist.setUserId(userId);
                playlist.setName(name);
                playlistDao.createPlaylist(playlist);
            } catch (SQLException e) {
                throw new DatabaseOperationException("Failed to create playlist", e);
            }
        });
    }

    // Method to retrieve all playlists of a user
    public List<Playlist> getUserPlaylists(int userId) {
        try {
            return playlistDao.getUserPlaylists(userId);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve user playlists", e);
        }
    }

    // Method to add a song to a playlist
    public void addSongToPlaylist(int playlistId, int songId) {
        threadPool.execute(() -> {
            try {
                playlistDao.addSongToPlaylist(playlistId, songId);
            } catch (SQLException e) {
                throw new DatabaseOperationException("Failed to add song to playlist", e);
            }
        });
    }

    // Method to search for songs in a playlist by name
    public List<Song> searchSongInPlaylist(int playlistId, String name) {
        try {
            return playlistDao.searchSongInPlaylist(playlistId, name);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to search song in playlist", e);
        }
    }

    // Method to remove a song from a playlist
    public void removeSongFromPlaylist(int playlistId, int songId) {
        threadPool.execute(() -> {
            try {
                playlistDao.removeSongFromPlaylist(playlistId, songId);
            } catch (SQLException e) {
                throw new DatabaseOperationException("Failed to remove song from playlist", e);
            }
        });
    }

    public interface DeletionCallback {
        void onSuccess();
        void onError(Exception e);
    }

    // Method to delete a playlist
    public void deletePlaylist(int playlistId, DeletionCallback callback) {
        threadPool.execute(() -> {
            try {
                playlistDao.deletePlaylist(playlistId);
                callback.onSuccess();
            } catch (SQLException e) {
                callback.onError(e);
            }
        });
    }

    // Method to get all songs in a playlist
    public List<Song> getSongsInPlaylist(int playlistId) {
        try {
            return playlistDao.getSongsInPlaylist(playlistId);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get songs from playlist", e);
        }
    }
}