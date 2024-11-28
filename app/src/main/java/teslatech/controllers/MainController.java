package teslatech.controllers;

import teslatech.Cliente;
import teslatech.Producto;
import teslatech.Datos;
import teslatech.db.ConexionDB;
import teslatech.services.ServicioProducto;
import teslatech.utils.Alertas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
// import net.sf.jasperreports.engine.JasperCompileManager;
// import net.sf.jasperreports.engine.JasperFillManager;
// import net.sf.jasperreports.engine.JasperPrint;
// import net.sf.jasperreports.engine.JasperReport;
// import net.sf.jasperreports.engine.design.JasperDesign;
// import net.sf.jasperreports.engine.xml.JRXmlLoader;
// import net.sf.jasperreports.view.JasperViewer;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class MainController implements Initializable {

    @FXML
    private Button customers_btn;

    @FXML
    private TableColumn<Cliente, String> customers_col_cashier;

    @FXML
    private TableColumn<Cliente, String> customers_col_customerID;

    @FXML
    private TableColumn<Cliente, String> customers_col_date;

    @FXML
    private TableColumn<Cliente, String> customers_col_total;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TableView<Cliente> customers_tableView;

    @FXML
    private BarChart<String, Integer> dashboard_CustomerChart;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_NSP;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TotalI;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<String, Number> dashboard_incomeChart;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<Producto, String> inventory_col_date;

    @FXML
    private TableColumn<Producto, String> inventory_col_price;

    @FXML
    private TableColumn<Producto, String> inventory_col_productID;

    @FXML
    private TableColumn<Producto, String> inventory_col_productName;

    @FXML
    private TableColumn<Producto, String> inventory_col_status;

    @FXML
    private TableColumn<Producto, String> inventory_col_stock;

    @FXML
    private TableColumn<Producto, String> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productID;

    @FXML
    private TextField inventory_productName;

    @FXML
    private ComboBox<String> inventory_status;

    @FXML
    private TextField inventory_stock;

    @FXML
    private TableView<Producto> inventory_tableView;

    @FXML
    private ComboBox<String> inventory_type;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField menu_amount;

    @FXML
    private Button menu_btn;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<Producto, String> menu_col_price;

    @FXML
    private TableColumn<Producto, String> menu_col_productName;

    @FXML
    private TableColumn<Producto, String> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<Producto> menu_tableView;

    @FXML
    private Label menu_total;

    @FXML
    private Label usuario;

    // private Connection conexionDB;
    // private PreparedStatement consultaPreparada;
    // private Statement statement;
    // private ResultSet resultadoConsulta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarNombreUsuario();

        mostrarNumeroDeClientes();
        mostrarIngresosTotalesDiarios();
        mostrarIngresosTotales();
        mostrarNumeroDeProductosVendidos();
        mostrarGraficoDeIngresos();
        mostrarGraficoDeClientes();

        mostrarListaDeTiposInventario();
        mostrarListaDeEstadosInventario();
        mostrarDatosInventario();

        mostrarTarjetasDelMenu();
        obtenerOrdenDelMenu();
        mostrarTotalDelMenu();
        mostrarDatosDeLaOrden();

        mostrarDatosClientes();
    }

    public void mostrarIngresosTotalesDiarios() {
        Date fechaActual = new Date();
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

        // Consulta SQL para sumar los valores de la columna 'total' de la tabla 'recibos' para la fecha actual.
        String consulta = "SELECT SUM(total) FROM recibo WHERE fecha = ?";
        double totalIngresosDeHoy = 0;

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            consultaPreparada.setDate(1, fechaSQL);

            ResultSet resultadoConsulta  = consultaPreparada.executeQuery();
            if (resultadoConsulta.next()) {
                totalIngresosDeHoy = resultadoConsulta.getDouble("SUM(total)");
            }

            dashboard_TI.setText("S/" + totalIngresosDeHoy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarIngresosTotales() {
        String consulta = "SELECT SUM(total) FROM recibo";
        float totalIngresos = 0;

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {
                totalIngresos = resultadoConsulta.getFloat("SUM(total)");
            }
            dashboard_TotalI.setText("S/" + totalIngresos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarNumeroDeProductosVendidos() {
        String consulta = "SELECT COUNT(cantidad) FROM cliente";
        int numeroProductosVendidos = 0;

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {
                numeroProductosVendidos = resultadoConsulta.getInt("COUNT(cantidad)");
            }
            dashboard_NSP.setText(String.valueOf(numeroProductosVendidos));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarGraficoDeIngresos() {
        dashboard_incomeChart.getData().clear();

        // Consulta SQL que selecciona la fecha y la suma de los totales de 'recibo' agrupados por fecha.
        String consulta = "SELECT fecha, SUM(total) FROM recibo GROUP BY fecha ORDER BY TIMESTAMP(fecha)";

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            XYChart.Series<String, Number> serieGrafico = new XYChart.Series<>();

            while (resultadoConsulta.next()) {
                String fecha = resultadoConsulta.getString("fecha");
                float total = resultadoConsulta.getFloat("SUM(total)");

                serieGrafico.getData().add(new XYChart.Data<>(fecha, total));
            }

            dashboard_incomeChart.getData().add(serieGrafico);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarGraficoDeClientes(){
        dashboard_CustomerChart.getData().clear();

        // Consulta SQL que selecciona la fecha y cuenta los clientes en la tabla 'recibo' agrupados por fecha.
        String sql = "SELECT fecha, COUNT(id) FROM recibo GROUP BY fecha ORDER BY TIMESTAMP(fecha)";

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(sql);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            XYChart.Series<String, Integer> serieGrafico = new XYChart.Series<>();

            while (resultadoConsulta.next()) {
                String fecha = resultadoConsulta.getString("fecha");
                int cantidadClientes = resultadoConsulta.getInt("COUNT(id)");

                serieGrafico.getData().add(new XYChart.Data<>(fecha, cantidadClientes));
            }

            dashboard_CustomerChart.getData().add(serieGrafico);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Cliente> obtenerListaDeClientes() {
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
        String consulta = "SELECT * FROM recibo";

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();
            Cliente cliente;

            while (resultadoConsulta.next()) {
                cliente = new Cliente(
                    resultadoConsulta.getInt("id"),
                    resultadoConsulta.getInt("id_cliente"),
                    resultadoConsulta.getDouble("total"),
                    resultadoConsulta.getDate("fecha"),
                    resultadoConsulta.getString("usuario_empleado")
                );

                listaClientes.add(cliente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes;
    }

    public void mostrarDatosClientes() {
        ObservableList<Cliente> listaClientes = obtenerListaDeClientes();
        customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        customers_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));
        customers_tableView.setItems(listaClientes);
    }

    public void btnGenerarRecibo() {
        // if (totalPrecio == 0 || menu_amount.getText().isEmpty()) {
        //     Alertas.mostrarError("Por favor haga su pedido primero");
        // } else {
        //     HashMap<String, Object> map = new HashMap<>();
        //     map.put("getReceipt", (clienteId - 1));

        //     try {
        //         Connection connect = null;
        //         JasperDesign jDesign = JRXmlLoader.load("/reports/reporte.jrxml");
        //         JasperReport jReport = JasperCompileManager.compileReport(jDesign);
        //         JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

        //         JasperViewer.viewReport(jPrint, false);
        //         reiniciarMenu();

        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
    }

    public ObservableList<Producto> obtenerOrdenDelMenu() {
        // Llamada al método obtenerIdCliente para asegurar que el ID del cliente esté definido
        obtenerIdCliente();

        // Crear una lista observable para almacenar los productos de la orden
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

        // Consulta SQL para obtener todos los productos del cliente según su ID
        String consulta = "SELECT * FROM cliente WHERE id_cliente = " + clienteId;

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            Producto producto;
            while (resultadoConsulta.next()) {
                producto = new Producto(
                    resultadoConsulta.getInt("id"),
                    resultadoConsulta.getString("id_producto"),
                    resultadoConsulta.getString("nombre_producto"),
                    resultadoConsulta.getString("tipo"),
                    resultadoConsulta.getInt("cantidad"),
                    resultadoConsulta.getDouble("precio"),
                    resultadoConsulta.getString("imagen"),
                    resultadoConsulta.getDate("fecha")
                );

                listaProductos.add(producto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    private double totalPrecio;

    public void mostrarTotalDelMenu() {
        calcularTotalDelMenu();
        menu_total.setText("S/" + totalPrecio);
    }

    // Muestra los datos de inventario en la tabla
    public void mostrarDatosDeLaOrden() {
        ObservableList<Producto> listaProductos = obtenerOrdenDelMenu();
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("precio"));
        menu_tableView.setItems(listaProductos);
    }

    // Obtiene el ID máximo (último ID) de la tabla cliente
    private int clienteId;

    // Calcula el total de precios de los productos
    public void calcularTotalDelMenu() {
        obtenerIdCliente();

        String consulta = "SELECT SUM(precio) FROM cliente WHERE id_cliente = " + clienteId;

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {
                totalPrecio = resultadoConsulta.getDouble("SUM(precio)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtenerIdCliente() {
        // Se define la consulta SQL para obtener el valor máximo del campo id_cliente en la tabla cliente
        String sql = "SELECT MAX(id_cliente) FROM cliente";

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(sql);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {
                clienteId = resultadoConsulta.getInt("MAX(id_cliente)");
            }

            // Se define una segunda consulta SQL para obtener el valor máximo del campo id_cliente en la tabla recibo
            String consultaIdRecibo = "SELECT MAX(id_cliente) FROM recibo";
            consultaPreparada = connect.prepareStatement(consultaIdRecibo);
            resultadoConsulta = consultaPreparada.executeQuery();

            int reciboId = 0;
            if (resultadoConsulta.next()) {
                reciboId = resultadoConsulta.getInt("MAX(id_cliente)");
            }

            if (clienteId == 0) {
                clienteId += 1;
            } else if (clienteId == reciboId) {
                clienteId += 1;
            }

            // Se asigna el valor final de idCliente a la variable estática de la clase Datos
            Datos.clienteId = clienteId;
            System.out.println("ClienteId: " + clienteId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Producto> obtenerDatosDelMenu() {
        String consulta = "SELECT * FROM producto";
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {
                Producto producto = new Producto(
                    resultadoConsulta.getInt("id"),
                    resultadoConsulta.getString("id_producto"),
                    resultadoConsulta.getString("nombre_producto"),
                    resultadoConsulta.getString("tipo"),
                    resultadoConsulta.getInt("stock"),
                    resultadoConsulta.getDouble("precio"),
                    resultadoConsulta.getString("imagen"),
                    resultadoConsulta.getDate("fecha")
                );

                listaProductos.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    // Devuelve una lista observable de productos consultando todos los datos de la tabla produc

    // Muestra las tarjetas de productos en la interfaz
    public void mostrarTarjetasDelMenu() {
        ObservableList<Producto> listaTarjetasProductos = FXCollections.observableArrayList();

        // Limpiamos la lista de tarjetas y la actualizamos con los nuevos productos
        listaTarjetasProductos.clear();
        listaTarjetasProductos.addAll(obtenerDatosDelMenu());

        int fila = 0;
        int columna = 0;

        // Limpiamos el GridPane para que no se acumulen tarjetas anteriores
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int i = 0; i < listaTarjetasProductos.size(); i++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/views/TarjetaProducto.fxml"));

                AnchorPane pane = load.load();
                TarjetaProductoController tarjetaControlador = load.getController();
                tarjetaControlador.establecerDatos(listaTarjetasProductos.get(i));

                if (columna == 3) {
                    columna = 0;
                    fila += 1;
                }

                // Agregamos la tarjeta al GridPane en la posición correspondiente
                menu_gridPane.add(pane, columna++, fila);

                // Establecemos márgenes para las tarjetas
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Muestra el recuento de recibos (recibo)
    public void mostrarNumeroDeClientes() {
        String consulta = "SELECT COUNT(id) FROM recibo";
        int numeroClientes = 0;

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement consultaPreparada = connect.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {
                numeroClientes = resultadoConsulta.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(numeroClientes));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   // Selecciona un producto de la tabla de inventario y muestra sus detalles
    public void seleccionarDatosInventario() {
        Producto prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        // Si no se ha seleccionado ningún producto, se sale del método
        if (num < 0) {
            return;
        }

        // Establece los valores de los campos en base al producto seleccionado
        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrecio()));

        // Asigna la imagen y la fecha
        Datos.path = prodData.getImagen();
        String path = "File:" + prodData.getImagen();
        Datos.fecha = String.valueOf(prodData.getFecha());
        Datos.id = prodData.getId();

        // Muestra la imagen en el campo correspondiente
        Image imagen = new Image(path, 200,200, false, true);
        inventory_imageView.setImage(imagen);
    }

    // Método para añadir un producto al inventario
    public void btnAgregarProductoInventario() {
        if (inventory_productID.getText().isEmpty() ||
            inventory_productName.getText().isEmpty() ||
            inventory_type.getSelectionModel().getSelectedItem() == null ||
            inventory_stock.getText().isEmpty() ||
            inventory_price.getText().isEmpty() ||
            inventory_status.getSelectionModel().getSelectedItem() == null ||
            Datos.path == null) {

            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");

        } else {
            String consultaProductoId = "SELECT id_producto FROM producto WHERE id_producto = '" + inventory_productID.getText() + "'";
            String insertarDatos = "INSERT INTO producto " + "(id_producto, nombre_producto, tipo, stock, precio, estado, imagen, fecha) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                Connection conexionDB = ConexionDB.conectarDB();
                Statement statement = conexionDB.createStatement();
                ResultSet resultadoConsulta = statement.executeQuery(consultaProductoId);
                PreparedStatement insertarPreaparada = conexionDB.prepareStatement(insertarDatos);

                if (resultadoConsulta.next()) {
                    Alertas.mostrarError(inventory_productID.getText() + " ya está en uso");
                } else {
                    // Inserta los datos del producto en la base de datos
                    insertarPreaparada.setString(1, inventory_productID.getText());
                    insertarPreaparada.setString(2, inventory_productName.getText());
                    insertarPreaparada.setString(3, inventory_type.getSelectionModel().getSelectedItem());
                    insertarPreaparada.setString(4, inventory_stock.getText());
                    insertarPreaparada.setString(5, inventory_price.getText());
                    insertarPreaparada.setString(6, inventory_status.getSelectionModel().getSelectedItem());
                    insertarPreaparada.setString(7, Datos.path.replace("\\", "\\\\"));
                    insertarPreaparada.setDate(8, new java.sql.Date(System.currentTimeMillis()));

                    insertarPreaparada.executeUpdate();
                    Alertas.mostrarInformacion("¡Añadido con éxito!");

                    mostrarDatosInventario();
                    btnLimpiarCamposInventario();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Button de Actualizar
    public void btnActualizarProductoInventario() {
        if (inventory_productID.getText().isEmpty() ||
            inventory_productName.getText().isEmpty() ||
            inventory_type.getSelectionModel().getSelectedItem() == null ||
            inventory_stock.getText().isEmpty() ||
            inventory_price.getText().isEmpty() ||
            inventory_status.getSelectionModel().getSelectedItem() == null ||
            Datos.path == null ||
            Datos.id == 0) {

            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");

        } else {
            String consulta = "UPDATE producto SET id_producto = ?, nombre_producto = ?, tipo = ?, stock = ?, precio = ?, estado = ?, imagen = ?, fecha = ? WHERE id = ?";

            try {
                Connection conexionDB = ConexionDB.conectarDB();
                PreparedStatement statement = conexionDB.prepareStatement(consulta);

                // Confirma si se desea actualizar el producto
                if (Alertas.mostrarConfirmacion("¿Está seguro de que desea Actualizar el ID del producto: " + inventory_productID.getText() + "?")) {
                    statement.setString(1, inventory_productID.getText());
                    statement.setString(2, inventory_productName.getText());
                    statement.setString(3, inventory_type.getSelectionModel().getSelectedItem());
                    statement.setString(4, inventory_stock.getText());
                    statement.setString(5, inventory_price.getText());
                    statement.setString(6, inventory_status.getSelectionModel().getSelectedItem());
                    statement.setString(7, Datos.path.replace("\\", "\\\\"));
                    statement.setDate(8, new java.sql.Date(System.currentTimeMillis()));
                    statement.setInt(9, Datos.id);

                    statement.executeUpdate();

                    Alertas.mostrarInformacion("¡Actualizado exitosamente!");

                    mostrarDatosInventario();
                    btnLimpiarCamposInventario();

                } else {
                    Alertas.mostrarError("Cancelado");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Button Limpiar los campos del inventario
    public void btnLimpiarCamposInventario() {
        inventory_productID.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        inventory_imageView.setImage(null);
        Datos.path = "";
        Datos.id = 0;
    }

    // Método para eliminar un producto del inventario
    public void btnEliminarProductoInventario() {
        // Verifica si hay un producto seleccionado para eliminar
        if (Datos.id == 0) {
            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");
            return;
        }

        // Confirma si el usuario desea eliminar el producto
        if (Alertas.mostrarConfirmacion("¿Está seguro de que desea eliminar el producto de ID: " + inventory_productID.getText() + "?")) {
            String consulta = "DELETE FROM producto WHERE id = ?";

            try {
                Connection conexionDB = ConexionDB.conectarDB();
                PreparedStatement preparar =  conexionDB.prepareStatement(consulta);

                preparar.setInt(1, Datos.id);
                preparar.executeUpdate();

                Alertas.mostrarError("Eliminado con éxito!");
                mostrarDatosInventario();
                btnLimpiarCamposInventario();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alertas.mostrarError("Cancelado");
        }
    }

    // Método para importar una imagen al inventario
    public void btnImportarImagenInventario() {
        FileChooser selectorArchivo = new FileChooser();
        selectorArchivo.getExtensionFilters().add(new ExtensionFilter("Abrir archivo de imagen", "*png", "*jpg"));

        File archivoSelecionado = selectorArchivo.showOpenDialog(main_form.getScene().getWindow());

        try {
            if (archivoSelecionado != null) {
                Datos.path = archivoSelecionado.getAbsolutePath();

                // Carga y muestra la imagen seleccionada
                Image imagen = new Image(archivoSelecionado.toURI().toString(), 200,200, false, true);
                inventory_imageView.setImage(imagen);
            } else {
                System.out.println("Error: No se seleccionó ninguna imagen.");
            }

        } catch (Exception e) {
            Alertas.mostrarError("Error al cargar la imagen");
            e.printStackTrace();
        }
    }

    // Muestra los datos de inventario en la tabla
    public void mostrarDatosInventario() {
        ObservableList<Producto> inventoryListData = ServicioProducto.obtenerListaProductos();
        inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("precio"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("estado"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        inventory_tableView.setItems(inventoryListData);
    }

    private String[] listaTipos = {
        "Procesador",
        "RAM",
        "Placa Base",
        "Tarjeta Gráfica"
    };

    // Define una lista de tipos de productos y la asigna a la lista desplegable
    public void mostrarListaDeTiposInventario() {
        ObservableList<String> listaDatos = FXCollections.observableArrayList(Arrays.asList(listaTipos));
        inventory_type.setItems(listaDatos);
    }

    private String[] listaEstados = {
        "Disponible",
        "No disponible"
    };

    //  Define una lista de estados (disponible y no disponible)
    public void mostrarListaDeEstadosInventario() {
        ObservableList<String> listaDatos = FXCollections.observableArrayList(Arrays.asList(listaEstados));
        inventory_status.setItems(listaDatos);
    }

    // Button de salir sesion
    public void cerrarSession() {
        try {
            if (Alertas.mostrarConfirmacion("¿Estás seguro de que quieres cerrar sesión?")) {
                logout_btn.getScene().getWindow().hide();

                System.out.println(getClass().getResource("/views/main.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                Image icon = new Image(getClass().getResourceAsStream("/logo/tesla-tech.png"));

                stage.setTitle("Tesla Tech");
                stage.getIcons().add(icon);
                stage.setMaxWidth(800);
                stage.setMaxHeight(650);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double monto;
    private double cambio;

    public void calcularCambio() {
        calcularTotalDelMenu();

        if (menu_amount.getText().isEmpty() || totalPrecio == 0) {
            Alertas.mostrarError("Inválido");
        } else {
            // Convierte el monto ingresado a un número decimal
            monto = Double.parseDouble(menu_amount.getText());

            // Verifica si el monto es menor al precio total
            if (monto < totalPrecio) {
                menu_amount.setText("");
            } else {
                // Calcula el cambio y lo muestra en el campo correspondiente
                cambio = (monto - totalPrecio);
                menu_change.setText("S/" + cambio);
            }
        }
    }

    public void btnPagarMenu() {
        if (totalPrecio == 0) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Message");
            alerta.setHeaderText(null);
            alerta.setContentText("¡Por favor, seleccione su pedido primero!");
            alerta.showAndWait();
        } else {
            calcularTotalDelMenu();
            String insertPay = "INSERT INTO recibo (id_cliente, total, fecha, usuario_empleado) " + "VALUES(?, ?, ?, ?)";

            try {
                Connection connect = ConexionDB.conectarDB();
                PreparedStatement consultaPreparada = connect.prepareStatement(insertPay);

                if (monto == 0) {
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setTitle("Error Messaged");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Algo falla");
                    alerta.showAndWait();
                } else {
                    Alert alerta = new Alert(AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmation Message");
                    alerta.setHeaderText(null);
                    alerta.setContentText("¿Estás seguro?");
                    Optional<ButtonType> option = alerta.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        obtenerIdCliente();
                        calcularTotalDelMenu();
                        consultaPreparada.setString(1, String.valueOf(clienteId));
                        consultaPreparada.setString(2, String.valueOf(totalPrecio));

                        Date fecha = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());

                        consultaPreparada.setString(3, String.valueOf(sqlDate));
                        consultaPreparada.setString(4, Datos.usuario);

                        consultaPreparada.executeUpdate();

                        alerta = new Alert(AlertType.INFORMATION);
                        alerta.setTitle("Infomation Message");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Exitoso");
                        alerta.showAndWait();

                        mostrarDatosDeLaOrden();
                        reiniciarMenu();

                    } else {
                        alerta = new Alert(AlertType.WARNING);
                        alerta.setTitle("Infomation Message");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Cancelado");
                        alerta.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void reiniciarMenu() {
        totalPrecio = 0;
        cambio = 0;
        monto = 0;
        menu_total.setText("S/0.0");
        menu_amount.setText("");
        menu_change.setText("S/0.0");
    }

    public void btnEliminarOrdenMenu() {
        if (getid == 0) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Message");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione el pedido que desea eliminar");
            alerta.showAndWait();
        } else {
            String deleteData = "DELETE FROM cliente WHERE id = " + getid;
            try {
                Connection connect = ConexionDB.conectarDB();

                Alert alerta = new Alert(AlertType.CONFIRMATION);
                alerta.setTitle("Confirmation Message");
                alerta.setHeaderText(null);
                alerta.setContentText("¿Está seguro de que desea eliminar este pedido?");
                Optional<ButtonType> option = alerta.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement consultaPreparada = connect.prepareStatement(deleteData);
                    consultaPreparada.executeUpdate();
                }

                mostrarDatosDeLaOrden();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // Variable para almacenar el ID del pedido seleccionado
    private int getid;

    // Método para seleccionar un pedido de la tabla del menú
    public void seleccionarOrdenDelMenu() {
        Producto prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        // Obtiene el ID del producto seleccionado
        getid = prod.getId();
    }

    // Método para mostrar el nombre del usuario actual
    public void mostrarNombreUsuario() {
        String usuarioActual = Datos.usuario;
        usuarioActual = usuarioActual.substring(0, 1).toUpperCase() + usuarioActual.substring(1);
        usuario.setText("Bienvenido " + usuarioActual);
    }

    // Método para cambiar entre diferentes paneles en la interfaz
    @FXML
    void cambiarPanel(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            // Muestra el panel del tablero y oculta los demás
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            // Actualiza las estadísticas en el tablero
            mostrarNumeroDeClientes();
            mostrarIngresosTotalesDiarios();
            mostrarIngresosTotales();
            mostrarNumeroDeProductosVendidos();
            mostrarGraficoDeIngresos();
            mostrarGraficoDeClientes();

        } else if (event.getSource() == inventory_btn) {
            // Muestra el panel de inventario y oculta los demás
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            // Actualiza los datos del inventario
            mostrarListaDeTiposInventario();
            mostrarListaDeEstadosInventario();
            mostrarDatosInventario();
        } else if (event.getSource() == menu_btn) {
            // Muestra el panel del menú y oculta los demás
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);

            // Actualiza las tarjetas y datos del menú
            mostrarTarjetasDelMenu();
            mostrarTotalDelMenu();
            mostrarDatosDeLaOrden();
        } else if (event.getSource() == customers_btn) {
            // Muestra el panel de clientes y oculta los demás
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);

            // Actualiza los datos de los clientes
            mostrarDatosClientes();
        }
    }
}
