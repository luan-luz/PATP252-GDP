package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.model.PatrimonioDTO;
import ideau.ControlePatrimonioDesktop.model.RespostaHTTP;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import ideau.ControlePatrimonioDesktop.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class VisualizarPatrimonioController implements Initializable {

    @FXML
    private AnchorPane anchorPrincipal;

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
    private TableColumn<PatrimonioDTO, Long> colId;
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
        mapper.registerModule(new JavaTimeModule());
        ObservableList<PatrimonioDTO> obsListPatr = null;

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeItem.setCellValueFactory(new PropertyValueFactory<>("nomeItem"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("nomeStatus"));
        colLocal.setCellValueFactory(new PropertyValueFactory<>("nomeLocal"));
        colNumPatr.setCellValueFactory(new PropertyValueFactory<>("numPatr"));
        colValCompra.setCellValueFactory(new PropertyValueFactory<>("valCompra"));
        colAliquota.setCellValueFactory(new PropertyValueFactory<>("aliqDeprecMes"));
        colDataAquisicao.setCellValueFactory(new PropertyValueFactory<>("dtAquisicao"));
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/patrimonio");

            if (resp.getHttpStatus() > 206) {throw new RuntimeException("Status: " + resp.getHttpStatus() +
                                                                        "Erro: " + resp.getBody());}

            lstPatr = mapper.readValue(resp.getBody(), new TypeReference<List<PatrimonioDTO>>() {});
            obsListPatr = FXCollections.observableArrayList(lstPatr);
            tblPatrimonios.getItems().addAll(obsListPatr);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar Patrimônios na tabela: " + e.getMessage());
        }

        //Pesquisa
        FilteredList<PatrimonioDTO> lstFiltrada = new FilteredList<>(obsListPatr, p -> true);

        edtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            lstFiltrada.setPredicate(patr -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }
                String filtro = newValue.toLowerCase();
                return patr.getId().toString().contains(filtro) || patr.getNomeItem().toLowerCase().contains(filtro) ||
                        patr.getNomeStatus().toLowerCase().contains(filtro) || patr.getNomeLocal().contains(filtro) ||
                        patr.getNumPatr().toLowerCase().contains(filtro);
            });

            SortedList<PatrimonioDTO> lstOrdenada = new SortedList<>(lstFiltrada);
            lstOrdenada.comparatorProperty().bind(tblPatrimonios.comparatorProperty());

            tblPatrimonios.setItems(lstOrdenada);
        });
    }

    @FXML
    void abrirCadastroPatrimonio(ActionEvent event) {
        try {
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/telaCadastroPatrimonio.fxml"));
            AnchorPane container = (AnchorPane) anchorPrincipal.getParent();
            container.getChildren().clear();
            container.getChildren().add(novaTela);

            container.setPrefSize(1170, 658);
            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela de cadastro de Patrimônios! Erro: " + e.getMessage());
        }
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
