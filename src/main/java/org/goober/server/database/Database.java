package org.goober.server.database;

import org.goober.server.model.PasswordVaultServerModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class Database {

    private static final String URL = "jdbc:mariadb://localhost:3306/dbname";
    private static final String USER = "dbuser";
    private static final String PASSWORD = "dbpassword";

    public Database() {
    }

    // Method to register a user
    public static int registerUser(String username, String authKey, String authSalt, String vaultSalt, String iv, String vault) throws SQLException {
        String sql = "INSERT INTO users (username, auth_key, salt) VALUES (?, ?, ?)";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);            // Set username
        statement.setString(2, authKey);       // Set hashed password
        statement.setBytes(3, Base64.getDecoder().decode(authSalt));
        statement.executeUpdate();

        String sql2 = "INSERT INTO vaults (encrypted_vault, auth_key_id, salt, iv) VALUES (?, ?, ?, ?)";
        Connection connection2 = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement2 = connection2.prepareStatement(sql2);
        statement2.setString(1, vault);
        statement2.setString(2, authKey);
        statement2.setBytes(3, Base64.getDecoder().decode(vaultSalt));
        statement2.setBytes(4, Base64.getDecoder().decode(iv));
        return statement2.executeUpdate();   // Execute insert

        }

    public static void storeEncryptedVault(String authKey, String encryptedVault) throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "UPDATE vaults SET encrypted_vault = ? WHERE auth_key_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, encryptedVault);
                statement.setString(2, authKey);
                statement.executeUpdate();
            }
        }
    }

    public static String retrieveEncryptedVault(String authKey) throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT encrypted_vault FROM vaults WHERE auth_key_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, authKey);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("encrypted_vault");
                    }
                    else throw new Exception("Retrieving query did not go well. Retry."); // Handle not found case appropriately
                }
            }
        }
    }

    public static String getUserAuthSalt(String username) throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT salt FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return Base64.getEncoder().encodeToString(resultSet.getBytes("salt"));
                    }
                    else throw new Exception("Retrieving query did not go well. Retry."); // Handle not found case appropriately
                }
            }
        }
    }

    public static String getUserVaultSalt(String username) throws Exception {

        String authKey = getUserAuthKey(username);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT salt FROM vaults WHERE auth_key_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, authKey);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return Base64.getEncoder().encodeToString(resultSet.getBytes("salt"));
                    }
                    else throw new Exception("Retrieving query did not go well. Retry."); // Handle not found case appropriately
                }
            }
        }
    }


    public static String getUserVaultIv(String username) throws Exception {

        String authKey = getUserAuthKey(username);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT iv FROM vaults WHERE auth_key_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, authKey);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return Base64.getEncoder().encodeToString(resultSet.getBytes("iv"));
                    }
                    else throw new Exception("Retrieving query did not go well. Retry."); // Handle not found case appropriately
                }
            }
        }
    }

    private static String getUserAuthKey(String username) throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT auth_key FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("auth_key");
                    }
                    else throw new Exception("Retrieving query did not go well. Retry."); // Handle not found case appropriately
                }
            }
        }
    }
}

