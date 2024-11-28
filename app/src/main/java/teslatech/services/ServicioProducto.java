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
        String consulta = "SELECT * FROM product";

        try {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta);
            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();

            while (resultadoConsulta .next()) {
                Producto producto = new Producto(
                    resultadoConsulta .getInt("id"),
                    resultadoConsulta .getString("prod_id"),
                    resultadoConsulta .getString("prod_name"),
                    resultadoConsulta .getString("type"),
                    resultadoConsulta .getInt("stock"),
                    resultadoConsulta .getDouble("price"),
                    resultadoConsulta .getString("status"),
                    resultadoConsulta .getString("image"),
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
        String consulta = "INSERT INTO product (prod_id, prod_name, type, stock, price, status, image, fecha) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try  {
            Connection conexionDB = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta);
            consultaPreparada.setString(1, product.getProductId());
            consultaPreparada.setString(2, product.getProductName());
            consultaPreparada.setString(3, product.getType());
            consultaPreparada.setInt(4, product.getStock());
            consultaPreparada.setDouble(5, product.getPrice());
            consultaPreparada.setString(6, product.getStatus());
            consultaPreparada.setString(7, product.getImage());
            consultaPreparada.setDate(8, product.getFecha());

            consultaPreparada.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean existeProducto(String productId) {
        String consultaSQL  = "SELECT prod_id FROM product WHERE prod_id = '" + productId + "'";
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
        String consulta = "UPDATE product SET prod_id = ?, prod_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, fecha = ? WHERE id = ?";
        try (Connection conexionDB = ConexionDB.conectarDB();
             PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta)) {

            consultaPreparada.setString(1, product.getProductId());
            consultaPreparada.setString(2, product.getProductName());
            consultaPreparada.setString(3, product.getType());
            consultaPreparada.setInt(4, product.getStock());
            consultaPreparada.setDouble(5, product.getPrice());
            consultaPreparada.setString(6, product.getStatus());
            consultaPreparada.setString(7, product.getImage());
            consultaPreparada.setDate(8, new java.sql.Date(product.getFecha().getTime()));
            consultaPreparada.setInt(9, product.getId());

            consultaPreparada.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String consulta = "DELETE FROM product WHERE id = ?";
        try (Connection conexionDB = ConexionDB.conectarDB();
             PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta)) {

            consultaPreparada.setInt(1, productId);
            consultaPreparada.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
