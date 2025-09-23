package ideau.ControlePatrimonioDesktop.utils;

import ideau.ControlePatrimonioDesktop.controller.SelecaoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Utils {

    public static <T> T abrirTelaSelecao(List<T> dados, Map<String, String> colunas, String nomeTela) throws IOException {
        FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/view/telaSelecaoGenerica.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Selecionar " + nomeTela);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));

        SelecaoController<T> controller = loader.getController();
        controller.setStage(stage);
        controller.setNomeTela(nomeTela);

        controller.setDadosLista(FXCollections.observableArrayList(dados), colunas);

        stage.showAndWait();

        return controller.getSelecionado();
    }

    public static void abrirTelaCadastro(String strNomeTela) throws IOException {
        FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/view/telaCadastro" + strNomeTela + ".fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cadastrar " + strNomeTela);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));
        loader.getController();
        stage.showAndWait();
    }
}
