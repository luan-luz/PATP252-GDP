package ideau.ControlePatrimonioDesktop.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class ConfiguracaoController {

    @FXML
    private AnchorPane container_configuracao;

    @FXML
    void carregarTela(String strNomeTela) throws IOException {
        try {
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/"+ strNomeTela +".fxml"));
            container_configuracao.getChildren().clear();
            container_configuracao.getChildren().add(novaTela);

            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela! Erro: " + e.getMessage());
        }
    }

    @FXML
    private void abrirConfiguracaoParametros(MouseEvent event) {
        try {
            carregarTela("");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirConfiguracaoAcessos(MouseEvent event) {
        try {
            carregarTela("");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirConfiguracaoGrupos(MouseEvent event) {
        try {
            carregarTela("");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirConfiguracaoBkp(MouseEvent event) {
        try {
            carregarTela("");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirConfiguracaoLogs(MouseEvent event) {
        try {
            carregarTela("");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }
}
