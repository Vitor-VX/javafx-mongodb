package menu.victor.vx.projects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerLogin  {
    
	private ConnectMongo connectMongo = new ConnectMongo("Tiktok", "usuarios");
	
    @FXML
    private TextField TextSenha;

    @FXML
    private TextField TextUsuario;

    @FXML
    private Label msgMenu;

    @FXML
    public void startLoginBtn() {
    	if (connectMongo.AutheticationFromUser(TextUsuario, TextSenha)) {
    		try {
    			EntryPoint.trocarTelaMenu("Principal");
				connectMongo.closeConnectionFromMongoDb();
    		} catch (Exception e) {
    			msgFunction("Ocorreu um erro ao mandar a pagina de acesso!");
    		}
    	}
    	
    	msgFunction("Usuário ou senha incorretos!");
    }

	public void msgFunction(String msg) {
    	msgMenu.setText(msg);
    }
}