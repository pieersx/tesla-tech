package teslatech.controllers;

import teslatech.utils.Alertas;
import teslatech.Datos;
import teslatech.models.ModeloUsuario;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML
    private TextField txtAnswer;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnProceed;

    @FXML
    private ComboBox<String> cmbSecurityQuestion;

    @FXML
    private AnchorPane paneSecurityQuestion;

    @FXML
    private TextField txtForgotPasswordUsername;

    @FXML
    private Button btnBackToLogin;

    @FXML
    private Button btnChangePassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private AnchorPane paneNewPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private Hyperlink linkForgotPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private AnchorPane panelLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Button btnAlreadyHaveAccount;

    @FXML
    private AnchorPane paneSidebar;

    @FXML
    private TextField txtSignupAnswer;

    @FXML
    private PasswordField txtSignupPassword;

    @FXML
    private ComboBox<String> cmbSignupQuestion;

    @FXML
    private Button btnSignup;

    @FXML
    private AnchorPane paneSignup;

    @FXML
    private TextField txtSignupUsername;

    private String[] listaPreguntas = {
        "¿Cuál es tu color favorito?",
        "¿Cuál es tu comida favorita?",
        "¿Cuál es tu fecha de nacimiento?"
    };

    /* ==============================
     * Métodos para el Inicio de Sesión
     * ============================== */

    // Método para manejar el inicio de sesión
    public void btnIniciarSesion() {
        String usuario = txtUsername.getText();
        String contrasena = txtPassword.getText();

        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alertas.mostrarError("Nombre de usuario/contraseña incorrectos");

            return;
        }

        if (ModeloUsuario.esUsuarioValido(usuario, contrasena)) {
            Alertas.mostrarInformacion("¡Inicio de sesión exitosamente!");

            Datos.usuario = usuario;
            abrirVistaPrincipal();
        } else {
            Alertas.mostrarError("Nombre de usuario/contraseña incorrectos");
        }
    }

    // Método para abrir la vista principal
    private void abrirVistaPrincipal() {
        try {
            System.out.println(getClass().getResource("/views/main.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
            Stage stage = new Stage();
            Image icon = new Image(getClass().getResourceAsStream("/logo/tesla-tech.png"));

            stage.setTitle("Tesla Tech");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            btnLogin.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ==============================
     * Métodos para el Registro de Usuarios
     * ============================== */

    // Método para el registro de usuarios
    public void btnRegistrarUsuario() {
        String usuario = txtSignupUsername.getText();
        String contrasena = txtSignupPassword.getText();
        String pregunta = cmbSignupQuestion.getSelectionModel().getSelectedItem();
        String respuesta = txtSignupAnswer.getText();

        if (txtSignupUsername.getText().isEmpty() || txtSignupPassword.getText().isEmpty() || cmbSignupQuestion.getSelectionModel().getSelectedItem() == null || txtSignupAnswer.getText().isEmpty()) {
            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");

            return;
        }

        if (ModeloUsuario.verificarNombreUsuarioExistente(usuario)) {
            Alertas.mostrarError("El nombre de usuario ya está en uso");
        } else if (contrasena.length() < 8) {
            Alertas.mostrarError("La contraseña debe tener al menos 8 caracteres");
        } else if (ModeloUsuario.registrarNuevoUsuario(usuario, contrasena, pregunta, respuesta)) {
            Alertas.mostrarInformacion("¡Cuenta registrada con éxito!");

            limpiarFormularioRegistro();

            TranslateTransition transicionFormulario = new TranslateTransition();
            transicionFormulario.setNode(paneSidebar);
            transicionFormulario.setToX(0);
            transicionFormulario.setDuration(Duration.seconds(.5));
            transicionFormulario.setOnFinished((ActionEvent e) -> {
                btnAlreadyHaveAccount.setVisible(false);
                btnCreateAccount.setVisible(true);
            });
            transicionFormulario.play();
        }
    }

    // Limpiar formulario de registro
    private void limpiarFormularioRegistro() {
        txtSignupUsername.setText("");
        txtSignupPassword.setText("");
        cmbSignupQuestion.getSelectionModel().clearSelection();
        txtSignupAnswer.setText("");

        txtConfirmPassword.setText("");
        txtNewPassword.setText("");
        cmbSecurityQuestion.getSelectionModel().clearSelection();
        txtAnswer.setText("");
        txtForgotPasswordUsername.setText("");
    }

    // Cargar lista de preguntas de seguridad en el formulario de registro
    public void cargarListaPreguntasRegistro() {
        ObservableList<String> listData = FXCollections.observableArrayList(listaPreguntas);
        cmbSignupQuestion.setItems(listData);
    }

    /* ==============================
     * Métodos para Recuperación de Contraseña
     * ============================== */

    // Mostrar preguntas de seguridad en el formulario de recuperación
    public void cargarListaPreguntasRecuperacion() {
        ObservableList<String> listData = FXCollections.observableArrayList(listaPreguntas);
        cmbSecurityQuestion.setItems(listData);
    }

    // Mostrar preguntas de seguridad en el formulario de recuperación
    public void btnAcceder() {
        String usuario = txtForgotPasswordUsername.getText();
        String pregunta = cmbSecurityQuestion.getSelectionModel().getSelectedItem();
        String respuesta = txtAnswer.getText();

        if (txtForgotPasswordUsername.getText().isEmpty() || cmbSecurityQuestion.getSelectionModel().getSelectedItem() == null || txtAnswer.getText().isEmpty()) {
            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");

            return;
        }

        if (ModeloUsuario.validarDatosRecuperacion(usuario, pregunta, respuesta)) {
            Alertas.mostrarInformacion("Usuario encontrado");
            paneNewPassword.setVisible(true);
            paneSecurityQuestion.setVisible(false);
        } else {
            Alertas.mostrarError("Usuario no encontrado");
        }
    }

    // Cambio de contraseña
    public void btnCambiarContrasena() {
        String nuevaContrasena = txtNewPassword.getText();
        String confirmarContrasena = txtConfirmPassword.getText();
        String usuario = txtForgotPasswordUsername.getText();
        String pregunta = cmbSecurityQuestion.getSelectionModel().getSelectedItem();
        String respuesta = txtAnswer.getText();

        if (txtNewPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty()) {
            Alertas.mostrarError("Por favor, rellene todos los campos en blanco");

            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            Alertas.mostrarError("Las contraseñas no coinciden");

            return;
        }

        if (ModeloUsuario.actualizarContrasenaUsuario(usuario, nuevaContrasena, pregunta, respuesta)) {
            Alertas.mostrarInformacion("¡Contraseña cambiada exitosamente!");

            panelLogin.setVisible(true);
            paneNewPassword.setVisible(false);

            limpiarFormularioRegistro();
        } else {
            Alertas.mostrarError("Error al cambiar la contraseña");
        }
    }

    /* ==============================
     * Métodos para Transiciones de Formularios
     * ============================== */

    // Transición al formulario de recuperación de contraseña
     public void linkMostrarFormularioRecuperacion() {
        paneSecurityQuestion.setVisible(true);
        panelLogin.setVisible(false);
        cargarListaPreguntasRecuperacion();
    }

    // Volver al formulario de preguntas de seguridad
    public void btnVolverAlFormularioPreguntas(){
        paneSecurityQuestion.setVisible(true);
        paneNewPassword.setVisible(false);
    }

    // Volver al formulario de inicio de sesión
    public void btnVolverAlFormularioInicioSesion(){
        panelLogin.setVisible(true);
        paneSecurityQuestion.setVisible(false);
    }

    // Manejar transición entre formularios de login y registro
    public void btnAlternarFormulario(ActionEvent event) {
        TranslateTransition transicionFormulario = new TranslateTransition();

        if (event.getSource() == btnCreateAccount) {
            transicionFormulario.setNode(paneSidebar);
            transicionFormulario.setToX(400);
            transicionFormulario.setDuration(Duration.seconds(.5));

            transicionFormulario.setOnFinished((ActionEvent e) -> {
                btnAlreadyHaveAccount.setVisible(true);
                btnCreateAccount.setVisible(false);
                paneSecurityQuestion.setVisible(false);
                panelLogin.setVisible(true);
                paneNewPassword.setVisible(false);

                cargarListaPreguntasRegistro();
            });
            transicionFormulario.play();
        } else if (event.getSource() == btnAlreadyHaveAccount) {
            transicionFormulario.setNode(paneSidebar);
            transicionFormulario.setToX(0);
            transicionFormulario.setDuration(Duration.seconds(.5));

            transicionFormulario.setOnFinished((ActionEvent e) -> {
                btnAlreadyHaveAccount.setVisible(false);
                btnCreateAccount.setVisible(true);
                paneSecurityQuestion.setVisible(false);
                panelLogin.setVisible(true);
                paneNewPassword.setVisible(false);
            });
            transicionFormulario.play();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
