package menu.victor.vx.projects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerPrincipal {

    private ConnectMongo connectMongo = new ConnectMongo("Tiktok", "novos_users");

    @FXML
    private Label labelMsgPainel;

    @FXML
    private Label lblOut;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;

    @FXML
    public void AddFromUserMongoDb() {
        connectMongo.AddNewFromUsers(txtNome, txtSenha, labelMsgPainel);
    }

    @FXML
    public void BtnFecharMenu() {
        EntryPoint.GetScene().close();
        connectMongo.closeConnectionFromMongoDb();
    }

    @FXML
    public void ListFromNamesDataBase() {
        try {
            EntryPoint.trocarTelaMenu("Nomes");
        } catch (Exception e) {
            System.out.println("Erro ao trocar de tela: " + e);
        }
    }

}