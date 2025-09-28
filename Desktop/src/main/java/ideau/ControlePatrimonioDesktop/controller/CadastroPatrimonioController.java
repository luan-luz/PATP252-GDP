package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.*;

import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import ideau.ControlePatrimonioDesktop.utils.ShowMessage;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.abrirTelaSelecao;

public class CadastroPatrimonioController implements Initializable {
    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditarPatr;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnRemoverPatr;

    @FXML
    private Button btnSelecItem;

    @FXML
    private Button btnSelecNota;

    @FXML
    private Button btnSelecSetor;

    @FXML
    private Button btnSelecStatus;

    @FXML
    private TextField edtNomeItem;

    @FXML
    private TextField edtNumNota;

    @FXML
    private TextField edtNumPatr;

    @FXML
    private TextField edtSetor;

    @FXML
    private TextField edtStatus;

    @FXML
    private TextField edtSerie;

    @FXML
    private TableView<PatrimonioDTO> tblPatrimonios;

    @FXML
    private TableColumn<PatrimonioDTO, String> colNomeItem;

    @FXML
    private TableColumn<PatrimonioDTO, String> colNumNota;

    @FXML
    private TableColumn<PatrimonioDTO, String> colNumPatr;

    @FXML
    private TableColumn<PatrimonioDTO, String> colSerieNota;

    @FXML
    private TableColumn<PatrimonioDTO, String> colSetor;

    @FXML
    private TableColumn<PatrimonioDTO, String> colStatus;

    public void initialize(URL Location, ResourceBundle resources) {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();

        colNomeItem.setCellValueFactory(new PropertyValueFactory<>("nomeItem"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("nomeStatus"));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("nomeSetor"));
        colNumPatr.setCellValueFactory(new PropertyValueFactory<>("numPatr"));
        colNumNota.setCellValueFactory(new PropertyValueFactory<>("numNota"));
        colSerieNota.setCellValueFactory(new PropertyValueFactory<>("serieNota"));

        //Item
        btnSelecItem.setOnAction(evt -> {
            try {
                RespostaHTTP resp = http.get("http://localhost:8080/item");

                if (resp == null) {
                    showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
                } else if (resp.getHttpStatus() < 206) { //Status menores que 206 são retornos válidos
                    List<ItemDTO> itens = mapper.readValue(resp.getBody(), new TypeReference<List<ItemDTO>>() {});
                    Map<String, String> colunas = new LinkedHashMap<>();
                    colunas.put("ID", "id");
                    colunas.put("Nome Do Item", "nomeItem");
                    colunas.put("Categoria", "nomeCategoria");
                    ItemDTO selecionado = abrirTelaSelecao(itens, colunas, "Itens");
                    if (selecionado != null) {
                        edtNomeItem.setText(selecionado.getNomeItem());
                    }
                } else {
                    throw new RuntimeException("Status" + resp.getBody() + " Erro: " + resp.getBody());
                }
            } catch (Exception e) {
                String msg = e.getMessage();
                if (msg == null || msg.isBlank()) {
                    msg = "Não foi possível se conectar ao Servidor!\nVerifique se a API está rodando.";
                }
                showMessage(Alert.AlertType.ERROR, "Erro ao carregar itens na tabela: " + msg);
            }
        });
    }

    @FXML
    void AbrirTelaSelecSetor(ActionEvent event) {
        List<Setor> lstSetor = Arrays.asList(
                new Setor(1L, "TI"),
                new Setor(1L, "Laboratório"),
                new Setor(1L, "Atendimento")
        );
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome Do Setor", "nome");
        try {
            Setor objSetor = abrirTelaSelecao(lstSetor, colunas,"Setores");
            if (objSetor != null) {edtSetor.setText(objSetor.getNome());}
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Setores!");
        }
    }

    @FXML
    void AbrirTelaSelecStatus(ActionEvent event) {
        List<Status> lstStatus = Arrays.asList(
                new Status(1L, "Ativo"),
                new Status(1L, "Em uso"),
                new Status(1L, "Em manutenção")
        );
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome Do Status", "nome");
        try {
            Status objStatus = abrirTelaSelecao(lstStatus, colunas,"Status");
            if (objStatus != null) {edtStatus.setText(objStatus.getNome());}
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Status!");
        }
    }

    @FXML
    void AddPatrTbl(ActionEvent event) {
        edtSerie.setText("1");
        tblPatrimonios.getItems().add(new PatrimonioDTO(edtNomeItem.getText(),
                                                        edtStatus.getText(),
                                                        edtSetor.getText(),
                                                        edtNumNota.getText(),
                                                        edtSerie.getText(),
                                                        edtNumPatr.getText()
        ));
    }
}
