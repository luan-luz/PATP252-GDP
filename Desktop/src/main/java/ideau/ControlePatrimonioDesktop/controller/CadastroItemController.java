package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.*;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.abrirTelaSelecao;

public class CadastroItemController implements Initializable {

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmarEdicao;

    @FXML
    private Button btnSelecCategoria;

    @FXML
    private TextField edtCategoria;

    @FXML
    private TextField edtNomeItem;

    @FXML
    private TableView<ItemDTO> tblItens;

    @FXML
    private TableColumn<ItemDTO, String> colCategoria;

    @FXML
    private TableColumn<ItemDTO, String> colNomeItem;

    private Map<Integer, Item> mapItens;

    private Map<Long, String> mapCategorias;

    public List<ItemDTO> getLstCadastrados() {
        return lstCadastrados;
    }

    public void setLstCadastrados(List<ItemDTO> lstCadastrados) {
        this.lstCadastrados = lstCadastrados;
    }

    private List<ItemDTO> lstCadastrados;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapItens = new HashMap<>();
        mapCategorias = new HashMap<>();
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();

        colNomeItem.setCellValueFactory(new PropertyValueFactory<>("nomeItem"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("nomeCategoria"));
        btnEditar.setVisible(false);
        btnRemover.setVisible(false);
        btnConfirmarEdicao.setVisible(false);
        btnCancelar.setVisible(false);

        btnCadastrar.setOnAction(evt -> {
            try {
                Integer cont = 1;
                Long idCategoria;
                List<ItemDTO> itensTbl = tblItens.getItems();
                for (ItemDTO dto : itensTbl) {
                    idCategoria = mapCategorias.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().equals(dto.getNomeCategoria()))
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .orElse(null);

                    mapItens.put(cont, new Item(dto.getNomeItem(), idCategoria));
                    cont++;
                }
                //Serializando para JSON
                String body = mapper.writeValueAsString(mapItens);
                //Mandando a request para a API e pegando a resposta
                RespostaHTTP resp = http.post("http://localhost:8080/item", body);

                if (resp.getHttpStatus() < 206) { //206 para baixo são retornos válidos
                    Map<Integer, ItemDTO> dto = mapper.readValue(resp.getBody(), new TypeReference<Map<Integer, ItemDTO>>() {});
                    setLstCadastrados(new ArrayList<>(dto.values()));
                    showMessage(Alert.AlertType.INFORMATION, "Itens Cadastrados com Sucesso!");
                    tblItens.getItems().clear();
                    Stage stage = (Stage) btnCadastrar.getScene().getWindow();
                    stage.close();
                } else {
                    throw new Exception("Status" + resp.getBody() + " Erro: " + resp.getBody());
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro no cadastro: " + e.getMessage());
            }
        });


        btnAdicionar.setOnAction(evt -> {
            if (!edtNomeItem.getText().isBlank()) {
                //Adicionando item à lista na tela
                tblItens.getItems().add(new ItemDTO(edtNomeItem.getText(), edtCategoria.getText()));
                limparEdits();
            }
        });

        btnRemover.setOnAction(evt -> {
            tblItens.getItems().remove(tblItens.getSelectionModel().getSelectedItem());
            tblItens.getSelectionModel().clearSelection();
            btnRemover.setVisible(false);
            btnEditar.setVisible(false);
        });

        btnSelecCategoria.setOnAction(evt -> {
//            try {
//                RespostaHTTP resp = http.get("/");
//            } catch (Exception e) {
//                showMessage(Alert.AlertType.ERROR, "Erro de comunicação com o servidor: " + e.getMessage());
//            }

            List<Categoria> categorias = Arrays.asList(
                    new Categoria(1L, "Notebooks"),
                    new Categoria(2L, "Cadeiras"),
                    new Categoria(3L, "Computadores")
            );
            try {
                Categoria categSelec = abrirTelaSelecao(categorias,
                                                         Map.of("ID", "id",
                                                         "Nome Da Categoria", "nome"),
                                                         "Categorias");
                if (categSelec != null) {
                    edtCategoria.setText(categSelec.getNome());
                    mapCategorias.put(categSelec.getId(), categSelec.getNome());
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro ao carregar categorias na tabela: " + e.getMessage());
            }
        });

        tblItens.setRowFactory(tv -> {
            TableRow<ItemDTO> row = new TableRow<>();
            row.setOnMouseClicked(eventClick -> {
                btnRemover.setVisible(true);
                btnEditar.setVisible(true);
            });
            return row;
        });

        btnEditar.setOnAction(evt -> {
            ItemDTO dto = tblItens.getSelectionModel().getSelectedItem();
            if (dto != null) {
                btnAdicionar.setVisible(false);
                btnConfirmarEdicao.setVisible(true);
                btnCancelar.setVisible(true);

                edtNomeItem.setText(dto.getNomeItem());
                edtCategoria.setText(dto.getNomeCategoria());
            }
        });

        btnConfirmarEdicao.setOnAction(evt -> {
            if (!edtNomeItem.getText().isBlank()) {
                tblItens.getSelectionModel().getSelectedItem().setNomeItem(edtNomeItem.getText());
            }
            if (!edtCategoria.getText().isBlank()) {
                tblItens.getSelectionModel().getSelectedItem().setNomeCategoria(edtCategoria.getText());
            };
            tblItens.refresh();
            tblItens.getSelectionModel().clearSelection();
            limparEdits();
            btnConfirmarEdicao.setVisible(false);
            btnCancelar.setVisible(false);
            btnEditar.setVisible(true);
        });

        btnCancelar.setOnAction(evt -> {
            tblItens.getSelectionModel().clearSelection();
            limparEdits();
        });

        btnLimpar.setOnAction(evt -> {
            tblItens.getItems().clear();
        });
    }

    private void limparEdits() {
        edtNomeItem.setText("");
        edtCategoria.setText("");
    }
}
