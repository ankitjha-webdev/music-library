package com.musiclibrary.models;

import java.sql.Date;

public class Song {
    private int songId;
    private String name;
    private String singer;
    private String musicDirector;
    private Date releaseDate;
    private String albumName;
    private boolean visibility;

    // Getters and setters

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getMusicDirector() {
        return musicDirector;
    }

    public void setMusicDirector(String musicDirector) {
        this.musicDirector = musicDirector;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public boolean isRestricted() {
        return visibility;
    }

    public void setRestricted(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", musicDirector='" + musicDirector + '\'' +
                ", releaseDate=" + releaseDate +
                ", albumName='" + albumName + '\'' +
                ", visibility=" + visibility +
                '}';
    }
}
