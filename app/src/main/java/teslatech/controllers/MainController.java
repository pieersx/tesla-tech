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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
    private BarChart<?, ?> dashboard_CustomerChart;

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
    private AreaChart<?, ?> dashboard_incomeChart;

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
    private Label username;

    // private Connection conexion;
    // private PreparedStatement prepare;
    // private Statement statement;
    // private ResultSet result;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();
        dashboardIncomeChart();
        dashboardCustomerChart();

        inventoryTypeList();
        inventoryStatusList();
        mostrarDatosInventario();

        menuDisplayCard();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();

        customersShowData();
    }

    public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";

        Connection connect = ConexionDB.conectarDB();

        try {
            double ti = 0;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";

        Connection connect = ConexionDB.conectarDB();

        try {
            float ti = 0;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_TotalI.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardNSP() {

        String sql = "SELECT COUNT(quantity) FROM customer";

        Connection connect = ConexionDB.conectarDB();

        try {
            int q = 0;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            dashboard_NSP.setText(String.valueOf(q));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardIncomeChart() {
        dashboard_incomeChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        Connection connect = ConexionDB.conectarDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }

            dashboard_incomeChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardCustomerChart(){
        dashboard_CustomerChart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        Connection connect = ConexionDB.conectarDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            dashboard_CustomerChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Cliente> customersDataList() {

        ObservableList<Cliente> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        Connection connect = ConexionDB.conectarDB();

        try {

            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            Cliente cData;

            while (result.next()) {
                cData = new Cliente(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));

                listData.add(cData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }


    private ObservableList<Cliente> customersListData;
    public void customersShowData() {
        customersListData = customersDataList();

        customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customers_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));

        customers_tableView.setItems(customersListData);
    }

    private Connection connect;
    public void menuReceiptBtn() {

        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Message");
            alerta.setContentText("Please order first");
            alerta.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));

            try {

                JasperDesign jDesign = JRXmlLoader.load("/reports/reporte.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                JasperViewer.viewReport(jPrint, false);

                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public ObservableList<Producto> menuGetOrder() {
        customerID();
        ObservableList<Producto> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;

        Connection connect = ConexionDB.conectarDB();

        try {

            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            Producto prod;

            while (result.next()) {
                prod = new Producto(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("S/ " + totalP);
    }

    // Muestra los datos de inventario en la tabla
    public void menuShowOrderData() {
        ObservableList<Producto> inventoryListData = menuGetOrder();

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(inventoryListData);
    }


    // Calcula el total de precios de los productos
    private double totalP;
    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement prepare = connect.prepareStatement(total);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Obtiene el ID máximo (último ID) de la tabla customer
    private int cID;
    public void customerID() {
        String sql = "SELECT MAX(customer_id) FROM customer";
        Connection connect = ConexionDB.conectarDB();

        try {
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            Datos.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Devuelve una lista observable de productos consultando todos los datos de la tabla product - 590 menuDisplayOrder
    private ObservableList<Producto> cardListData = FXCollections.observableArrayList();
    public ObservableList<Producto> menuGetData() {
        String sql = "SELECT * FROM product";
        ObservableList<Producto> listData = FXCollections.observableArrayList();

        try {
            Connection connect = ConexionDB.conectarDB();
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                Producto prod = new Producto(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    // Muestra las tarjetas de productos en la interfaz
    public void menuDisplayCard() {
        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/views/TarjetaProducto.fxml"));
                AnchorPane pane = load.load();
                TarjetaProductoController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Muestra el recuento de recibos (receipt)
    public void dashboardDisplayNC() {
        String sql = "SELECT COUNT(id) FROM receipt";
        Connection connect = ConexionDB.conectarDB();

        try {
            int nc = 0;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Selecionar un producto en la tabla de inventario
    public void inventorySelectData() {
        Producto prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));

        Datos.path = prodData.getImage();

        String path = "File:" + prodData.getImage();
        Datos.date = String.valueOf(prodData.getDate());
        Datos.id = prodData.getId();

        Image image = new Image(path, 200,200, false, true);
        inventory_imageView.setImage(image);
    }

    // Button de Añadir
    public void inventoryAddBtn() {
        if (inventory_productID.getText().isEmpty() ||
            inventory_productName.getText().isEmpty() ||
            inventory_type.getSelectionModel().getSelectedItem() == null ||
            inventory_stock.getText().isEmpty() ||
            inventory_price.getText().isEmpty() ||
            inventory_status.getSelectionModel().getSelectedItem() == null ||
            Datos.path == null) {

            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");

        } else {
            String consultaProductoId = "SELECT prod_id FROM product WHERE prod_id = '" + inventory_productID.getText() + "'";
            String insertarDatos = "INSERT INTO product " + "(prod_id, prod_name, type, stock, price, status, image, date) " + "VALUES(?,?,?,?,?,?,?,?)";

            try {
                Connection conexion = ConexionDB.conectarDB();
                Statement statement = conexion.createStatement();
                ResultSet resultado = statement.executeQuery(consultaProductoId);
                PreparedStatement insertarPreaparada = conexion.prepareStatement(insertarDatos);

                if (resultado.next()) {
                    Alertas.mostrarError(inventory_productID.getText() + " ya está en uso");
                } else {
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
                    inventoryClearBtn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Button de Actualizar
    public void inventoryUpdateBtn() {
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
            // String path = Datos.path.replace("\\", "\\\\");
            String query = "UPDATE product SET prod_id = ?, prod_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, date = ? WHERE id = ?";

            try {
                Connection conexion = ConexionDB.conectarDB();
                PreparedStatement statement = conexion.prepareStatement(query);

                if (Alertas.mostrarConfirmacion("Are you sure you want to UPDATE PRoduct ID: " + inventory_productID.getText() + "?")) {
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
                    inventoryClearBtn();
                } else {
                    Alertas.mostrarError("Cancelado");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Button Limpiar
    public void inventoryClearBtn() {
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

    // Button Eliminar
    public void inventoryDeleteBtn() {
        if (Datos.id == 0) {
            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");

            return;
        }

        if (Alertas.mostrarConfirmacion("¿Está seguro de que desea eliminar el producto de ID: " + inventory_productID.getText() + "?")) {
            String eliminarProducto = "DELETE FROM product WHERE id = ?";

            try {
                Connection conexion = ConexionDB.conectarDB();
                PreparedStatement preparar =  conexion.prepareStatement(eliminarProducto);

                System.out.println(Datos.id);
                System.out.println(Datos.username);
                System.out.println(inventory_productID.getText());
                preparar.setInt(1, Datos.id);
                preparar.executeUpdate();

                Alertas.mostrarError("Eliminado con éxito!");
                mostrarDatosInventario();
                inventoryClearBtn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alertas.mostrarError("Cancelado");
        }
    }

    // Button importar imagen
    public void inventoryImportBtn() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Abrir archivo de imagen", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        try {
            if (file != null) {
                Datos.path = file.getAbsolutePath();
                Image image = new Image(file.toURI().toString(), 200,200, false, true);
                inventory_imageView.setImage(image);
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
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inventory_tableView.setItems(inventoryListData);
    }

    private String[] listaTipos = {
        "Procesador",
        "RAM",
        "Placa Base",
        "Tarjeta Gráfica"
    };

    // Define una lista de tipos de productos y la asigna a la lista desplegable
    public void inventoryTypeList() {
        ObservableList<String> listaDatos = FXCollections.observableArrayList(Arrays.asList(listaTipos));
        inventory_type.setItems(listaDatos);
    }

    private String[] listaEstados = {
        "Disponible",
        "No disponible"
    };

    //  Define una lista de estados (disponible y no disponible)
    public void inventoryStatusList() {
        ObservableList<String> listaDatos = FXCollections.observableArrayList(Arrays.asList(listaEstados));
        inventory_status.setItems(listaDatos);
    }

    // Button de salir sesion
    public void logout() {
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
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double amount;
    private double change;
    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            Alertas.mostrarError("Inválido");
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) {
                menu_amount.setText("");
            } else {
                change = (amount - totalP);
                menu_change.setText("S/" + change);
            }
        }
    }

    public void menuPayBtn() {

        if (totalP == 0) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Message");
            alerta.setHeaderText(null);
            alerta.setContentText("Please choose your order first!");
            alerta.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) "
                    + "VALUES(?,?,?,?)";

            Connection connect = ConexionDB.conectarDB();

            try {

                if (amount == 0) {
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setTitle("Error Messaged");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Something wrong :3");
                    alerta.showAndWait();
                } else {
                    Alert alerta = new Alert(AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmation Message");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Are you sure?");
                    Optional<ButtonType> option = alerta.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        PreparedStatement prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, Datos.username);

                        prepare.executeUpdate();

                        alerta = new Alert(AlertType.INFORMATION);
                        alerta.setTitle("Infomation Message");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Successful.");
                        alerta.showAndWait();

                        menuShowOrderData();
                        menuRestart();

                    } else {
                        alerta = new Alert(AlertType.WARNING);
                        alerta.setTitle("Infomation Message");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Cancelled.");
                        alerta.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("S/ 0.0");
        menu_amount.setText("");
        menu_change.setText("S/ 0.0");
    }

    public void menuRemoveBtn() {

        if (getid == 0) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Message");
            alerta.setHeaderText(null);
            alerta.setContentText("Please select the order you want to remove");
            alerta.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            Connection connect = ConexionDB.conectarDB();
            try {
                Alert alerta = new Alert(AlertType.CONFIRMATION);
                alerta.setTitle("Confirmation Message");
                alerta.setHeaderText(null);
                alerta.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alerta.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private int getid;
    public void menuSelectOrder() {
        Producto prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

    }

    // Mostrar nombre del usuario
    public void displayUsername() {
        String user = Datos.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText("Bienvenido " + user);
    }

    // Cambiar de Panel
    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();

        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            mostrarDatosInventario();
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);

            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);

            customersShowData();
        }
    }
}
