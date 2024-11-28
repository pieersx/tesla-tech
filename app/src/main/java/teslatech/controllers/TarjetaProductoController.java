package teslatech.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import teslatech.Datos;
import teslatech.Producto;
import teslatech.db.ConexionDB;

public class TarjetaProductoController implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        establecerCantidad();
    }

    private Image imagen;
    private String idProducto;
    private String tipoProducto;
    private String fechaProducto;
    private String imagenProducto;
    private SpinnerValueFactory<Integer> girar;
    private Alert alerta;

    // Asigna los datos de un producto a la tarjeta.
    public void establecerDatos(Producto producto) {
        imagenProducto = producto.getImagen();
        fechaProducto = String.valueOf(producto.getFecha());
        tipoProducto = producto.getTipo();
        idProducto = producto.getProductId();
        prod_name.setText(producto.getProductName());
        prod_price.setText("S/" + String.valueOf(producto.getPrecio()));
        String path = "File:" + producto.getImagen();
        imagen = new Image(path, 200,200, false, true);
        prod_imageView.setImage(imagen);
        precio = producto.getPrecio();
    }
    private int cantidadProducto;

    public void establecerCantidad() {
        girar = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(girar);
    }

    private double totalP;
    private double precio;

    public void btnAgregar() {
        MainController mainController = new MainController();
        mainController.obtenerIdCliente();

        cantidadProducto = prod_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT estado FROM producto WHERE id_producto = '" + idProducto + "'";

        try {
            int stockActual = 0;
            String checkStock = "SELECT stock FROM producto WHERE id_producto = '" + idProducto + "'";

            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(checkStock);
            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();

            if (resultadoConsulta .next()) {
                stockActual = resultadoConsulta .getInt("stock");
            }

            if (stockActual == 0){
                // Si no hay stock, actualiza el estado a "No disponible".
                String updateStock = "UPDATE producto SET nombre_producto = '" + prod_name.getText() +
                    "', tipo = '" + tipoProducto +
                    "', stock = 0, precio = " + precio +
                    ", estado = 'No disponible', imagen = '" + imagenProducto +
                    "', fecha = '" + fechaProducto +
                    "' WHERE id_producto = '" + idProducto + "'";

                consultaPreparada = connect.prepareStatement(updateStock);
                consultaPreparada.executeUpdate();
            }

            consultaPreparada = connect.prepareStatement(checkAvailable);
            resultadoConsulta  = consultaPreparada.executeQuery();

            if (resultadoConsulta .next()) {
                check = resultadoConsulta .getString("estado");
                System.out.println("Product Status = " + check);
            }

            if (!check.equals("Disponible") || cantidadProducto == 0) {
                alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Error Message");
                alerta.setHeaderText(null);
                alerta.setContentText("Algo va mal");
                alerta.showAndWait();
            } else {

                if (stockActual < cantidadProducto) {
                    alerta = new Alert(AlertType.ERROR);
                    alerta.setTitle("Error Message");
                    alerta.setHeaderText(null);
                    alerta.setContentText("No válido. Este producto está agotado");
                    alerta.showAndWait();
                } else {
                    imagenProducto = imagenProducto.replace("\\", "\\\\");

                    // Inserta el producto en el carrito o tabla de clientes.
                    String insertarProducto = "INSERT INTO cliente " + "(id_cliente, id_producto, nombre_producto, tipo, cantidad, precio, fecha, imagen, usuario_empleado) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    consultaPreparada = connect.prepareStatement(insertarProducto);
                    consultaPreparada.setString(1, String.valueOf(Datos.clienteId));
                    consultaPreparada.setString(2, idProducto);
                    consultaPreparada.setString(3, prod_name.getText());
                    consultaPreparada.setString(4, tipoProducto);
                    consultaPreparada.setString(5, String.valueOf(cantidadProducto));

                    totalP = (cantidadProducto * precio);
                    consultaPreparada.setString(6, String.valueOf(totalP));

                    Date fecha = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
                    consultaPreparada.setString(7, String.valueOf(sqlDate));

                    consultaPreparada.setString(8, imagenProducto);
                    consultaPreparada.setString(9, Datos.usuario);

                    consultaPreparada.executeUpdate();

                    // Actualiza el stock restante.
                    int upStock = stockActual - cantidadProducto;
                    String updateStock = "UPDATE producto SET nombre_producto = '" + prod_name.getText() +
                        "', tipo = '" + tipoProducto +
                        "', stock = " + upStock +
                        ", precio = " + precio +
                        ", estado = '" + check +
                        "', imagen = '" + imagenProducto +
                        "', fecha = '" + fechaProducto +
                        "' WHERE id_producto = '" + idProducto + "'";

                    consultaPreparada = connect.prepareStatement(updateStock);
                    consultaPreparada.executeUpdate();

                    alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Information Message");
                    alerta.setHeaderText(null);
                    alerta.setContentText("¡Añadido con éxito!");
                    alerta.showAndWait();

                    mainController.calcularTotalDelMenu();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
