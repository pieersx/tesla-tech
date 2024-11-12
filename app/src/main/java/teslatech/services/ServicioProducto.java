package teslatech.services;

import teslatech.Producto;
import teslatech.db.DBConnection;
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
            Connection conexion = DBConnection.connectDB();
            PreparedStatement prepare = conexion.prepareStatement(consulta);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                Producto prodData = new Producto(
                    result.getInt("id"),
                    result.getString("prod_id"),
                    result.getString("prod_name"),
                    result.getString("type"),
                    result.getInt("stock"),
                    result.getDouble("price"),
                    result.getString("status"),
                    result.getString("image"),
                    result.getDate("date")
                );
                listaProductos.add(prodData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos;
    }

    public static void agregarProducto(Producto product) {
        String consulta = "INSERT INTO product (prod_id, prod_name, type, stock, price, status, image, date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try  {
            Connection conexion = DBConnection.connectDB();
            PreparedStatement prepare = conexion.prepareStatement(consulta);
            prepare.setString(1, product.getProductId());
            prepare.setString(2, product.getProductName());
            prepare.setString(3, product.getType());
            prepare.setInt(4, product.getStock());
            prepare.setDouble(5, product.getPrice());
            prepare.setString(6, product.getStatus());
            prepare.setString(7, product.getImage());
            prepare.setDate(8, product.getDate());

            prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean existeProducto(String productId) {
        String query = "SELECT prod_id FROM product WHERE prod_id = '" + productId + "'";
        try {
            Connection conexion = DBConnection.connectDB();
            Statement statement = conexion.createStatement();
            ResultSet result = statement.executeQuery(query);

            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateProduct(Producto product) {
        String consulta = "UPDATE product SET prod_id = ?, prod_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, date = ? WHERE id = ?";
        try (Connection conexion = DBConnection.connectDB();
             PreparedStatement prepare = conexion.prepareStatement(consulta)) {

            prepare.setString(1, product.getProductId());
            prepare.setString(2, product.getProductName());
            prepare.setString(3, product.getType());
            prepare.setInt(4, product.getStock());
            prepare.setDouble(5, product.getPrice());
            prepare.setString(6, product.getStatus());
            prepare.setString(7, product.getImage());
            prepare.setDate(8, new java.sql.Date(product.getDate().getTime()));
            prepare.setInt(9, product.getId());

            prepare.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String consulta = "DELETE FROM product WHERE id = ?";
        try (Connection conexion = DBConnection.connectDB();
             PreparedStatement prepare = conexion.prepareStatement(consulta)) {

            prepare.setInt(1, productId);
            prepare.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
