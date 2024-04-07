package com.musiclibrary.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/music_library";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());

    private static Connection connection = createConnection();

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect to database", e);
            return null;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
