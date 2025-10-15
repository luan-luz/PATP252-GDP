package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.PatrimonioDTO;
import ideau.ControlePatrimonioDesktop.model.RespostaHTTP;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class VisualizarPatrimonioController implements Initializable {

    @FXML
    private Button btnCadNumPatr;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditarPatr;

    @FXML
    private Button btnMudarLocal;

    @FXML
    private Button btnMudarStatus;

    @FXML
    private TableColumn<PatrimonioDTO, BigDecimal> colAliquota;

    @FXML
    private TableColumn<PatrimonioDTO, LocalDate> colDataAquisicao;

    @FXML
    private TableColumn<PatrimonioDTO, String> colLocal;

    @FXML
    private TableColumn<PatrimonioDTO, String> colNomeItem;

    @FXML
    private TableColumn<PatrimonioDTO, String> colNumPatr;

    @FXML
    private TableColumn<PatrimonioDTO, String> colStatus;

    @FXML
    private TableColumn<PatrimonioDTO, BigDecimal> colValCompra;

    @FXML
    private TextField edtPesquisa;

    @FXML
    private TableView<PatrimonioDTO> tblPatrimonios;

    private HTTPTransmit http;
    private ObjectMapper mapper;

    List<PatrimonioDTO> lstPatr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.http = new HTTPTransmit();
        this.mapper = new ObjectMapper();

        colNomeItem.setCellValueFactory(new PropertyValueFactory<>("nomeItem"));

        try {
            RespostaHTTP resp = http.get("http://localhost:8080/patrimonio");

            if (resp.getHttpStatus() > 206) {throw new RuntimeException("Status: " + resp.getHttpStatus() +
                                                                        "Erro: " + resp.getBody());}

            lstPatr = mapper.readValue(resp.getBody(), new TypeReference<List<PatrimonioDTO>>() {});
            tblPatrimonios.getItems().addAll(FXCollections.observableArrayList(lstPatr));
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar Patrimônios na tabela: " + e.getMessage());
        }
    }

    @FXML
    void abrirTelaSelecItem(ActionEvent event) {

    }

    @FXML
    void cadNumPatr(ActionEvent event) {

    }

    @FXML
    void editarPatr(ActionEvent event) {

    }

    @FXML
    void mudarLocal(ActionEvent event) {

    }

    @FXML
    void mudarStatus(ActionEvent event) {

    }

}
