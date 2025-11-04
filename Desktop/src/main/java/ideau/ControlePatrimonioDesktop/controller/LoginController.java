package ideau.ControlePatrimonioDesktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class LoginController implements Initializable {

    @FXML
    private Button btnEntrar;

    @FXML
    private PasswordField edtSenha;

    @FXML
    private TextField edtUsuario;

    @FXML
    public void verificarLogin(ActionEvent event) throws IOException {
        String usuario = edtUsuario.getText().trim();
        String senha = edtSenha.getText().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            showMessage(Alert.AlertType.WARNING, "Preencha usuário e senha.");
            return;
        }

        try {
            // JSON do login
            String json = String.format("{\"usuario\":\"%s\",\"senha\":\"%s\"}", usuario, senha);

            // Cria e envia requisição HTTP para a API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login")) // URL da sua API
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Valida resposta da API
            if (response.statusCode() == 200) {
                abrirTelaPrincipal();
            } else if (response.statusCode() == 401) {
                edtSenha.setText("");
                showMessage(Alert.AlertType.WARNING, "Usuário ou senha inválidos!");
            } else {
                showMessage(Alert.AlertType.ERROR, "Erro inesperado: " + response.statusCode() + "\n" + response.body());
            }

        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao conectar com o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirTelaPrincipal() throws IOException {
        Stage telaLogin = (Stage) btnEntrar.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("/view/telaPrincipal.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Gestor IDEAU");
        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/icones-png/logo.png")));
        } catch (Exception e) {
            System.out.println("Logo da janela não encontrada! " + e.getMessage());
        }

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();

        telaLogin.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
