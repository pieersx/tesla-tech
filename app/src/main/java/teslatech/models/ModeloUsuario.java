package teslatech.models;

import teslatech.db.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloUsuario {
    // Método para validar si un usuario y su contraseña son correctos
    public static boolean esUsuarioValido(String usuario, String contrasena) {
        String consultaSQL  = "SELECT usuario, contrasena FROM usuario WHERE usuario = ? and contrasena = ?";

        try {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, usuario);
            consultaPreparada.setString(2, contrasena);
            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();

            return resultadoConsulta .next(); // Devuelve true si se encuentra el usuario
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // Método para verificar si un nombre de usuario ya está registrado
    public static boolean verificarNombreUsuarioExistente(String usuario) {
        String consultaSQL  = "SELECT usuario FROM usuario WHERE usuario = ?";

        try {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consultaSQL );
            consultaPreparada.setString(1, usuario);
            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();

            return resultadoConsulta .next(); // Devuelve true si se encuentra el usuario
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // Método para registrar un nuevo usuario
    public static boolean registrarNuevoUsuario(String usuario, String contrasena, String pregunta, String respuesta) {
        String consultaSQL  = "INSERT INTO usuario (usuario, contrasena, pregunta, respuesta, fecha) VALUES (?, ?, ?, ?, CURRENT_DATE)";

        try {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consultaSQL );
            consultaPreparada.setString(1, usuario);
            consultaPreparada.setString(2, contrasena);
            consultaPreparada.setString(3, pregunta);
            consultaPreparada.setString(4, respuesta);
            consultaPreparada.executeUpdate();

            return true; // Registro exitoso
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // Método para validar datos de recuperación de contraseña
    public static boolean validarDatosRecuperacion(String usuario, String pregunta, String respuesta) {
        String consultaSQL  = "SELECT usuario FROM usuario WHERE usuario = ? AND pregunta = ? AND respuesta = ?";

        try {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consultaSQL );
            consultaPreparada.setString(1, usuario);
            consultaPreparada.setString(2, pregunta);
            consultaPreparada.setString(3, respuesta);
            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();

            return resultadoConsulta .next(); // Devuelve true si los datos son válidos
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // Método para actualizar la contraseña de un usuario
    public static boolean actualizarContrasenaUsuario(String usuario, String nuevaContrasena, String pregunta, String respuesta) {
        String consultaSQL  = "UPDATE usuario SET contrasena = ?, pregunta = ?, respuesta = ?, fecha = CURRENT_DATE WHERE usuario = ?";

        try {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consultaSQL );
            consultaPreparada.setString(1, nuevaContrasena);
            consultaPreparada.setString(2, pregunta);
            consultaPreparada.setString(3, respuesta);
            consultaPreparada.setString(4, usuario);

            int filasActualizada  = consultaPreparada.executeUpdate();
            return filasActualizada > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public boolean updatePassword(String usuario, String nuevaContrasena) {
        String consultaSQL  = "UPDATE usuario SET contrasena = ? WHERE usuario = ?";
        Connection conexionDB = ConexionDB.conectarDB();
        try {
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consultaSQL );
            consultaPreparada.setString(1, nuevaContrasena);
            consultaPreparada.setString(2, usuario);
            consultaPreparada.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
