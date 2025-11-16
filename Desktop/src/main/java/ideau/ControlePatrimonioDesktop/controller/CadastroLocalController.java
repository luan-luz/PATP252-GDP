package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.Local;
import ideau.ControlePatrimonioDesktop.model.PatrimonioDTO;
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

public class CadastroLocalController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private TextField edtNome;

    @FXML
    private Region regEsquerdo;

    private List<Local> lstCadastrados;

    @FXML
    void cadastrarAPI(ActionEvent event) {
        try {
            if (!edtNome.getText().isBlank()) {
                HTTPTransmit http = new HTTPTransmit();
                ObjectMapper mapper = new ObjectMapper();
                Map<Integer, Local> map = new HashMap<>();
                map.put(1, new Local(edtNome.getText()));
                String body = mapper.writeValueAsString(map);
                RespostaHTTP resp = http.post("http://localhost:8080/local", body);
                if (resp.getHttpStatus() < 206) {
                    map.clear();
                    map = mapper.readValue(resp.getBody(), new TypeReference<Map<Integer, Local>>() {});;
                    this.lstCadastrados = map.values().stream().toList();
                    showMessage(Alert.AlertType.INFORMATION,"Local cadastrado com sucesso!");
                    Stage stage = (Stage) btnCadastrar.getScene().getWindow();
                    stage.close();
                } else {
                    throw new Exception("Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
                }
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao cadastrar Local: " + e.getMessage());
        }
    }
    public List<Local> getLstCadastrados() {
        return this.lstCadastrados;
    }
}
