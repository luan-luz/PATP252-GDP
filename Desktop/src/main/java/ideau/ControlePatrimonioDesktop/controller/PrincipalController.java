package ideau.ControlePatrimonioDesktop.controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class PrincipalController implements Initializable {

    @FXML
    private StackPane btnFechar;
    @FXML
    private AnchorPane container, anchorSidebar;
    @FXML
    private VBox boxSidebar;

    private boolean booMenuAberto = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    void carregarTela(String strNomeTela) throws IOException {
        try {
            //Não testei essa rotina, mas acho que funciona :P
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/"+ strNomeTela +"fxml"));
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
}
