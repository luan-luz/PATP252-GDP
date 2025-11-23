package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.model.NotaDTO;
import ideau.ControlePatrimonioDesktop.model.RespostaHTTP;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
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
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class VisualizarNotasController implements Initializable {

    @FXML
    private AnchorPane anchorPrincipal;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditar;

    @FXML
    private TableColumn<NotaDTO, Long> colId;
    @FXML
    private TableColumn<NotaDTO, String> colNumNota;

    @FXML
    private TableColumn<NotaDTO, LocalDate> colDtEmis;

    @FXML
    private TableColumn<NotaDTO, String> colSerie;

    @FXML
    private TableColumn<NotaDTO, String> colFornecedor;


    @FXML
    private TableColumn<NotaDTO, String> colChAcesso;

    @FXML
    private TableColumn<NotaDTO, BigDecimal> colValTot;

    @FXML
    private TextField edtPesquisa;

    @FXML
    private TableView<NotaDTO> tblNotas;

    HTTPTransmit http;
    private ObjectMapper mapper;
    DateTimeFormatter dateFormatter;

    List<NotaDTO> lstNotas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.http = new HTTPTransmit();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ObservableList<NotaDTO> obsList = null;

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumNota.setCellValueFactory(new PropertyValueFactory<>("numNota"));
        colSerie.setCellValueFactory(new PropertyValueFactory<>("serieNota"));
        colChAcesso.setCellValueFactory(new PropertyValueFactory<>("chaveNota"));
        colValTot.setCellValueFactory(new PropertyValueFactory<>("vlrTotal"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
        colDtEmis.setCellValueFactory(new PropertyValueFactory<>("dtEmissao"));
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/nota");

            if (resp.getHttpStatus() > 206) {throw new RuntimeException("Status: " + resp.getHttpStatus() +
                                                                        "Erro: " + resp.getBody());}

            lstNotas = mapper.readValue(resp.getBody(), new TypeReference<List<NotaDTO>>() {});
            obsList = FXCollections.observableArrayList(lstNotas);
            tblNotas.getItems().addAll(obsList);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar Patrimônios na tabela: " + e.getMessage());
        }

        //Pesquisa
        FilteredList<NotaDTO> lstFiltrada = new FilteredList<>(obsList, p -> true);

        edtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            lstFiltrada.setPredicate(obj -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }
                String filtro = newValue.toLowerCase();
                return obj.getId().toString().contains(filtro) || obj.getNumNota().toLowerCase().contains(filtro) ||
                        obj.getSerieNota().toLowerCase().contains(filtro) || obj.getChaveNota().contains(filtro) ||
                        obj.getNomeFornecedor().toLowerCase().contains(filtro);
            });

            SortedList<NotaDTO> lstOrdenada = new SortedList<>(lstFiltrada);
            lstOrdenada.comparatorProperty().bind(tblNotas.comparatorProperty());

            tblNotas.setItems(lstOrdenada);
        });

        //Deixando no formato dd/MM/yyyy
        colDtEmis.setCellFactory(column -> new TableCell<NotaDTO, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(item));
                }
            }
        });

    }

    @FXML
    void abrirCadastro(ActionEvent event) {
        try {
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/telaCadastroNotas.fxml"));
            AnchorPane container = (AnchorPane) anchorPrincipal.getParent();
            container.getChildren().clear();
            container.getChildren().add(novaTela);

            container.setPrefSize(1170, 658);
            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela de cadastro de Notas! Erro: " + e.getMessage());
        }
    }

    @FXML
    void editar() {
        try {
            NotaDTO selec = tblNotas.getSelectionModel().getSelectedItem();
            if (selec != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telaEdicaoNotas.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Editando Nota id: " + selec.getId());

                EdicaoNotasController controller = loader.getController();
                controller.setNotaDTO(selec);

                stage.showAndWait();
                NotaDTO dto = controller.getNotaDTO();

                if (dto != null) {
                    tblNotas.getItems().add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir tela de edição: " + e.getMessage());
        }
    }
}
