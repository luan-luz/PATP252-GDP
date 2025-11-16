package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.RespostaHTTP;
import ideau.ControlePatrimonioDesktop.model.Status;
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

public class CadastroStatusController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private TextField edtNome;

    @FXML
    private Region regEsquerdo;

    List<Status> lstCadastrados;

    @FXML
    void cadastrarAPI() {
        try {
            if (!edtNome.getText().isBlank()) {
                HTTPTransmit http = new HTTPTransmit();
                ObjectMapper mapper = new ObjectMapper();
                Map<Integer, Status> map = new HashMap<>();
                map.put(1, new Status(edtNome.getText()));
                String body = mapper.writeValueAsString(map);
                RespostaHTTP resp = http.post("http://localhost:8080/status", body);
                if (resp.getHttpStatus() < 206) {
                    map.clear();
                    map = mapper.readValue(resp.getBody(), new TypeReference<Map<Integer, Status>>() {});
                    this.lstCadastrados = map.values().stream().toList();
                    showMessage(Alert.AlertType.INFORMATION,"Status cadastrado com sucesso!");
                    Stage stage = (Stage) btnCadastrar.getScene().getWindow();
                    stage.close();
                } else {
                    throw new Exception("Status: " + resp.getHttpStatus() + "Erro: " + resp.getBody());
                }
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Erro ao cadastrar Status: " + e.getMessage());
        }
    }
    public List<Status> getLstCadastrados() {
        return this.lstCadastrados;
    }
}
