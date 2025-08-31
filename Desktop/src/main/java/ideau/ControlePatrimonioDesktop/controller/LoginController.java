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
import java.net.URL;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class LoginController implements Initializable{

    @FXML
    private Button btnEntrar;

    @FXML
    private PasswordField edtSenha;

    @FXML
    private TextField edtUsuario;

    @FXML
    public void verificarLogin(ActionEvent event) throws IOException {
        String strUsuarioDigitado, strSenhaDigitada;
        try {
            strUsuarioDigitado = edtUsuario.getText();
            strSenhaDigitada   = edtSenha.getText();
            Stage telaLogin = (Stage) btnEntrar.getScene().getWindow();

            //Provisório para testar
            if (strUsuarioDigitado == "" && strSenhaDigitada == "") {
                Parent root = FXMLLoader.load(getClass().getResource("/view/telaPrincipal.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Controle de Patrimônio");
                try {
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/imagens/logo_ideau.png")));
                } catch (Exception e) {
                    //adicionar uns logs """mais melhor""", por enquanto vamos de sout :P
                    System.out.println("Logo da janela não encontrada! " + e.getMessage()); //se não achar executa normal, apenas avisa que nao achou
                }
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();

                telaLogin.close();
            } else {
                edtSenha.setText("");
                showMessage(Alert.AlertType.WARNING, "Usuário ou senha inválidos!");
            }

        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro no login! Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
