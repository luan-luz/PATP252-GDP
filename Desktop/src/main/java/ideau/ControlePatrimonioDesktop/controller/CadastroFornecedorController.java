package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.Fornecedor;
import ideau.ControlePatrimonioDesktop.model.FornecedorDTO;
import ideau.ControlePatrimonioDesktop.model.RespostaHTTP;
import ideau.ControlePatrimonioDesktop.utils.HTTPTransmit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ideau.ControlePatrimonioDesktop.utils.ShowMessage.showMessage;

public class CadastroFornecedorController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnLimparCampos;

    @FXML
    private TextField edtCNPJ;

    @FXML
    private TextField edtComplemento;

    @FXML
    private TextField edtFantasia;

    @FXML
    private TextField edtIe;

    @FXML
    private TextField edtLogradouro;

    @FXML
    private TextField edtNumero;

    @FXML
    private TextField edtRazao;

    @FXML
    private Region regEsquerdo;

    List<FornecedorDTO> lstCadastrados;

    @FXML
    void cadastrarAPI() {
        if (validaFornecedor()) {
            FornecedorDTO fornec = new FornecedorDTO(
                    edtRazao.getText(),
                    edtFantasia.getText(),
                    edtCNPJ.getText(),
                    edtIe.getText(),
                    edtLogradouro.getText(),
                    edtNumero.getText(),
                    edtComplemento.getText()
            );
            try {
                HTTPTransmit http = new HTTPTransmit();
                ObjectMapper mapper = new ObjectMapper();

                Map<Integer, FornecedorDTO> map = new HashMap<>();
                map.put(1, fornec);

                String body = mapper.writeValueAsString(map);

                RespostaHTTP resp = http.post("http://localhost:8080/fornecedor", body);
                if (resp.getHttpStatus() < 206) {
                    map.clear();
                    map = mapper.readValue(resp.getBody(), new TypeReference<Map<Integer, FornecedorDTO>>() {});
                    this.lstCadastrados = map.values().stream().toList();
                    showMessage(Alert.AlertType.INFORMATION, "Fornecedor cadastrado com sucesso!");
                    limparCampos();
                    Stage stage = (Stage) edtRazao.getScene().getWindow();
                    stage.close();
                } else {
                    throw new Exception("Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Erro ao cadastrar Fornecedor: ");
            }
        }
    }

    public List<FornecedorDTO> getLstCadastrados() {
        return this.lstCadastrados;
    }

    boolean validaFornecedor() {
        boolean booPodeProsseguir = true;
        if (edtRazao.getText().isBlank()) {
            booPodeProsseguir = false;
            System.out.println(edtRazao.getStyleClass().toString());
            if (!edtRazao.getStyleClass().contains("error")) {
                edtRazao.getStyleClass().add("error");
            };
        } else {
            edtRazao.getStyleClass().remove("error");
        }
        if (edtCNPJ.getText().isBlank()) {
            booPodeProsseguir = false;
            System.out.println(edtCNPJ.getStyleClass().toString());
            if (!edtCNPJ.getStyleClass().contains("error")) {
                edtCNPJ.getStyleClass().add("error");
            };
        } else {
            edtCNPJ.getStyleClass().remove("error");
        }
        if (edtIe.getText().isBlank()) {
            booPodeProsseguir = false;
            System.out.println(edtIe.getStyleClass().toString());
            if (!edtIe.getStyleClass().contains("error")) {
                edtIe.getStyleClass().add("error");
            };
        } else {
            edtIe.getStyleClass().remove("error");
        }
        if (!booPodeProsseguir) {
            showMessage(Alert.AlertType.ERROR, "Preencha os campos obrigatórios!");
        }
        return booPodeProsseguir;
    }

    @FXML
    void limparCampos() {
        edtRazao.clear();
        edtFantasia.clear();
        edtCNPJ.clear();
        edtIe.clear();
        edtLogradouro.clear();
        edtNumero.clear();
        edtComplemento.clear();
    }
}
