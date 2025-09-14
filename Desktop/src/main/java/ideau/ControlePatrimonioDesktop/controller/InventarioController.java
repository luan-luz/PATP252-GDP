package ideau.ControlePatrimonioDesktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class InventarioController {

    @FXML
    private AnchorPane container_inventario;

    @FXML
    void carregarTela(String strNomeTela) throws IOException {
        try {
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/"+ strNomeTela +".fxml"));
            container_inventario.getChildren().clear();
            container_inventario.getChildren().add(novaTela);

            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela! Erro: " + e.getMessage());
        }
    }

    @FXML
    private void abrirInventarioPatrimonios(MouseEvent event) {
        try {
            carregarTela("telaInventarioPatrimonio");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirInventarioItens(MouseEvent event) {
        try {
            carregarTela("telaInventarioItens");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirInventarioSetores(MouseEvent event) {
        try {
            carregarTela("telaInventarioSetores");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirInventarioCategorias(MouseEvent event) {
        try {
            carregarTela("telaInventarioCategorias");
        } catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void abrirCadastro(MouseEvent event) {
        try {
            System.out.println("Teste");
        } /*catch (IOException e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela Home: " + e.getMessage());
        }*/finally {

        }
    }
}
