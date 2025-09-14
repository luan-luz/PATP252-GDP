package ideau.ControlePatrimonioDesktop.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CadastroController {
    @FXML
    private AnchorPane container_cadastro;

    @FXML
    void carregarTela(String strNomeTela) throws IOException {
        try {
            //Não testei essa rotina, mas acho que funciona :P
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/"+ strNomeTela +".fxml"));
            container_cadastro.getChildren().clear();
            container_cadastro.getChildren().add(novaTela);

            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela! Erro: " + e.getMessage());
        }
    }

    @FXML
    private void abrirCadastroPatrimonios(MouseEvent event) {
        try {
            carregarTela("telaCadastroPatrimonio");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }


    @FXML
    private void abrirCadastroItens(MouseEvent event) {
        try {
            carregarTela("telaCadastroItens");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }


    @FXML
    private void abrirCadastroSetores(MouseEvent event) {
        try {
            carregarTela("telaCadastroSetores");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }
    @FXML
    private void abrirCadastroCategorias(MouseEvent event) {
        try {
            carregarTela("telaCadastroCategorias");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }
    @FXML
    private void abrirCadastroFornecedores(MouseEvent event) {
        try {
            carregarTela("telaCadastroFornecedores");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }
    @FXML
    private void abrirCadastroUsuarios(MouseEvent event) {
        try {
            carregarTela("telaCadastroUsuarios");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }
    @FXML
    private void abrirCadastroAuditoria(MouseEvent event) {
        try {
            carregarTela("telaCadastroAuditoria");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }
}
