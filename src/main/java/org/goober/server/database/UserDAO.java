package org.goober.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "DBUSER";
    private static final String DB_PASSWORD = "DBPASSWORD";

    // Method to register a user
    public int registerUser(String username, String hashedPassword, String salt) throws SQLException {
        String sql = "INSERT INTO user (username, password_hash, salt) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);            // Set username
            statement.setString(2, hashedPassword);       // Set hashed password
            statement.setString(3, salt);                 // Set salt
            return statement.executeUpdate();                    // Execute insert
        }
    }

    // Other methods like updateUser, getUserById, deleteUser, etc., can be added here
}
