package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.model.*;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import ideau.ControlePatrimonioDesktop.utils.ShowMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.abrirTelaSelecao;

public class EdicaoPatrimonioController implements Initializable {

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnSelecItem;

    @FXML
    private Button btnSelecLocal;

    @FXML
    private Button btnSelecNota;

    @FXML
    private Button btnSelecStatus;

    @FXML
    private DatePicker dtpDtAquisicao;

    @FXML
    private TextField edtAliquota;

    @FXML
    private TextField edtLocal;

    @FXML
    private TextField edtNomeItem;

    @FXML
    private TextField edtNumNota;

    @FXML
    private TextField edtNumPatr;

    @FXML
    private TextField edtStatus;

    @FXML
    private TextField edtValCompra;

    @FXML
    private Label lblNumPatr;

    @FXML
    private RadioButton rdbImobilizado;

    @FXML
    private RadioButton rdbUsoConsumo;

    private PatrimonioDTO patr;

    public void setPatrimonioDTO(PatrimonioDTO patr) {
        this.patr = patr;

        edtNomeItem.setText(patr.getNomeItem());
        edtNumNota.setText(patr.getNumNota()+"/"+patr.getSerieNota());
        edtLocal.setText(patr.getNomeLocal());
        edtStatus.setText(patr.getNomeStatus());
        if (!patr.getNumPatr().isBlank()) {
            edtNumPatr.setText(patr.getNumPatr());
        }
        edtAliquota.setText(patr.getAliqDeprecMes().toString());
        edtValCompra.setText(patr.getValCompra().toString());
        dtpDtAquisicao.setValue(patr.getDtAquisicao());
    }
    public PatrimonioDTO getPatrimonioDTO() { return this.patr;}

    HTTPTransmit http;
    ObjectMapper mapper;
    DateTimeFormatter dateFormatter;
    List<ItemDTO> lstItens;
    List<Local> lstLocal;
    List<Status> lstStatus;
    List<NotaDTO> lstNota;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.http = new HTTPTransmit();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.lstItens = new ArrayList<>();
        this.lstLocal = new ArrayList<>();
        this.lstStatus = new ArrayList<>();
    }

    @FXML
    void confirmarEdicao() {
        BigDecimal valCompra = BigDecimal.ZERO;
        BigDecimal aliquota = BigDecimal.ZERO;
        Map<Integer, Patrimonio> mapPatr = new HashMap<>();
        Long idItem;
        Long idLocal;
        Long idStatus;
        Long idNota = null;
        boolean booPodeProsseguir = true;
        if (edtNomeItem.getText().isBlank()) {
            booPodeProsseguir = false;
            if (!edtNomeItem.getStyleClass().contains("error")) {
                edtNomeItem.getStyleClass().add("error");
            };
        } else {
            edtNomeItem.getStyleClass().remove("error");
        }
        if (edtLocal.getText().isBlank()) {
            booPodeProsseguir = false;
            if (!edtLocal.getStyleClass().contains("error")) {
                edtLocal.getStyleClass().add("error");
            };
        } else {
            edtLocal.getStyleClass().remove("error");
        }
        if (edtStatus.getText().isBlank()) {
            booPodeProsseguir = false;
            if (!edtStatus.getStyleClass().contains("error")) {
                edtStatus.getStyleClass().add("error");
            };
        } else {
            edtStatus.getStyleClass().remove("error");
        }
        if (!booPodeProsseguir) {
            showMessage(Alert.AlertType.ERROR, "Preencha os campos obrigatórios!");
        } else {
            try {
                if (!edtValCompra.getText().isBlank()) {
                    valCompra = new BigDecimal(edtValCompra.getText().replace(",", "."));
                    edtValCompra.getStyleClass().remove("error");
                }
            } catch (NumberFormatException e) {
                booPodeProsseguir = false;
                if (!edtValCompra.getStyleClass().contains("error")) {
                    edtValCompra.getStyleClass().add("error");
                };
                showMessage(Alert.AlertType.ERROR, "O valor: " + edtValCompra.getText() + " não é válido!");
            }
            try {
                if (!edtAliquota.getText().isBlank()) {
                    aliquota = new BigDecimal(edtAliquota.getText().replace(",", "."));
                    edtAliquota.getStyleClass().remove("error");
                }
            } catch (NumberFormatException e) {
                booPodeProsseguir = false;
                if (!edtAliquota.getStyleClass().contains("error")) {
                    edtAliquota.getStyleClass().add("error");
                };
                showMessage(Alert.AlertType.ERROR, "O valor: " + edtAliquota.getText() + " não é válido!");
            }
        }
        try {
            String[] numSerieNota = edtNumNota.getText().split("/");
            PatrimonioDTO dto = new PatrimonioDTO(
                    patr.getId(),
                    edtNomeItem.getText(),
                    edtStatus.getText(),
                    edtLocal.getText(),
                    numSerieNota[1],
                    numSerieNota[2],
                    edtNumPatr.getText(),
                    valCompra,
                    aliquota,
                    dtpDtAquisicao.getValue()
            );


            idNota = lstNota
                    .stream()
                    .filter(entry -> entry.getNumNota().equals(dto.getNumNota()) &&
                            entry.getSerieNota().equals(dto.getSerieNota()))
                    .map(NotaDTO::getId)
                    .findFirst()
                    .orElse(null);

            idItem = lstItens
                    .stream()
                    .filter(entry -> entry.getNomeItem().equals(dto.getNomeItem()))
                    .map(ItemDTO::getId)
                    .findFirst()
                    .orElse(null);
            idLocal = lstLocal
                    .stream()
                    .filter(entry -> entry.getNome().equals(dto.getNomeLocal()))
                    .map(Local::getId)
                    .findFirst()
                    .orElse(null);
            idStatus = lstStatus
                    .stream()
                    .filter(entry -> entry.getNome().equals(dto.getNomeStatus()))
                    .map(Status::getId)
                    .findFirst()
                    .orElse(null);

            Patrimonio patr = new Patrimonio(
                    dto.getId(),
                    idItem,
                    idStatus,
                    idNota,
                    dto.getNumPatr(),
                    dto.getValCompra(),
                    dto.getAliqDeprecMes(),
                    dto.getDtAquisicao()
            );

            //Serializando para JSON
            String body = mapper.writeValueAsString(patr);
            System.out.println(body);
            //Mandando a request para a API e pegando a resposta
            RespostaHTTP resp = http.post("http://localhost:8080/patrimonio", body);

            if (resp.getHttpStatus() < 206) { //206 para baixo são retornos válidos
                PatrimonioDTO dtoRet = mapper.readValue(resp.getBody(), new TypeReference<PatrimonioDTO>() {});
                showMessage(Alert.AlertType.INFORMATION, "Patrimônio Atualizado com Sucesso!");
                limparCampos();
                setPatrimonioDTO(dtoRet);
            } else {
                throw new Exception("Status" + resp.getBody() + " Erro: " + resp.getBody());
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao atualizar patrimônio: " + e.getMessage());
        }
    }
    @FXML
    void abrirTelaSelecLocal(ActionEvent event) {
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/local");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                this.lstLocal = mapper.readValue(resp.getBody(), new TypeReference<List<Local>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome Do Local", "nome");
        try {
            Local objLocal = abrirTelaSelecao(lstLocal, colunas,"Locais");
            if (objLocal != null) {
                edtLocal.setText(objLocal.getNome());
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Locais!" + e.getMessage());
        }
    }

    @FXML
    void abrirTelaSelecStatus(ActionEvent event) {
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/status");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                this.lstStatus = mapper.readValue(resp.getBody(), new TypeReference<List<Status>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome Do Status", "nome");
        try {
            Status objStatus = abrirTelaSelecao(lstStatus, colunas,"Status");
            if (objStatus != null) {
                edtStatus.setText(objStatus.getNome());
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Status!" + e.getMessage());
        }
    }

    @FXML
    void abrirTelaSelecItem() {
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/item");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                this.lstItens = mapper.readValue(resp.getBody(), new TypeReference<List<ItemDTO>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome Do Item", "nomeItem");
        colunas.put("Nome Da Categoria", "nomeCategoria");

        try {
            ItemDTO objItem = abrirTelaSelecao(lstItens, colunas,"Itens");
            if (objItem != null) {
                edtNomeItem.setText(objItem.getNomeItem());
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Itens!" + e.getMessage());
        }
    }

    @FXML
    void abrirTelaSelecNota() {
        try {
            RespostaHTTP resp = http.get("http://localhost:8080/nota");
            if (resp.getHttpStatus() > 206) {
                showMessage(Alert.AlertType.ERROR, "Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
            } else if (resp.getHttpStatus() == null) {
                showMessage(Alert.AlertType.ERROR, "Erro ao contatar o servidor! Entre em contato com o setor de TI.");
            } else {
                this.lstNota = mapper.readValue(resp.getBody(), new TypeReference<List<NotaDTO>>() {});
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro de comunicação com a API: " + e.getMessage());
        }
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nº/Série Nota", "numSerieNota");
        colunas.put("Ch. de Acesso", "chaveNota");
        colunas.put("Val. Tot.", "vlrTotal");
        colunas.put("Dt. Emissão", "dtEmissao");
        colunas.put("Nome Fornecedor", "nomeFornecedor");
        try {
            NotaDTO objNota = abrirTelaSelecao(lstNota, colunas,"Notas Fiscais");
            if (objNota != null) {
                edtNumNota.setText(objNota.getNumSerieNota());
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Notas Fiscais!" + e.getMessage());
        }
    }



    @FXML
    void limparCampos() {
        edtNomeItem.clear();
        edtNumNota.clear();
        edtLocal.clear();
        edtStatus.clear();
        edtAliquota.clear();
        edtValCompra.clear();
        dtpDtAquisicao.setValue(null);
        rdbUsoConsumo.setSelected(true);
        toggleEdtNumPatr();
    }

    @FXML
    void toggleEdtNumPatr() {
        edtNumPatr.clear();
        edtNumPatr.setVisible(rdbImobilizado.isSelected());
        lblNumPatr.setVisible(rdbImobilizado.isSelected());
    }

}
