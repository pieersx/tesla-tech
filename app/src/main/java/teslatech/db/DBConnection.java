package teslatech.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static final String URL = "jdbc:mysql://autorack.proxy.rlwy.net:54386/tesla_tech_db";
    public static final String USER = "root";
    public static final String PASSWORD = "boSfRwFPvOHlElpHSFRvOtiWhFJIEsmG";

    public static Connection connectDB() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return connection;
    }
}
