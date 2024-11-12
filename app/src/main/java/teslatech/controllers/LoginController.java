package teslatech.controllers;

import teslatech.utils.Alerts;
import teslatech.Datos;
import teslatech.models.UserModel;

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
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<String> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<String> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;

    private String[] questionList = {
        "¿Cuál es tu color favorito?",
        "¿Cuál es tu comida favorita?",
        "¿Cuál es tu fecha de nacimiento?"
    };

    /* ==============================
     * Métodos para el Inicio de Sesión
     * ============================== */

    // Método para manejar el inicio de sesión
    public void loginBtn() {
        String username = si_username.getText();
        String password = si_password.getText();

        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            Alerts.showError("Nombre de usuario/contraseña incorrectos");

            return;
        }

        if (UserModel.isValidUser(username, password)) {
            Alerts.showInfo("¡Inicio de sesión exitosamente!");

            Datos.username = username;
            openMainForm();
        } else {
            Alerts.showError("Nombre de usuario/contraseña incorrectos");
        }
    }

    // Método para abrir la vista principal
    private void openMainForm() {
        try {
            System.out.println(getClass().getResource("/views/main.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
            Stage stage = new Stage();
            Image icon = new Image(getClass().getResourceAsStream("/logo/tesla-tech.png"));

            stage.setTitle("Tesla Tech");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            si_loginBtn.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ==============================
     * Métodos para el Registro de Usuarios
     * ============================== */

    // Método para el registro de usuarios
    public void regBtn() {
        String username = su_username.getText();
        String password = su_password.getText();
        String question = su_question.getSelectionModel().getSelectedItem();
        String answer = su_answer.getText();

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null || su_answer.getText().isEmpty()) {
            Alerts.showError("Por favor, rellene todos los campos en blanco");

            return;
        }

        if (UserModel.isUsernameTaken(username)) {
            Alerts.showError("El nombre de usuario ya está en uso");
        } else if (password.length() < 8) {
            Alerts.showError("La contraseña debe tener al menos 8 caracteres");
        } else if (UserModel.registerUser(username, password, question, answer)) {
            Alerts.showInfo("¡Cuenta registrada con éxito!");
            clearSignupForm();
        }
    }

    // Limpiar formulario de registro
    private void clearSignupForm() {
        su_username.setText("");
        su_password.setText("");
        su_question.getSelectionModel().clearSelection();
        su_answer.setText("");

        np_confirmPassword.setText("");
        np_newPassword.setText("");
        fp_question.getSelectionModel().clearSelection();
        fp_answer.setText("");
        fp_username.setText("");
    }

    // Cargar lista de preguntas de seguridad en el formulario de registro
    public void regLquestionList() {
        ObservableList<String> listData = FXCollections.observableArrayList(questionList);
        su_question.setItems(listData);
    }

    /* ==============================
     * Métodos para Recuperación de Contraseña
     * ============================== */

    // Mostrar preguntas de seguridad en el formulario de recuperación
    public void forgotPassQuestionList() {
        ObservableList<String> listData = FXCollections.observableArrayList(questionList);
        fp_question.setItems(listData);
    }

    // Mostrar preguntas de seguridad en el formulario de recuperación
    public void proceedBtn() {
        String username = fp_username.getText();
        String question = fp_question.getSelectionModel().getSelectedItem();
        String answer = fp_answer.getText();

        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null || fp_answer.getText().isEmpty()) {
            Alerts.showError("Por favor, rellene todos los campos en blanco");

            return;
        }

        if (UserModel.isValidRecoveryData(username, question, answer)) {
            Alerts.showInfo("Usuario encontrado");
            np_newPassForm.setVisible(true);
            fp_questionForm.setVisible(false);
        } else {
            Alerts.showError("Usuario no encontrado");
        }
    }

    // Cambio de contraseña
    public void changePassBtn() {
        String newPassword = np_newPassword.getText();
        String confirmPassword = np_confirmPassword.getText();
        String username = fp_username.getText();
        String queston = fp_question.getSelectionModel().getSelectedItem();
        String answer = fp_answer.getText();

        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            Alerts.showError("Por favor, rellene todos los campos en blanco");

            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Alerts.showError("Las contraseñas no coinciden");

            return;
        }

        if (UserModel.updateUserPassword(username, newPassword, queston, answer)) {
            Alerts.showInfo("¡Contraseña cambiada exitosamente!");

            si_loginForm.setVisible(true);
            np_newPassForm.setVisible(false);

            clearSignupForm();
        } else {
            Alerts.showError("Error al cambiar la contraseña");
        }
    }

    /* ==============================
     * Métodos para Transiciones de Formularios
     * ============================== */

    // Transición al formulario de recuperación de contraseña
     public void switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        forgotPassQuestionList();
    }

    // Volver al formulario de inicio de sesión
    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }

    // Volver al formulario de preguntas de seguridad
    public void backToQuestionForm(){
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    // Manejar transición entre formularios de login y registro
    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(400);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);
                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                regLquestionList();
            });
            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);
                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });
            slider.play();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // @Override
    // public void initialize(URL location, ResourceBundle resources) {
    // }
}
