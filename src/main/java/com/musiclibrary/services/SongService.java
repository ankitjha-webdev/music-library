package com.musiclibrary.services;

import com.musiclibrary.dao.SongDao;
import com.musiclibrary.models.Song;
import com.musiclibrary.exceptions.DatabaseOperationException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SongService {
    private final SongDao songDao;

    public SongService() {
        this.songDao = new SongDao();
    }

    // Method to add a new song
    public void addSong(String name, String singer, String musicDirector, Date releaseDate, String albumName) {
        // Check for null or empty values for all parameters
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Song name cannot be null or empty.");
        }
        if (singer == null || singer.trim().isEmpty()) {
            throw new IllegalArgumentException("Singer name cannot be null or empty.");
        }
        if (musicDirector == null || musicDirector.trim().isEmpty()) {
            throw new IllegalArgumentException("Music Director name cannot be null or empty.");
        }
        if (releaseDate == null) {
            throw new IllegalArgumentException("Release date cannot be null.");
        }
        if (albumName == null || albumName.trim().isEmpty()) {
            throw new IllegalArgumentException("Album name cannot be null or empty.");
        }

        try {
            Song song = new Song();
            song.setName(name);
            song.setSinger(singer);
            song.setMusicDirector(musicDirector);
            song.setReleaseDate(releaseDate);
            song.setAlbumName(albumName);
            songDao.addSong(song);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to add song", e);
        }
    }

    // Method to update an existing song
    public void updateSong(int songId, String name, String singer, String musicDirector, Date releaseDate,
            String albumName) {
        try {
            Song existingSong = songDao.getSongDetails(songId);
            if (existingSong == null) {
                throw new IllegalArgumentException("Song with ID " + songId + " does not exist.");
            }
            Song song = new Song();
            song.setSongId(songId);
            song.setName(name);
            song.setSinger(singer);
            song.setMusicDirector(musicDirector);
            song.setReleaseDate(releaseDate);
            song.setAlbumName(albumName);
            songDao.updateSong(song);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update song", e);
        }
    }

    // Method to delete an existing song
    public void deleteSong(int songId) {
        try {
            Song existingSong = songDao.getSongDetails(songId);
            if (existingSong == null) {
                throw new IllegalArgumentException("Song with ID " + songId + " does not exist.");
            }
            songDao.deleteSong(songId);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete song", e);
        }
    }

    // Method to retrieve all songs
    public List<Song> getAllSongs() {
        try {
            return songDao.getAllSongs();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve all songs", e);
        }
    }

    // Method to search for songs by name
    public List<Song> searchSongByName(String name) {
        try {
            List<Song> songs = songDao.searchSongByName(name);
            return songs.stream()
                    .filter(song -> song.getName().contains(name))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to search song by name", e);
        }
    }

    // Method to restrict visibility of a song
    public void restrictSongVisibility(int songId, boolean visibility) {
        try {
            Song song = songDao.getSongDetails(songId);
            if (song != null) {
                song.setRestricted(visibility);
                songDao.updateSong(song);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to restrict song visibility", e);
        }
    }

    // Method to get details of a song by ID
    public Song getSongDetails(int songId) {
        try {
            return songDao.getSongDetails(songId);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get song details", e);
        }
    }
}
