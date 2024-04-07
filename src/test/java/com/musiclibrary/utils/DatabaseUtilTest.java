package com.musiclibrary.utils;

import com.musiclibrary.utils.DatabaseUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUtilTest {

    private static Connection connection;

    @BeforeAll
    public static void setUpClass() {
        // Set up the test database connection
        connection = DatabaseUtil.getConnection();
    }

    @Test
    public void getConnection_ShouldReturnValidConnection() {
        // Assert
        assertNotNull(connection, "The connection should not be null");
    }

    @Test
    public void getConnection_ShouldBeAbleToExecuteQuery() {
        // Arrange
        String query = "SELECT 1";

        // Act & Assert
        try (Statement statement = connection.createStatement()) {
            assertTrue(statement.execute(query), "Should be able to execute query");
        } catch (SQLException e) {
            fail("Should be able to create a statement and execute query");
        }
    }

    @AfterAll
    public static void tearDownClass() {
        // Close the test database connection
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
