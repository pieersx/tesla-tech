package teslatech.models;

import teslatech.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public static boolean isValidUser(String username, String password) {
        String query = "SELECT username, password FROM users WHERE username = ? and password = ?";
        Connection connect = DBConnection.connectDB();
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setString(1, username);
            prepare.setString(2, password);
            ResultSet result = prepare.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUsernameTaken(String username) {
        String query = "SELECT username FROM users WHERE username = ?";
        Connection connect = DBConnection.connectDB();
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setString(1, username);
            ResultSet result = prepare.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String username, String password, String question, String answer) {
        String query = "INSERT INTO users (username, password, question, answer, date) VALUES (?, ?, ?, ?, CURRENT_DATE)";
        Connection connect = DBConnection.connectDB();
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setString(1, username);
            prepare.setString(2, password);
            prepare.setString(3, question);
            prepare.setString(4, answer);
            prepare.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para validar datos de recuperación de contraseña
    public static boolean isValidRecoveryData(String username, String question, String answer) {
        String query = "SELECT username FROM users WHERE username = ? AND question = ? AND answer = ?";
        Connection connect = DBConnection.connectDB();
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setString(1, username);
            prepare.setString(2, question);
            prepare.setString(3, answer);
            ResultSet result = prepare.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar la contraseña de un usuario
    public static boolean updateUserPassword(String username, String newPassword, String question, String answer) {
        String query = "UPDATE users SET password = ?, question = ?, answer = ?, date = CURRENT_DATE WHERE username = ?";
        Connection connect = DBConnection.connectDB();
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setString(1, newPassword);
            prepare.setString(2, question);
            prepare.setString(3, answer);
            prepare.setString(4, username);
            int rowsUpdated = prepare.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        connect = DBConnection.connectDB();
        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, newPassword);
            prepare.setString(2, username);
            prepare.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
