package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.model.Fornecedor;
import ideau.ControlePatrimonioDesktop.model.FornecedorDTO;
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

public class VisualizarFornecedoresController implements Initializable {

    @FXML
    private AnchorPane anchorPrincipal;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditar;

    @FXML
    private TableColumn<FornecedorDTO, Long> colId;
    @FXML
    private TableColumn<FornecedorDTO, String> colRazao;

    @FXML
    private TableColumn<FornecedorDTO, String> colFantasia;

    @FXML
    private TableColumn<FornecedorDTO, String> colCnpj;

    @FXML
    private TableColumn<FornecedorDTO, String> colIe;


    @FXML
    private TableColumn<FornecedorDTO, String> colLogradouro;

    @FXML
    private TableColumn<FornecedorDTO, String> colComplemento;

    @FXML
    private TableColumn<FornecedorDTO, String> colNumero;

    @FXML
    private TextField edtPesquisa;

    @FXML
    private TableView<FornecedorDTO> tblFornec;

    HTTPTransmit http;
    private ObjectMapper mapper;
    DateTimeFormatter dateFormatter;

    List<FornecedorDTO> lstFornec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.http = new HTTPTransmit();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ObservableList<FornecedorDTO> obsList = null;

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRazao.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));
        colFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("CNPJ"));
        colIe.setCellValueFactory(new PropertyValueFactory<>("IE"));
        colLogradouro.setCellValueFactory(new PropertyValueFactory<>("nomeLogradouro"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colComplemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/fornecedor");

            if (resp.getHttpStatus() > 206) {throw new RuntimeException("Status: " + resp.getHttpStatus() +
                                                                        "Erro: " + resp.getBody());}

            lstFornec = mapper.readValue(resp.getBody(), new TypeReference<List<FornecedorDTO>>() {});
            obsList = FXCollections.observableArrayList(lstFornec);
            tblFornec.getItems().addAll(obsList);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar Patrimônios na tabela: " + e.getMessage());
        }

        //Pesquisa
        FilteredList<FornecedorDTO> lstFiltrada = new FilteredList<>(obsList, p -> true);

        edtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            lstFiltrada.setPredicate(obj -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }
                String filtro = newValue.toLowerCase();
                return obj.getId().toString().contains(filtro) || obj.getRazaoSocial().toLowerCase().contains(filtro) ||
                        obj.getNomeFantasia().toLowerCase().contains(filtro) || obj.getCNPJ().contains(filtro) ||
                        obj.getIE().toLowerCase().contains(filtro);
            });

            SortedList<FornecedorDTO> lstOrdenada = new SortedList<>(lstFiltrada);
            lstOrdenada.comparatorProperty().bind(tblFornec.comparatorProperty());

            tblFornec.setItems(lstOrdenada);
        });
    }

    @FXML
    void abrirCadastro(ActionEvent event) {
        try {
            Parent novaTela = new FXMLLoader().load(getClass().getResource("/view/telaCadastroFornecedores.fxml"));
            AnchorPane container = (AnchorPane) anchorPrincipal.getParent();
            container.getChildren().clear();
            container.getChildren().add(novaTela);

            container.setPrefSize(1170, 658);
            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar tela de cadastro de Fornecedores! Erro: " + e.getMessage());
        }
    }

    @FXML
    void editar() {
        showMessage(Alert.AlertType.ERROR, "Ainda não implementado!");
//        try {
//            FornecedorDTO selec = tblFornec.getSelectionModel().getSelectedItem();
//            if (selec != null) {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telaEdicaoFornecedores.fxml"));
//                Stage stage = new Stage();
//                stage.setScene(new Scene(loader.load()));
//                stage.setTitle("Editando Nota id: " + selec.getId());

//                EdicaoNotasController controller = loader.getController();
//                controller.setFornecedorDTO(selec);

//                stage.showAndWait();
//                FornecedorDTO dto = controller.getFornecedorDTO();

//                if (dto != null) {
//                    tblNotas.getItems().add(dto);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showMessage(Alert.AlertType.ERROR, "Erro ao abrir tela de edição: " + e.getMessage());
//        }
    }
}
