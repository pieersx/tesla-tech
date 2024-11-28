package teslatech.services;

import teslatech.Producto;
import teslatech.db.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class ServicioProducto {
    public static ObservableList<Producto> obtenerListaProductos() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        String consulta = "SELECT * FROM producto";

        try {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta);
            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();

            while (resultadoConsulta .next()) {
                Producto producto = new Producto(
                    resultadoConsulta .getInt("id"),
                    resultadoConsulta .getString("id_producto"),
                    resultadoConsulta .getString("nombre_producto"),
                    resultadoConsulta .getString("tipo"),
                    resultadoConsulta .getInt("stock"),
                    resultadoConsulta .getDouble("precio"),
                    resultadoConsulta .getString("estado"),
                    resultadoConsulta .getString("imagen"),
                    resultadoConsulta .getDate("fecha")
                );

                listaProductos.add(producto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos;
    }

    public static void agregarProducto(Producto product) {
        String consulta = "INSERT INTO producto (id_producto, nombre_producto, tipo, stock, precio, estado, imagen, fecha) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try  {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta);
            consultaPreparada.setString(1, product.getProductId());
            consultaPreparada.setString(2, product.getProductName());
            consultaPreparada.setString(3, product.getTipo());
            consultaPreparada.setInt(4, product.getStock());
            consultaPreparada.setDouble(5, product.getPrecio());
            consultaPreparada.setString(6, product.getEstado());
            consultaPreparada.setString(7, product.getImagen());
            consultaPreparada.setDate(8, product.getFecha());

            consultaPreparada.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean existeProducto(String productId) {
        String consultaSQL  = "SELECT id_producto FROM producto WHERE id_producto = '" + productId + "'";
        try {
            Connection conexionDB = ConexionDB.conectarDB();
            Statement statement = conexionDB.createStatement();
            ResultSet resultadoConsulta  = statement.executeQuery(consultaSQL );

            return resultadoConsulta .next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateProduct(Producto product) {
        String consulta = "UPDATE producto SET id_producto = ?, nombre_producto = ?, tipo = ?, stock = ?, precio = ?, estado = ?, imagen = ?, fecha = ? WHERE id = ?";
        try (Connection conexionDB = ConexionDB.conectarDB();
             PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta)) {

            consultaPreparada.setString(1, product.getProductId());
            consultaPreparada.setString(2, product.getProductName());
            consultaPreparada.setString(3, product.getTipo());
            consultaPreparada.setInt(4, product.getStock());
            consultaPreparada.setDouble(5, product.getPrecio());
            consultaPreparada.setString(6, product.getEstado());
            consultaPreparada.setString(7, product.getImagen());
            consultaPreparada.setDate(8, new java.sql.Date(product.getFecha().getTime()));
            consultaPreparada.setInt(9, product.getId());

            consultaPreparada.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String consulta = "DELETE FROM producto WHERE id = ?";
        try (Connection conexionDB = ConexionDB.conectarDB();
             PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta)) {

            consultaPreparada.setInt(1, productId);
            consultaPreparada.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
