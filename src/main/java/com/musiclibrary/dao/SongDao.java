package com.musiclibrary.dao;

import com.musiclibrary.models.Song;
import com.musiclibrary.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDao {
 private final Connection connection;

 public SongDao() {
     this.connection = DatabaseUtil.getConnection();
 }

 // Method to add a new song
 public void addSong(Song song) throws SQLException {
     String query = "INSERT INTO Song (name, singer, musicDirector, releaseDate, albumName) VALUES (?, ?, ?, ?, ?)";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, song.getName());
         statement.setString(2, song.getSinger());
         statement.setString(3, song.getMusicDirector());
         statement.setDate(4, song.getReleaseDate());
         statement.setString(5, song.getAlbumName());
         statement.executeUpdate();
     }
 }

 // Method to update an existing song
 public void updateSong(Song song) throws SQLException {
     String query = "UPDATE Song SET name = ?, singer = ?, musicDirector = ?, releaseDate = ?, albumName = ?, visibility = ? WHERE songId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, song.getName());
         statement.setString(2, song.getSinger());
         statement.setString(3, song.getMusicDirector());
         statement.setDate(4, song.getReleaseDate());
         statement.setString(5, song.getAlbumName());
         statement.setBoolean(6, song.isRestricted());
         statement.setInt(7, song.getSongId());
         statement.executeUpdate();
     }
 }

 // Method to delete an existing song
 public void deleteSong(int songId) throws SQLException {
     String query = "DELETE FROM Song WHERE songId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setInt(1, songId);
         statement.executeUpdate();
     }
 }

 // Method to retrieve all songs
 public List<Song> getAllSongs() throws SQLException {
     List<Song> songs = new ArrayList<>();
     String query = "SELECT * FROM Song";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         try (ResultSet resultSet = statement.executeQuery()) {
             while (resultSet.next()) {
                 songs.add(extractSongFromResultSet(resultSet));
             }
         }
     }
     return songs;
 }

 // Method to search for songs by name
 public List<Song> searchSongByName(String name) throws SQLException {
     List<Song> songs = new ArrayList<>();
     String query = "SELECT * FROM Song WHERE name LIKE ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, "%" + name + "%");
         try (ResultSet resultSet = statement.executeQuery()) {
             while (resultSet.next()) {
                 songs.add(extractSongFromResultSet(resultSet));
             }
         }
     }
     return songs;
 }

 // Method to extract Song object from ResultSet
 private Song extractSongFromResultSet(ResultSet resultSet) throws SQLException {
     Song song = new Song();
     song.setSongId(resultSet.getInt("songId"));
     song.setName(resultSet.getString("name"));
     song.setSinger(resultSet.getString("singer"));
     song.setMusicDirector(resultSet.getString("musicDirector"));
     song.setReleaseDate(resultSet.getDate("releaseDate"));
     song.setAlbumName(resultSet.getString("albumName"));
     return song;
 }
 
 // Method to restrict visibility of a song
 public void restrictSongVisibility(int songId, boolean visibility) throws SQLException {
     String query = "UPDATE Song SET visibility = ? WHERE songId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setBoolean(1, visibility);
         statement.setInt(2, songId);
         statement.executeUpdate();
     }
 }
 
 // Method to get details of a song by ID
 public Song getSongDetails(int songId) throws SQLException {
     String query = "SELECT * FROM Song WHERE songId = ?";
     try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setInt(1, songId);
         try (ResultSet resultSet = statement.executeQuery()) {
             if (resultSet.next()) {
                 Song song = new Song();
                 song.setSongId(resultSet.getInt("songId"));
                 song.setName(resultSet.getString("name"));
                 song.setSinger(resultSet.getString("singer"));
                 song.setMusicDirector(resultSet.getString("musicDirector"));
                 song.setReleaseDate(resultSet.getDate("releaseDate"));
                 song.setAlbumName(resultSet.getString("albumName"));
                 song.setRestricted(resultSet.getBoolean("visibility"));
                 return song;
             }
         }
     }
     return null; // Return null if no song with the given ID is found
 }
}
