package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ideau.ControlePatrimonioDesktop.model.*;

import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.*;

public class CadastroPatrimonioController implements Initializable {
    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditarPatr;

    @FXML
    private Button btnCancelarEdicao;

    @FXML
    private Button btnConfirmarEdicao;

    @FXML
    private Button btnLimpar;

    @FXML private Button btnLimparCampos;

    @FXML
    private Button btnRemoverPatr;

    @FXML
    private Button btnSelecItem;

    @FXML
    private Button btnSelecNota;

    @FXML
    private Button btnSelecLocal;

    @FXML
    private Button btnSelecStatus;

    @FXML
    private TableColumn<PatrimonioDTO, String> colAliquota;

    @FXML
    private TableColumn<PatrimonioDTO, LocalDate> colDataAquisicao;

    @FXML
    private TableColumn<PatrimonioDTO, String> colNomeItem;

    @FXML
    private TableColumn<PatrimonioDTO, String> colNumPatr;

    @FXML
    private TableColumn<PatrimonioDTO, String> colLocal;

    @FXML
    private TableColumn<PatrimonioDTO, String> colStatus;

    @FXML
    private TableColumn<PatrimonioDTO, String> colValCompra;

    @FXML
    private DatePicker dtpDtAquisicao;

    @FXML
    private TextField edtAliquota;

    @FXML
    private TextField edtNomeItem;

    @FXML
    private TextField edtNumNota;

    @FXML
    private TextField edtNumPatr;

    @FXML
    private TextField edtLocal;

    @FXML
    private TextField edtStatus;

    @FXML
    private TextField edtValCompra;

    @FXML
    private Spinner<Integer> spnQuantidade;

    @FXML
    private TableView<PatrimonioDTO> tblPatrimonios;

    @FXML
    private RadioButton rdbImobilizado;

    @FXML
    private RadioButton rdbUsoConsumo;

    @FXML
    private Label lblNumPatr;

    @FXML
    private Label lblQuantidade;

    @FXML
    private Region regEsquerdo;

    HTTPTransmit http;
    ObjectMapper mapper;
    DateTimeFormatter dateFormatter;

    List<ItemDTO> lstItens;
    List<Local> lstLocal;
    List<Status> lstStatus;

    public void initialize(URL Location, ResourceBundle resources) {
        this.http = new HTTPTransmit();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lstItens = new ArrayList<>();
        lstLocal = new ArrayList<>();
        lstStatus = new ArrayList<>();
        //Setando ValueFactorys nas colunas da tabela
        colNomeItem.setCellValueFactory(new PropertyValueFactory<>("nomeItem"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("nomeStatus"));
        colLocal.setCellValueFactory(new PropertyValueFactory<>("nomeLocal"));
        colNumPatr.setCellValueFactory(new PropertyValueFactory<>("numPatr"));
        colValCompra.setCellValueFactory(new PropertyValueFactory<>("valCompra"));
        colAliquota.setCellValueFactory(new PropertyValueFactory<>("aliqDeprecMes"));
        colDataAquisicao.setCellValueFactory(new PropertyValueFactory<>("dtAquisicao"));

        //Row Factory para deixar os itens da tabela clicáveis
        tblPatrimonios.setRowFactory(tv -> {
            TableRow<PatrimonioDTO> row = new TableRow<>();
            row.setOnMouseClicked(eventClick -> {
                btnRemoverPatr.setVisible(true);
                btnEditarPatr.setVisible(true);
            });
            return row;
        });

        //Deixando no formato dd/MM/yyyy
        colDataAquisicao.setCellFactory(column -> new TableCell<PatrimonioDTO, LocalDate>() {
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

        //Campos específicos de valor e alíquota
        edtValCompra.setTextFormatter(new TextFormatter<>(retFiltroCampoValor()));
        edtAliquota.setTextFormatter(new TextFormatter<>(retFiltroCampoPorcent("5")));

        //Ajustando Spinner
        spnQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,1));

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
        //Adicionando radio buttons a um grupo
        ToggleGroup grupo = new ToggleGroup();
        rdbImobilizado.setToggleGroup(grupo);
        rdbUsoConsumo.setToggleGroup(grupo);
        rdbUsoConsumo.setSelected(true);
        edtNumPatr.setVisible(false);
        lblNumPatr.setVisible(false);

        //Só visível se algum item selecionado
        btnEditarPatr.setVisible(false);
        btnConfirmarEdicao.setVisible(false);
        btnCancelarEdicao.setVisible(false);
    }
    @FXML
    void cadastrarPatrimoniosAPI() {
        Map<Integer, Patrimonio> mapPatr = new HashMap<>();
        try {
            Integer cont = 1;
            Long idItem;
            Long idLocal;
            Long idStatus;
            Long idNota;
            List<PatrimonioDTO> patrTbl = tblPatrimonios.getItems();
            for (PatrimonioDTO dto : patrTbl) {
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
                idNota = 1L;//ajustar
                mapPatr.put(cont, new Patrimonio(idItem, idStatus, idLocal, idNota, dto.getNumPatr(), dto.getValCompra(),
                        dto.getAliqDeprecMes(), dto.getDtAquisicao()));
                cont++;
            }
            //Serializando para JSON
            String body = mapper.writeValueAsString(mapPatr);
            //Mandando a request para a API e pegando a resposta
            RespostaHTTP resp = http.post("http://localhost:8080/patrimonio", body);

            if (resp.getHttpStatus() < 206) { //206 para baixo são retornos válidos
                Map<Integer, Patrimonio> dto = mapper.readValue(resp.getBody(), new TypeReference<Map<Integer, Patrimonio>>() {});
                showMessage(Alert.AlertType.INFORMATION, "Itens Cadastrados com Sucesso!");
                tblPatrimonios.getItems().clear();
                mapPatr.clear();
                lstItens.clear();
                lstLocal.clear();
                lstStatus.clear();
            } else {
                throw new Exception("Status" + resp.getBody() + " Erro: " + resp.getBody());
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro no cadastro: " + e.getMessage());
        }
    }
    @FXML
    public void editarPatr() {
        PatrimonioDTO dto = tblPatrimonios.getSelectionModel().getSelectedItem();
        if (dto != null) {
            btnAdicionar.setVisible(false);
            btnLimparCampos.setVisible(false);
            spnQuantidade.setVisible(false);
            lblQuantidade.setVisible(false);
            regEsquerdo.setPrefWidth(129);
            btnConfirmarEdicao.setVisible(true);
            btnCancelarEdicao.setVisible(true);

            edtNomeItem.setText(dto.getNomeItem());
            edtLocal.setText(dto.getNomeLocal());
            edtStatus.setText(dto.getNomeStatus());
            dtpDtAquisicao.setValue(dto.getDtAquisicao());
            edtValCompra.setText(dto.getValCompra().toString());
            edtAliquota.setText(dto.getAliqDeprecMes().toString());

            if (!dto.getNumPatr().isBlank()) {
                rdbImobilizado.setSelected(true);
                edtNumPatr.setText(dto.getNumPatr());
            } else {
                rdbUsoConsumo.setSelected(true);
            }
            toggleEdtNumPatr();
        }
    }

    @FXML
    public void confirmarEdicao() {
        boolean booPodeProsseguir = true;
        edtNomeItem.getStyleClass().remove("error");
        edtLocal.getStyleClass().remove("error");
        edtStatus.getStyleClass().remove("error");

        if (!edtNomeItem.getText().isBlank()) {
            tblPatrimonios.getSelectionModel().getSelectedItem().setNomeItem(edtNomeItem.getText());
        } else {
            if (!edtNomeItem.getStyleClass().contains("error")) {
                edtNomeItem.getStyleClass().add("error");
            };
            booPodeProsseguir = false;
        }
        if (!edtLocal.getText().isBlank()) {
            tblPatrimonios.getSelectionModel().getSelectedItem().setNomeStatus(edtLocal.getText());
        } else {
            if (!edtLocal.getStyleClass().contains("error")) {
                edtLocal.getStyleClass().add("error");
            };
            booPodeProsseguir = false;

        }
        if (!edtStatus.getText().isBlank()) {
            tblPatrimonios.getSelectionModel().getSelectedItem().setNomeLocal(edtStatus.getText());
        } else {
            if (!edtStatus.getStyleClass().contains("error")) {
                edtStatus.getStyleClass().add("error");
            };
            booPodeProsseguir = false;
        }
        if (!booPodeProsseguir) {
            showMessage(Alert.AlertType.ERROR, "Preencha os campos obrigatórios!");
        } else {
            tblPatrimonios.getSelectionModel().getSelectedItem().setNumPatr(edtNumPatr.getText());
            tblPatrimonios.getSelectionModel().getSelectedItem().setDtAquisicao(dtpDtAquisicao.getValue());
            tblPatrimonios.getSelectionModel().getSelectedItem().setAliqDeprecMes(new BigDecimal(edtAliquota.getText()));
            tblPatrimonios.getSelectionModel().getSelectedItem().setValCompra(new BigDecimal(edtValCompra.getText()));

            tblPatrimonios.refresh();
            tblPatrimonios.getSelectionModel().clearSelection();
            //chama o método cancelar edição pois os setVisible são os mesmos
            cancelarEdicao();
        }
    }
    @FXML
    public void cancelarEdicao() {
        btnAdicionar.setVisible(true);
        btnLimparCampos.setVisible(true);
        spnQuantidade.setVisible(true);
        lblQuantidade.setVisible(true);
        regEsquerdo.setPrefWidth(211);
        btnConfirmarEdicao.setVisible(false);
        btnCancelarEdicao.setVisible(false);
        limparCampos();
    }
    @FXML
    void abrirTelaSelecItem() {
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
                    lstItens.add(selecionado);
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
    }
    @FXML
    void AbrirTelaSelecLocal(ActionEvent event) {
        List<Local> lstLocal = Arrays.asList(
                new Local(1L, "TI"),
                new Local(1L, "Laboratório"),
                new Local(1L, "Atendimento")
        );
        Map<String, String> colunas = new LinkedHashMap<>();
        colunas.put("ID", "id");
        colunas.put("Nome Do Local", "nome");
        try {
            Local objLocal = abrirTelaSelecao(lstLocal, colunas,"Locales");
            if (objLocal != null) {
                edtLocal.setText(objLocal.getNome());
                lstLocal.add(objLocal);
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Locais!");
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
            if (objStatus != null) {
                edtStatus.setText(objStatus.getNome());
                lstStatus.add(objStatus);
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Status!");
        }
    }
    @FXML
    void abrirTelaSelecNota(ActionEvent event) {
        edtNumNota.setText("1");
    }


    @FXML
    void AddPatrTbl(ActionEvent event) {
        BigDecimal valCompra = BigDecimal.ZERO;
        BigDecimal aliquota = BigDecimal.ZERO;

        Integer qtd = spnQuantidade.getValue();

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

        if (booPodeProsseguir) {
            for (int i = 1; i <= qtd; i++) {
                tblPatrimonios.getItems().add(new PatrimonioDTO(edtNomeItem.getText(),
                                                                edtStatus.getText(),
                                                                edtLocal.getText(),
                                                                edtNumNota.getText(),
                                                                "1", //ajustar
                                                                edtNumPatr.getText(),
                                                                valCompra,
                                                                aliquota,
                                                                dtpDtAquisicao.getValue()
                ));
            }
        }
        limparCampos();
    }

    @FXML
    void toggleEdtNumPatr() {
        edtNumPatr.clear();
        boolean booVisivel = rdbImobilizado.isSelected();
        edtNumPatr.setVisible(booVisivel);
        lblNumPatr.setVisible(booVisivel);
    }

    @FXML
    void removerPatrimonio() {
        tblPatrimonios.getItems().remove(tblPatrimonios.getSelectionModel().getSelectedItem());
        tblPatrimonios.getSelectionModel().clearSelection();
        btnRemoverPatr.setVisible(false);
        btnEditarPatr.setVisible(false);
    }
    @FXML
    void limparTabelaPatrimonios() {
        tblPatrimonios.getItems().clear();
    }
    @FXML
    void limparCampos() {
        rdbUsoConsumo.setSelected(true);
        toggleEdtNumPatr();
        dtpDtAquisicao.setValue(null);
        edtValCompra.clear();
        edtAliquota.clear();
        edtNomeItem.clear();
        edtLocal.clear();
        edtStatus.clear();
        spnQuantidade.getValueFactory().setValue(1);
    }
}
