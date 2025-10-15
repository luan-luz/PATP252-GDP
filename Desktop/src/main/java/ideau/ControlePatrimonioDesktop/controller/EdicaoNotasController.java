package ideau.ControlePatrimonioDesktop.controller;

import ideau.ControlePatrimonioDesktop.model.*;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;
import static ideau.ControlePatrimonioDesktop.utils.Utils.abrirTelaSelecao;

public class EdicaoNotasController implements Initializable {
    @FXML
    private Button btnConfirmarEdicao;

    @FXML
    private Button btnLimparCampos;

    @FXML
    private Button btnSelecFornec;

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
    private Region regEsquerdo;

    private NotaDTO notaDTO;
    private HTTPTransmit http;

    public NotaDTO getNotaDTO() {
        return notaDTO;
    }

    public void setNotaDTO(NotaDTO notaDTO) {
        this.notaDTO = notaDTO;
    }


    public NotaDTO getEditado() {
        return getNotaDTO();
    }

    @FXML
    void AbrirTelaSelecFornec() {
        try {
//            RespostaHTTP resp = http.get("http://localhost:8080/fornecedor");
//            if (resp == null) throw new RuntimeException("Não foi possível se conectar com o servidor.");
//
//            if (resp.getHttpStatus() > 206) {
//                throw new RuntimeException("Status: " + resp.getHttpStatus() + "Mensagem: " + resp.getBody());
//            }

            List<FornecedorDTO> fornecedores = List.of(new FornecedorDTO(1L,"Papelita Ind e Com de Papeis LTDA", "Papelita", "12345678901234", "2316555", "Rua General Osório", "5620", "Sala 1"));
            FornecedorDTO fornecSelec = abrirTelaSelecao(fornecedores,
                    Map.of("ID", "id",
                            "Razão Social", "razaoSocial"),
                    "Fornecedores");
            if (fornecSelec != null) {
                edtFornecedor.setText(fornecSelec.getRazaoSocial());
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao carregar categorias na tabela: " + e.getMessage());
        }
    }

    @FXML
    void confirmarEdicao() {
        setNotaDTO(new NotaDTO(
                edtNumNfe.getText(),
                edtSerieNfe.getText(),
                edtChAcesso.getText(),
                new BigDecimal(edtValTot.getText()),
                dtpDtAquisicao.getValue(),
                edtFornecedor.getText()));

//            http.put();

        limparCampos();
        Stage stage = (Stage) btnConfirmarEdicao.getScene().getWindow();
        stage.close();
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



    public void setObjEdicao(NotaDTO notaDTO) {
        edtNumNfe.setText(notaDTO.getNumNota());
        edtSerieNfe.setText(notaDTO.getSerieNota());
        edtChAcesso.setText(notaDTO.getChaveNota());
        edtValTot.setText(notaDTO.getVlrTotal().toString());
        edtFornecedor.setText(notaDTO.getNomeFornecedor());
        dtpDtAquisicao.setValue(notaDTO.getDtEmissao());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.http = new HTTPTransmit();
    }
}
