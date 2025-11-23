package ideau.ControlePatrimonioDesktop.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.controller.*;
import ideau.ControlePatrimonioDesktop.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

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

        Parent root = loader.load();

        Object controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Cadastrar " + strNomeTela);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Utils.class.getResource("/view/css/styles.css").toExternalForm());

        stage.setScene(scene);


        stage.showAndWait();
        //Todo novo controller de cadastro deve ser colocado aqui.
        //deve ter o método getLstCadastrados, para retornar a lista de objetos cadastrados e
        //colocar na tela de seleção
        if (controller instanceof CadastroItemController cadastroItemController) {
            return (List<T>) cadastroItemController.getLstCadastrados();
        } else if (controller instanceof CadastroLocalController cadastroLocalController) {
            return (List<T>) cadastroLocalController.getLstCadastrados();
        } else if (controller instanceof CadastroStatusController cadastroStatusController) {
            return (List<T>) cadastroStatusController.getLstCadastrados();
        } else if (controller instanceof CadastroCategoriaController cadastroCategoriaController) {
            return (List<T>) cadastroCategoriaController.getLstCadastrados();
        } else if (controller instanceof CadastroFornecedorController cadastroFornecedorController) {
            return (List<T>) cadastroFornecedorController.getLstCadastrados();
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

        //Todo novo controller de edição deve ser colocado aqui. Deve ter o método setObjEdicao
        if(controller instanceof EdicaoItemController edicaoController) {
            edicaoController.setObjEdicao((ItemDTO) obj);
        } else if(controller instanceof EdicaoNotasController edicaoController) {
            edicaoController.setObjEdicao((NotaDTO) obj);
        }

        stage.showAndWait();

        //Todo novo controller de edição deve ser colocado aqui. Deve ter o método getEditado, para retornar o
        // objeto editado e colocar na tabela
        if(controller instanceof EdicaoItemController edicaoController) {
            return (T) edicaoController.getEditado();
        } else if(controller instanceof EdicaoNotasController edicaoController) {
            return (T) edicaoController.getEditado();
        }

        return null;
    }
    public static UnaryOperator<TextFormatter.Change> retFiltroCampoValor() {
        return change -> {
            String newTxt = change.getControlNewText();
            if (newTxt.matches("\\d*(\\,\\d{0,2}|\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        };
    }
    public static UnaryOperator<TextFormatter.Change> retFiltroCampoPorcent(String strCasasDecimais) {
        return change -> {
            String newTxt = change.getControlNewText();
            if (newTxt.matches("([0-9]{0,2})([\\.,][0-9]{0,"+ strCasasDecimais +"})?")
                    || newTxt.equals("100")
                    || newTxt.equals("100,00")
                    || newTxt.equals("100.00")) {
                return change;
            }
            return null;
        };
    }
    public static List<Local> getLocaisAPI() {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/local");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                return mapper.readValue(resp.getBody(), new TypeReference<List<Local>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        return null;
    }

    public static List<Status> getStatusAPI() {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/status");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                return mapper.readValue(resp.getBody(), new TypeReference<List<Status>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        return null;
    }

    public static List<ItemDTO> getItensAPI() {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/item");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                return mapper.readValue(resp.getBody(), new TypeReference<List<ItemDTO>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        return null;
    }
    public static List<NotaDTO> getNotasAPI() {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/nota");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                return mapper.readValue(resp.getBody(), new TypeReference<List<NotaDTO>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        return null;
    }
    public static List<FornecedorDTO> getFornecedoresAPI() {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/fornecedor");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                return mapper.readValue(resp.getBody(), new TypeReference<List<FornecedorDTO>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        return null;
    }
    public static List<Categoria> getCategoriasAPI() {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/categoria");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                return mapper.readValue(resp.getBody(), new TypeReference<List<Categoria>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        return null;
    }
}
