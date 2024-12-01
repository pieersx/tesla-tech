package teslatech.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Constantes para la URL de la base de datos y las credenciales
    public static final String URL = "";
    public static final String USUARIO = "";
    public static final String CONTRASENA = "";

    public static Connection conectarDB() {
        Connection connection = null;
        try {
            // Cargar el controlador JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión a la base de datos
            connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException | SQLException  e) {
            // Captura los errores de conexión y los muestra
            e.printStackTrace();
        }
        return connection;
    }
}
