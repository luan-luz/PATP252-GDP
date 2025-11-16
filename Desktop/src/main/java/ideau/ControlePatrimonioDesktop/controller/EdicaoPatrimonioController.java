package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.model.*;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import ideau.ControlePatrimonioDesktop.utils.ShowMessage;
import ideau.ControlePatrimonioDesktop.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.*;

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

        //Adicionando radio buttons a um grupo
        ToggleGroup grupo = new ToggleGroup();
        rdbImobilizado.setToggleGroup(grupo);
        rdbUsoConsumo.setToggleGroup(grupo);
        rdbUsoConsumo.setSelected(true);
        edtNumPatr.setVisible(false);
        lblNumPatr.setVisible(false);

        //Ajustando formatação do datepicker e setando o dia de hoje como default apenas para não ficar em branco
        dtpDtAquisicao.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty())
                        ? LocalDate.parse(string, dateFormatter)
                        : null;
            }
        });

        //Campos específicos de valor e alíquota
        edtValCompra.setTextFormatter(new TextFormatter<>(retFiltroCampoValor()));
        edtAliquota.setTextFormatter(new TextFormatter<>(retFiltroCampoPorcent("5")));
    }

    @FXML
    void confirmarEdicao() {
        System.out.println(this.patr.getId());
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

            this.lstNota = Utils.getNotasAPI();
            idNota = lstNota
                    .stream()
                    .filter(entry -> entry.getNumNota().equals(numSerieNota[0]) &&
                            entry.getSerieNota().equals(numSerieNota[1]))
                    .map(NotaDTO::getId)
                    .findFirst()
                    .orElse(null);
            this.lstItens = Utils.getItensAPI();
            idItem = lstItens
                    .stream()
                    .filter(entry -> entry.getNomeItem().equals(edtNomeItem.getText()))
                    .map(ItemDTO::getId)
                    .findFirst()
                    .orElse(null);
            this.lstLocal = Utils.getLocaisAPI();
            idLocal = lstLocal
                    .stream()
                    .filter(entry -> entry.getNome().equals(edtLocal.getText()))
                    .map(Local::getId)
                    .findFirst()
                    .orElse(null);
            this.lstStatus = Utils.getStatusAPI();
            System.out.println(edtStatus.getText());
            lstStatus.forEach(status -> {
                System.out.println(status.getNome());
            });
            idStatus = this.lstStatus
                    .stream()
                    .filter(entry -> entry.getNome().equalsIgnoreCase(edtStatus.getText()))
                    .map(Status::getId)
                    .findFirst()
                    .orElse(null);
            System.out.println(lstStatus.toString());
            System.out.println(edtStatus.getText());
            Patrimonio patrimonio = new Patrimonio(
                    this.patr.getId(),
                    idItem,
                    idStatus,
                    idLocal,
                    idNota,
                    edtNumPatr.getText(),
                    valCompra,
                    aliquota,
                    dtpDtAquisicao.getValue()
            );
            System.out.println(patrimonio.getId());
            //Serializando para JSON
            String body = mapper.writeValueAsString(patrimonio);
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
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome", "nome");

        this.lstLocal = Utils.getLocaisAPI();

        if (this.lstLocal != null) {
            try {
                Local objLocal = abrirTelaSelecao(lstLocal, colunas,"Locais");
                if (objLocal != null) {
                    edtLocal.setText(objLocal.getNome());
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Locais!" + e.getMessage());
            }
        }

    }

    @FXML
    void abrirTelaSelecStatus(ActionEvent event) {
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome", "nome");
        this.lstStatus = Utils.getStatusAPI();
        if (this.lstStatus != null) {
            try {
                Status objStatus = abrirTelaSelecao(lstStatus, colunas,"Status");
                if (objStatus != null) {
                    edtStatus.setText(objStatus.getNome());
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Status!" + e.getMessage());
            }
        }
    }

    @FXML
    void abrirTelaSelecItem() {
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome Do Item", "nomeItem");
        colunas.put("Nome Da Categoria", "nomeCategoria");
        this.lstItens = Utils.getItensAPI();
        if (this.lstItens != null) {
            try {
                ItemDTO objItem = abrirTelaSelecao(lstItens, colunas,"Itens");
                if (objItem != null) {
                    edtNomeItem.setText(objItem.getNomeItem());
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Itens!" + e.getMessage());
            }
        }
    }

    @FXML
    void abrirTelaSelecNota() {
        this.lstNota = Utils.getNotasAPI();
        if (this.lstNota != null) {
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
