package ideau.ControlePatrimonioDesktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class PrincipalController implements Initializable {

    @FXML
    private Button btnInventario;
    @FXML
    private Button btnRelatorio;
    @FXML
    private Button btnFechar;
    @FXML
    private AnchorPane container, anchorSidebar;
    @FXML
    private VBox boxSidebar;


    private boolean booMenuAberto = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        telaHome();
    }

    @FXML
    private void toggleMenu() {
        if (booMenuAberto) { //Fechar Menu
            anchorSidebar.setPrefWidth(0);
            boxSidebar.setPrefWidth(0);

            for (Node nodeHbox : boxSidebar.getChildren()) {
                if (nodeHbox instanceof HBox hbox) {
                    for (Node nodeItens : hbox.getChildren()) {
                        if (nodeItens instanceof Label label) {
                            label.setVisible(false);
                            label.setManaged(false);
                        }
                    }
                }
            }
        } else { //Abrir menu
            anchorSidebar.setPrefWidth(195);
            boxSidebar.setPrefWidth(195);

            for (Node nodeHbox : boxSidebar.getChildren()) {
                if (nodeHbox instanceof HBox hbox) {
                    for (Node nodeItens : hbox.getChildren()) {
                        if (nodeItens instanceof Label label) {
                            label.setVisible(true);
                            label.setManaged(true);
                        }
                    }
                }
            }
        }
        booMenuAberto = !booMenuAberto;
    }

    @FXML
    void fecharJanela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizarJanela(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    void carregarTela(String strNomeTela) throws IOException {
        try {
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/" + strNomeTela + ".fxml"));
            container.getChildren().clear();
            container.getChildren().add(novaTela);
            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela! Erro: " + e.getMessage());
        }
    }

    @FXML
    private void abrirHome(MouseEvent event) {
        try {
            carregarTela("telaHome");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }


    @FXML
    private void abrirCadastro(MouseEvent event) {
        try {
            carregarTela("telaCadastro");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Cadastro: " + e.getMessage());
        }
    }

    @FXML
    private void abrirInventario(MouseEvent event) {
        try {
            carregarTela("telaInventario");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Inventario: " + e.getMessage());
        }
    }

    @FXML
    private void abrirConfiguracao(MouseEvent event){
        try {
            carregarTela("telaConfiguracao");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Configuração: " + e.getMessage());
        }
    }


    @FXML
    private void abrirConta(MouseEvent event){
        try {
            carregarTela("telaConta");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Conta: " + e.getMessage());
        }
    }


    private void telaHome() {
        try {
            carregarTela("telaHome");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirRelatorios(){
        try {
            carregarTela("telaRelatorios");
        } catch (IOException e){
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar a tela Relatórios");
        }

    }



}
