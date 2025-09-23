package ideau.ControlePatrimonioDesktop.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.abrirTelaCadastro;

public class SelecaoController<T> implements Initializable {
    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSelecionar;

    @FXML
    private TextField edtPesquisa;

    @FXML
    private TableView<T> tblSelecao;

    private T selecionado;
    private Stage stage;

    private String nomeTela;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        btnEditar.setVisible(false);
        btnSelecionar.setVisible(false);

        btnCadastrar.setOnAction(event -> {
            try {
                abrirTelaCadastro(nomeTela);
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro: " + e.getMessage());
            }
        });

        btnEditar.setOnAction(event -> {
            //abrir tela cadastro para editar
        });

        tblSelecao.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(eventClick -> {
                if (eventClick.getClickCount() == 1 && !row.isEmpty()) {
                    btnEditar.setVisible(true);
                    btnSelecionar.setVisible(true);
                }
                if (eventClick.getClickCount() == 2 && !row.isEmpty()) {
                    selecionado = row.getItem();
                    stage.close();
                }
            });
        return row;
        });

        tblSelecao.setOnKeyPressed(evtKeyPress -> {
            if (evtKeyPress.getCode() == KeyCode.ENTER || evtKeyPress.getCode() == KeyCode.SPACE) {
                T dadoLinha = tblSelecao.getSelectionModel().getSelectedItem();
                if (dadoLinha != null) {
                    selecionado = dadoLinha;
                    stage.close();
                }
            }
        });

        btnSelecionar.setOnAction(event -> {
            T dadoLinha = tblSelecao.getSelectionModel().getSelectedItem();
            if (dadoLinha != null) {
                selecionado = dadoLinha;
                stage.close();
            }
        });

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDadosLista(ObservableList<T> listaDados, Map<String, String> mapColunas) {
        tblSelecao.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
        for (Map.Entry<String, String> entry : mapColunas.entrySet()) {
            TableColumn<T, String> col = new TableColumn<>(entry.getKey());
            col.setCellValueFactory((new PropertyValueFactory<>(entry.getValue())));
            this.tblSelecao.getColumns().add(col);
            this.tblSelecao.getColumns().getFirst().setStyle("-fx-alignment: CENTER;");
            this.tblSelecao.getColumns().getFirst().setMaxWidth(80);
        }
        tblSelecao.setItems(listaDados);
    }

    public T getSelecionado() {
        return selecionado;
    }

    public void setNomeTela(String nomeTela) {
        this.nomeTela = nomeTela;
    }
}
