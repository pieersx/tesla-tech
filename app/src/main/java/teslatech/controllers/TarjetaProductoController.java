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

    private Producto producto;
    private Image imagen;
    private String prodID;
    private String type;
    private String prod_date;
    private String imagenProducto;
    private SpinnerValueFactory<Integer> spin;
    private Alert alerta;

    // Asigna los datos de un producto a la tarjeta.
    public void establecerDatos(Producto producto) {
        this.producto = producto;
        imagenProducto = producto.getImage();
        prod_date = String.valueOf(producto.getFecha());
        type = producto.getType();
        prodID = producto.getProductId();
        prod_name.setText(producto.getProductName());
        prod_price.setText("S/" + String.valueOf(producto.getPrice()));
        String path = "File:" + producto.getImage();
        imagen = new Image(path, 200,200, false, true);
        prod_imageView.setImage(imagen);
        precio = producto.getPrice();
    }
    private int cantidadProducto;

    public void establecerCantidad() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    private double totalP;
    private double precio;

    public void btnAgregar() {
        MainController mainController = new MainController();
        mainController.obtenerIdCliente();

        cantidadProducto = prod_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE prod_id = '" + prodID + "'";

        try {
            int stockActual = 0;
            String checkStock = "SELECT stock FROM product WHERE prod_id = '" + prodID + "'";

            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(checkStock);
            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();

            if (resultadoConsulta .next()) {
                stockActual = resultadoConsulta .getInt("stock");
            }

            if (stockActual == 0){
                // Si no hay stock, actualiza el estado a "No disponible".
                String updateStock = "UPDATE product SET prod_name = '" + prod_name.getText() +
                    "', type = '" + type +
                    "', stock = 0, price = " + precio +
                    ", status = 'No disponible', image = '" + imagenProducto +
                    "', fecha = '" + prod_date +
                    "' WHERE prod_id = '" + prodID + "'";

                consultaPreparada = connect.prepareStatement(updateStock);
                consultaPreparada.executeUpdate();
            }

            consultaPreparada = connect.prepareStatement(checkAvailable);
            resultadoConsulta  = consultaPreparada.executeQuery();

            if (resultadoConsulta .next()) {
                check = resultadoConsulta .getString("status");
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
                    String insertarProducto = "INSERT INTO customer " + "(customer_id, prod_id, prod_name, type, quantity, price, fecha, image, em_username) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    consultaPreparada = connect.prepareStatement(insertarProducto);
                    consultaPreparada.setString(1, String.valueOf(Datos.clienteId));
                    consultaPreparada.setString(2, prodID);
                    consultaPreparada.setString(3, prod_name.getText());
                    consultaPreparada.setString(4, type);
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
                    String updateStock = "UPDATE product SET prod_name = '" + prod_name.getText() +
                        "', type = '" + type +
                        "', stock = " + upStock +
                        ", price = " + precio +
                        ", status = '" + check +
                        "', image = '" + imagenProducto +
                        "', fecha = '" + prod_date +
                        "' WHERE prod_id = '" + prodID + "'";

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
