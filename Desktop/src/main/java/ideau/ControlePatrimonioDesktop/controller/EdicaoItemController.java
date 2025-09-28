package ideau.ControlePatrimonioDesktop.controller;

import ideau.ControlePatrimonioDesktop.model.Categoria;
import ideau.ControlePatrimonioDesktop.model.ItemDTO;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.abrirTelaSelecao;

public class EdicaoItemController implements Initializable {
    @FXML
    private Button btnConfirmarEdicao;

    @FXML
    private Button btnSelecCategoria;

    @FXML
    private TextField edtCategoria;

    @FXML
    private TextField edtNomeItem;

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    private ItemDTO itemDTO;

    public ItemDTO getEditado() {
        return getItemDTO();
    }

    public void setObjEdicao(ItemDTO itemDTO) {
        edtNomeItem.setText(itemDTO.getNomeItem());
        edtCategoria.setText(itemDTO.getNomeCategoria());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HTTPTransmit http = new HTTPTransmit();

        btnConfirmarEdicao.setOnAction(evt -> {
            ItemDTO itemDTO = new ItemDTO(edtNomeItem.getText(), edtCategoria.getText());
            setItemDTO(itemDTO);

//            http.put();

            edtNomeItem.clear();
            edtCategoria.clear();
            Stage stage = (Stage) btnConfirmarEdicao.getScene().getWindow();
            stage.close();
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
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro ao carregar categorias na tabela: " + e.getMessage());
            }
        });
    }
}
