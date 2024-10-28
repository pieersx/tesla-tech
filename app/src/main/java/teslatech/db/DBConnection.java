package teslatech.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/tesla_tech_db";
    public static final String USER = "pieers";
    public static final String PASSWORD = "";

    public static Connection connectDB() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return connection;
    }
}
