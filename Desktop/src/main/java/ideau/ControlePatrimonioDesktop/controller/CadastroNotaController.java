package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.model.*;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import ideau.ControlePatrimonioDesktop.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.*;

public class CadastroNotaController implements Initializable {

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnLimparCampos;

    @FXML
    private Button btnSelecFornec;

    @FXML
    private TableColumn<NotaDTO, String> colChAcessoNfe;

    @FXML
    private TableColumn<NotaDTO, LocalDate> colDtEmissao;

    @FXML
    private TableColumn<NotaDTO, String> colFornecedor;

    @FXML
    private TableColumn<NotaDTO, String> colNumNfe;

    @FXML
    private TableColumn<NotaDTO, String> colSerieNfe;

    @FXML
    private TableColumn<NotaDTO, BigDecimal> colValTot;
    @FXML
    private DatePicker dtpDtAquisicao;

    @FXML
    private TextField edtChAcesso;

    @FXML
    private TextField edtFornecedor;

    @FXML
    private TextField edtNumNfe;

    @FXML
    private TextField edtSerieNfe;

    @FXML
    private TextField edtValTot;

    @FXML
    private Label lblNumPatr;

    @FXML
    private Region regEsquerdo;

    HTTPTransmit http;
    ObjectMapper mapper;
    DateTimeFormatter dateFormatter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.http = new HTTPTransmit();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @FXML
    void AbrirTelaSelecFornec(ActionEvent event) {
        try {
            Map<String, String> mapColunas = new LinkedHashMap<>();
            List<FornecedorDTO> dados = Utils.getFornecedoresAPI();
            if (dados != null) {
                mapColunas.put("ID", "id");
                mapColunas.put("Razão Social", "razaoSocial");
                mapColunas.put("Nome Fantasia", "nomeFantasia");
                mapColunas.put("CNPJ", "CNPJ");
                mapColunas.put("Insc. Estad.", "IE");
                mapColunas.put("Logradouro", "nomeLogradouro");
                mapColunas.put("Número", "numero");
                mapColunas.put("Complemento", "complemento");
                FornecedorDTO selec = abrirTelaSelecao(dados, mapColunas, "Fornecedores");
                if (selec != null) {
                    edtFornecedor.setText(selec.getRazaoSocial());
                }
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Fornecedores: " + e.getMessage());
        }
    }

    @FXML
    void cadastrarAPI(ActionEvent event) {
        Nota nota;
        try {
            List<FornecedorDTO> lstFornecedor = Utils.getFornecedoresAPI();
            if (lstFornecedor != null) {
                nota = new Nota(
                        edtNumNfe.getText(),
                        edtSerieNfe.getText(),
                        edtChAcesso.getText(),
                        new BigDecimal(edtValTot.getText().replace(",", ".")),
                        dtpDtAquisicao.getValue(),
                        lstFornecedor
                                .stream()
                                .filter(nt -> nt.getRazaoSocial().equals(edtFornecedor.getText()))
                                .map(FornecedorDTO::getId)
                                .findFirst()
                                .orElse(null)
                );
                //Serializando para JSON
                String body = mapper.writeValueAsString(Map.of("1", nota));
                System.out.println(body);

                //Mandando a request para a API e pegando a resposta
                RespostaHTTP resp = http.post("http://localhost:8080/nota", body);

                if (resp.getHttpStatus() < 206) { //206 para baixo são retornos válidos
                    Map<Integer, NotaDTO> dto = mapper.readValue(resp.getBody(), new TypeReference<Map<Integer, NotaDTO>>() {
                    });
                    showMessage(Alert.AlertType.INFORMATION, "Nota Cadastrada com Sucesso!");
                    limparCampos();
                    Stage stage = (Stage) btnAdicionar.getScene().getWindow();
                    stage.close();
                } else {
                    throw new Exception("Status" + resp.getBody() + " Erro: " + resp.getBody());
                }
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro no cadastro: " + e.getMessage());
        }

    }

    @FXML
    void limparCampos() {
        edtNumNfe.clear();
        edtSerieNfe.clear();
        edtChAcesso.clear();
        edtValTot.clear();
        edtFornecedor.clear();
        dtpDtAquisicao.setValue(null);
    }

}
