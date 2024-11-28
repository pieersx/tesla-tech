package teslatech;

// Estos importes corresponden a las clases necesarias para crear una aplicación JavaFX:
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// La clase App extiende Application, lo que la convierte en una aplicación JavaFX
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            System.out.println(getClass().getResource("/views/login.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/logo/tesla-tech.png"));

            stage.setTitle("Tesla Tech");
            stage.getIcons().add(icon);
            stage.setMaxWidth(800);
            stage.setMaxHeight(650);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
