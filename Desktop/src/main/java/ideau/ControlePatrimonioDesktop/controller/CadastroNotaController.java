package ideau.ControlePatrimonioDesktop.controller;

import ideau.ControlePatrimonioDesktop.model.Nota;
import ideau.ControlePatrimonioDesktop.model.NotaDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.*;

public class CadastroNotaController {

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelarEdicao;

    @FXML
    private Button btnConfirmarEdicao;

    @FXML
    private Button btnEditarPatr;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnLimparCampos;

    @FXML
    private Button btnRemoverPatr;

    @FXML
    private Button btnSelecFornec;

    @FXML
    private TableColumn<Nota, BigDecimal> colAliquota;

    @FXML
    private TableColumn<Nota, String> colLocal;

    @FXML
    private TableColumn<Nota, String> colNomeItem;

    @FXML
    private TableColumn<Nota, String> colNumPatr;

    @FXML
    private TableColumn<Nota, String> colStatus;

    @FXML
    private TableColumn<Nota, String> colValCompra;

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

    @FXML
    private TableView<Nota> tblNotas;

    @FXML
    void AbrirTelaSelecFornec(ActionEvent event) {
        try {
            List<NotaDTO> dados = List.of();
//            abrirTelaSelecao(dados,);
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao abrir seleção de Fornecedores: " + e.getMessage());
        }
    }

    @FXML
    void AddNotaTbl(ActionEvent event) {
        NotaDTO dto = new NotaDTO(
                edtNumNfe.getText(),
                edtSerieNfe.getText(),
                edtChAcesso.getText(),
                BigDecimal.valueOf(edtValTot.getText()),
                dtpDtAquisicao.getValue(),
                edtFornecedor.getText()
        );
    }

    @FXML
    void cadastrarAPI(ActionEvent event) {

    }

    @FXML
    void cancelarEdicao(ActionEvent event) {

    }

    @FXML
    void confirmarEdicao(ActionEvent event) {

    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void limparCampos(ActionEvent event) {

    }

    @FXML
    void limparTabela(ActionEvent event) {

    }

    @FXML
    void remover(ActionEvent event) {

    }

}
