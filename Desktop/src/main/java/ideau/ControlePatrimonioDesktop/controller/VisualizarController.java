package ideau.ControlePatrimonioDesktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class VisualizarController {

    @FXML
    private Button btnFornecedores;

    @FXML
    private Button btnNotas;

    @FXML
    private Button btnPatrimonios;

    @FXML
    private Button btnUsuarios;

    @FXML
    private AnchorPane container;

    @FXML
    void abrirCadastroFornecedores(MouseEvent event) {

    }

    @FXML
    void abrirCadastroPatrimonios(MouseEvent event) {

    }

    @FXML
    void abrirCadastroUsuarios(MouseEvent event) {

    }

    @FXML
    void abrirVisualizarFornecedores(ActionEvent event) { carregarTela("telaVisualizarFornecedores"); }

    @FXML
    void abrirVisualizarNotas(ActionEvent event) { carregarTela("telaVisualizarNotas"); }

    @FXML
    void abrirVisualizarPatrimonio(ActionEvent event) {
        carregarTela("telaVisualizarPatrimonio");
    }

    @FXML
    void abrirVisualizarUsuario(ActionEvent event) {

    }
    @FXML
    void carregarTela(String strNomeTela) {
        try {
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/"+ strNomeTela +".fxml"));
            container.getChildren().clear();
            container.getChildren().add(novaTela);

            container.setPrefSize(1170, 658);
            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela! Erro: " + e.getMessage());
        }
    }
}

