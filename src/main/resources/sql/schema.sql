 create database music_library;
 use music_library;

-- User Table
CREATE TABLE User (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    emailId VARCHAR(255) UNIQUE NOT NULL,
    phoneNumber VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Song Table
CREATE TABLE Song (
    songId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    singer VARCHAR(255) NOT NULL,
    musicDirector VARCHAR(255) NOT NULL,
    releaseDate DATE NOT NULL,
    albumName VARCHAR(255),
    visibility BOOLEAN DEFAULT TRUE
);

-- Playlist Table
CREATE TABLE Playlist (
    playlistId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (userId) REFERENCES User(userId)
);

-- PlaylistSong Table (Many-to-Many Relationship)
CREATE TABLE PlaylistSong (
    playlistId INT,
    songId INT,
    PRIMARY KEY (playlistId, songId),
    FOREIGN KEY (playlistId) REFERENCES Playlist(playlistId),
    FOREIGN KEY (songId) REFERENCES Song(songId)
);