package teslatech.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {
    public static final String URL = "jdbc:mysql://localhost:3306/tesla_tech_db";
    public static final String USUARIO = "pieers";
    public static final String CONTRASENA = "";

    public static Connection conectarDB() {
        Connection connection = null;
        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return connection;
    }
}
