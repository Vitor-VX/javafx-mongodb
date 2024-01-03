package menu.victor.vx.projects;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EntryPoint extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        trocarTelaMenu("Login");
        primaryStage.initStyle(StageStyle.UNDECORATED); 
        primaryStage.setResizable(false); 
        primaryStage.show();
    }

    public static void trocarTelaMenu(String fxml) throws IOException {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/fxml/" + fxml + ".fxml"));
            Scene sc = new Scene(root);

            stage.setScene(sc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage GetScene() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}