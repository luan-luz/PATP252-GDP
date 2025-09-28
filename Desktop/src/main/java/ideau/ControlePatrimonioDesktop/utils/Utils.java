package ideau.ControlePatrimonioDesktop.utils;

import ideau.ControlePatrimonioDesktop.controller.CadastroItemController;
import ideau.ControlePatrimonioDesktop.controller.EdicaoItemController;
import ideau.ControlePatrimonioDesktop.controller.SelecaoController;
import ideau.ControlePatrimonioDesktop.model.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Collections;
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

    @SuppressWarnings("unchecked")
    public static <T> List<T> abrirTelaCadastro(String strNomeTela) throws IOException {
        FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/view/telaCadastro" + strNomeTela + ".fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cadastrar " + strNomeTela);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));
        Object controller = loader.getController();


        stage.showAndWait();
        //Todo novo controller de cadastro deve ser colocado aqui.
        //deve ter o método getLstCadastrados, para retornar a lista de objetos cadastrados e
        //colocar na tela de seleção
        if (controller instanceof CadastroItemController cadastroItemController) {
            return (List<T>) cadastroItemController.getLstCadastrados();
        }

        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public static <T> T abrirTelaEdicao(String strNomeTela, T obj) throws IOException {
        FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/view/telaEdicao" + strNomeTela + ".fxml"));
        Stage stage = new Stage();
        stage.setTitle("Editar " + strNomeTela);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));
        Object controller = loader.getController();

        //Todo novo controller de edição deve ser colocado aqui. Deve ter o método setObjEdicao, para retornar o
        // objeto editado e colocar na tabela
        if(controller instanceof EdicaoItemController edicaoController) {
            edicaoController.setObjEdicao((ItemDTO) obj);
        }

        stage.showAndWait();

        //Todo novo controller de edição deve ser colocado aqui. Deve ter o método getEditado, para retornar o
        // objeto editado e colocar na tabela
        if(controller instanceof EdicaoItemController edicaoController) {
            return (T) edicaoController.getEditado();
        }

        return null;
    }
}
