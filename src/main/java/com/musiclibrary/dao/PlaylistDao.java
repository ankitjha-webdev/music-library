package com.musiclibrary.dao;

import com.musiclibrary.models.Playlist;
import com.musiclibrary.models.Song;
import com.musiclibrary.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDao {
    private final Connection connection;

    public PlaylistDao() {
        this.connection = DatabaseUtil.getConnection();
    }

    // Method to create a new play-list
    public void createPlaylist(Playlist playlist) throws SQLException {
        String query = "INSERT INTO playlist (userId, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlist.getUserId());
            statement.setString(2, playlist.getName());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all play-lists of a user
    public List<Playlist> getUserPlaylists(int userId) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlist WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    playlists.add(extractPlaylistFromResultSet(resultSet));
                }
            }
        }
        return playlists;
    }

    // Method to extract Play-list object from ResultSet
    private Playlist extractPlaylistFromResultSet(ResultSet resultSet) throws SQLException {
        Playlist playlist = new Playlist();
        playlist.setPlaylistId(resultSet.getInt("playlistId"));
        playlist.setUserId(resultSet.getInt("userId"));
        playlist.setName(resultSet.getString("name"));
        return playlist;
    }

    // Method to add a song to a playlist
    public void addSongToPlaylist(int playlistId, int songId) throws SQLException {
        String query = "INSERT INTO playlistsong (playlistId, songId) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlistId);
            statement.setInt(2, songId);
            statement.executeUpdate();
        }
    }

    // Method to search for songs in a play-list by name
    public List<Song> searchSongInPlaylist(int playlistId, String name) throws SQLException {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT s.* FROM song s JOIN playlistsong ps ON s.songId = ps.songId WHERE ps.playlistId = ? AND s.name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlistId);
            statement.setString(2, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Song song = new Song();
                    song.setSongId(resultSet.getInt("songId"));
                    song.setName(resultSet.getString("name"));
                    song.setSinger(resultSet.getString("singer"));
                    song.setMusicDirector(resultSet.getString("musicDirector"));
                    song.setReleaseDate(resultSet.getDate("releaseDate"));
                    song.setAlbumName(resultSet.getString("albumName"));
                    songs.add(song);
                }
            }
        }
        return songs;
    }

    // deletePlaylistsByUserId
    public void deletePlaylistsByUserId(int userId) throws SQLException {
        String query = "DELETE FROM playlist WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    // Method to remove a song from a playlist
    public void removeSongFromPlaylist(int playlistId, int songId) throws SQLException {
        String query = "DELETE FROM playlistsong WHERE playlistId = ? AND songId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlistId);
            statement.setInt(2, songId);
            statement.executeUpdate();
        }
    }

    // Method to delete a playlist
    public void deletePlaylist(int playlistId) throws SQLException {
        // First, remove all songs from the playlist to maintain referential integrity
        String removeSongsQuery = "DELETE FROM playlistsong WHERE playlistId = ?";
        try (PreparedStatement statement = connection.prepareStatement(removeSongsQuery)) {
            statement.setInt(1, playlistId);
            statement.executeUpdate();
        }
        // Then, delete the playlist itself
        String deletePlaylistQuery = "DELETE FROM playlist WHERE playlistId = ?";
        try (PreparedStatement statement = connection.prepareStatement(deletePlaylistQuery)) {
            statement.setInt(1, playlistId);
            statement.executeUpdate();
        }
    }

    // Method to get all songs in a playlist
    public List<Song> getSongsInPlaylist(int playlistId) throws SQLException {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT s.songId, s.name, s.singer, s.musicDirector, s.releaseDate, s.albumName FROM song s INNER JOIN playlistsong ps ON s.songId = ps.songId WHERE ps.playlistId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlistId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Song song = new Song();
                    song.setSongId(resultSet.getInt("songId"));
                    song.setName(resultSet.getString("name"));
                    song.setSinger(resultSet.getString("singer"));
                    song.setMusicDirector(resultSet.getString("musicDirector"));
                    song.setReleaseDate(resultSet.getDate("releaseDate"));
                    song.setAlbumName(resultSet.getString("albumName"));
                    songs.add(song);
                }
            }
        }
        return songs;
    }
}
