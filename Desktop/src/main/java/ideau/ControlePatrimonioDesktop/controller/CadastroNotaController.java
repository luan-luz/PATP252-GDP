package ideau.ControlePatrimonioDesktop.controller;

import ideau.ControlePatrimonioDesktop.model.Nota;
import ideau.ControlePatrimonioDesktop.model.NotaDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.*;

public class CadastroNotaController implements Initializable {

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

    @FXML
    private TableView<NotaDTO> tblNotas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNumNfe.setCellValueFactory(new PropertyValueFactory<>("numNota"));
        colSerieNfe.setCellValueFactory(new PropertyValueFactory<>("serieNota"));
        colChAcessoNfe.setCellValueFactory(new PropertyValueFactory<>("chaveNota"));
        colDtEmissao.setCellValueFactory(new PropertyValueFactory<>("dtEmissao"));
        colValTot.setCellValueFactory(new PropertyValueFactory<>("vltTotal"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
    }

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
    void AddNotaTbl() {
        NotaDTO dto = new NotaDTO(
                edtNumNfe.getText(),
                edtSerieNfe.getText(),
                edtChAcesso.getText(),
                new BigDecimal(edtValTot.getText()),
                dtpDtAquisicao.getValue(),
                edtFornecedor.getText()
        );
        tblNotas.getItems().add(dto);
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
    void limparCampos() {
        edtNumNfe.clear();
        edtSerieNfe.clear();
        edtChAcesso.clear();
        edtValTot.clear();
        edtFornecedor.clear();
    }

    @FXML
    void limparTabela(ActionEvent event) {

    }

    @FXML
    void remover(ActionEvent event) {

    }

}
