package menu.victor.vx.projects;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ConnectMongo {
    private MongoClient mongoClient;
    private String databaseName, colletionName;
    private String urlConnectMongo = System.getenv("CONNECT_URL_MONGO");

    public ConnectMongo(String databaseName, String colletionName) {
        this.databaseName = databaseName;
        this.colletionName = colletionName;
        this.mongoClient = MongoClients.create(urlConnectMongo);
    }

    public boolean AutheticationFromUser(TextField usuario, TextField senha) {
        try {
            MongoDatabase db = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> documentCollection = db.getCollection(colletionName);

            Document doc = documentCollection.find(and(eq("email", usuario.getText()), eq("senha", senha.getText())))
                    .first();

            return doc != null;
        } catch (Exception e) {
            handleException("Erro durante a autenticação do usuário", e);
            return false;
        }
    }

    public void AddNewFromUsers(TextField newNameUser, TextField newPasswordUser, Label text) {
        try {
            MongoDatabase db = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collectionInsert = db.getCollection(colletionName);

            if (!newNameUser.getText().isEmpty() && !newPasswordUser.getText().isEmpty()) {
                InsertOneResult insertOneResult = collectionInsert.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("email", newNameUser.getText())
                        .append("senha", newPasswordUser.getText()));

                if (insertOneResult.wasAcknowledged()) {
                    text.setText("Novo usuário com o nome \"" + newNameUser.getText() + "\" adicionado!");
                }
            }
        } catch (Exception e) {
            handleException("Erro ao tentar inserir o novo usuário", e);
        }
    }

    public void getNamesFromDatabase(List<String> nomesList) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collectionGetAllNames = db.getCollection(colletionName);

        try (MongoCursor<Document> cursor = collectionGetAllNames.find().iterator()) {
            while (cursor.hasNext()) {
                Document dc = cursor.next();

                String nome = dc.getString("email");
                nomesList.add(nome);
            }
        }
    }

    public void DeleteUserFromMongoDb(List<CheckBox> checkBoxes, Label textDelete) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collectionDelete = db.getCollection(colletionName);

        boolean selecionados = false;

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                String nomeDelete = checkBox.getText();

                collectionDelete.deleteOne(eq("email", nomeDelete));
                selecionados = true;
            }

            if (selecionados) {
                textDelete.setText("Nomes selecionados deletados com sucesso!");
                selecionados = !selecionados;
            } else {
                textDelete.setText("Nenhum nome selecionado!");
            }

            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    Platform.runLater(() -> textDelete.setText(""));
                } catch (Exception e) {
                    handleException("Erro ao tentar limpar a label: ", e);
                }
            }).start();
        }
    }

    public void closeConnectionFromMongoDb() {
        try {
            mongoClient.close();
        } catch (Exception e) {
            handleException("Erro ao fechar a conexão com o MongoDB", e);
        }
    }

    private void handleException(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
    }
}