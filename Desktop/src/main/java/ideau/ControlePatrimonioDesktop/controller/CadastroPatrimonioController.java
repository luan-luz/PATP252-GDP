package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.Item;
import ideau.ControlePatrimonioDesktop.model.ItemDTO;

import ideau.ControlePatrimonioDesktop.model.RespostaHTTP;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.abrirTelaSelecao;

public class CadastroPatrimonioController implements Initializable {
        @FXML
        private Button btnCadastrar;

        @FXML
        private Button btnEditarPatr;

        @FXML
        private Button btnLimpar;

        @FXML
        private Button btnRemoverPatr;

        @FXML
        private Button btnSelecNota;

        @FXML
        private Button btnSelecItem;

        @FXML
        private TextField edtNomeItem;

        @FXML
        private TextField edtNumNota;

        @FXML
        private TextField edtNumPatr;

        @FXML
        private TableView<ItemDTO> tblPatrimonios;

    public void initialize(URL Location, ResourceBundle resources) {
        HTTPTransmit http = new HTTPTransmit();
        ObjectMapper mapper = new ObjectMapper();
        //Item
        btnSelecItem.setOnAction(evt -> {
            try {
                RespostaHTTP resp = http.get("http://localhost:8080/item");

                if (resp.getHttpStatus() < 206) { //Status menores que 206 são retornos válidos
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
                showMessage(Alert.AlertType.ERROR, "Erro ao carregar itens na tabela: " + e.getMessage());
            }
        });
    }
}
