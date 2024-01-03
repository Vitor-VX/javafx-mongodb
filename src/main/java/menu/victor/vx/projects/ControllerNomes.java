package menu.victor.vx.projects;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControllerNomes implements Initializable {

    private ConnectMongo connectMongo = new ConnectMongo("Tiktok", "novos_users");

    private List<CheckBox> checkBoxes;

    @FXML
    private VBox vBox;

    @FXML
    private Label lblOut;

    @FXML
    private Label labelMsgDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxes = new ArrayList<>();
        loadNames();
    }

    public void loadNames() {
        List<String> nomes = new ArrayList<>();

        vBox.getChildren().clear();
        connectMongo.getNamesFromDatabase(nomes);

        for (String nome : nomes) {
            CheckBox checkBox = new CheckBox(nome);

            checkBoxes.add(checkBox);
            vBox.getChildren().add(checkBox);
        }
    }

    @FXML
    public void AddFromUserMongoDb() {
        try {
            EntryPoint.trocarTelaMenu("Principal");
            connectMongo.closeConnectionFromMongoDb();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void btn_DeletarMarcado() {
        connectMongo.DeleteUserFromMongoDb(checkBoxes, labelMsgDelete);
    }

    @FXML
    public void BtnFecharMenu() {
        EntryPoint.GetScene().close();
    }
}
