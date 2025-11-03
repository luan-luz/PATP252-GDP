package ideau.ControlePatrimonioDesktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class RelatorioController {

    @FXML
    private DatePicker dateCadastroDe;
    @FXML
    private DatePicker dateCadastroAte;
    @FXML
    private ComboBox<String> comboSetor;
    @FXML
    private ComboBox<String> comboCategoria;
    @FXML
    private ComboBox<String> comboSituacao;
    @FXML
    private ComboBox<String> comboImprimirValores;
    @FXML
    private ComboBox<String> comboExportar;
    @FXML
    private Button btnGerar;

    @FXML
    private void initialize() {
        // Popular ComboBoxes com valores padrão
        comboSetor.getItems().addAll("Todos", "Setor 1", "Setor 2");
        comboCategoria.getItems().addAll("Todas", "Categoria 1", "Categoria 2");
        comboSituacao.getItems().addAll("Todas", "Ativo", "Inativo");
        comboImprimirValores.getItems().addAll("Sim", "Não");
        comboExportar.getItems().addAll("Excel", "PDF");
        comboSetor.setValue("Todos");
        comboCategoria.setValue("Todas");
        comboSituacao.setValue("Todas");
        comboImprimirValores.setValue("Sim");
        comboExportar.setValue("Excel");
        btnGerar.setOnAction(event -> gerarRelatorio());
    }

    @FXML
    private void gerarRelatorio() {
        try {
            LocalDate de = dateCadastroDe.getValue();
            LocalDate ate = dateCadastroAte.getValue();
            if (de == null || ate == null) {
                showAlert("Erro", "As datas não podem estar vazias.");
                return;
            }
            if (de.isAfter(ate)) {
                showAlert("Erro", "A data inicial não pode ser maior que a final.");
                return;
            }
            String setor = comboSetor.getValue();
            String categoria = comboCategoria.getValue();
            String situacao = comboSituacao.getValue();
            String imprimirValores = comboImprimirValores.getValue();
            String exportar = comboExportar.getValue();

            setor = "Todos".equalsIgnoreCase(setor) ? null : setor;
            categoria = "Todas".equalsIgnoreCase(categoria) ? null : categoria;
            situacao = "Todas".equalsIgnoreCase(situacao) ? null : situacao;

            String url = String.format(
                    "http://localhost:8080/api/relatorios?de=%s&ate=%s&setor=%s&categoria=%s&situacao=%s&imprimirValores=%s&exportar=%s",
                    de, ate,
                    setor != null ? setor : "",
                    categoria != null ? categoria : "",
                    situacao != null ? situacao : "",
                    imprimirValores,
                    exportar
            );


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                FileChooser fileChooser = new FileChooser();
                String extensaoArquivo;
                if ("Excel".equalsIgnoreCase(exportar)) {
                    extensaoArquivo = "xlsx";
                } else if ("PDF".equalsIgnoreCase(exportar)) {
                    extensaoArquivo = "pdf";
                } else {
                    extensaoArquivo = "bin"; // fallback
                }

                fileChooser.setInitialFileName("relatorio." + extensaoArquivo);
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter(exportar, "*." + extensaoArquivo)
                );

                var arquivo = fileChooser.showSaveDialog(btnGerar.getScene().getWindow());
                if (arquivo != null) {
                    try (FileOutputStream fos = new FileOutputStream(arquivo)) {
                        fos.write(response.body());
                        fos.flush();
                        showAlert("Sucesso", "Relatório salvo em: " + arquivo.getAbsolutePath());
                    }
                }
            } else {
                showAlert("Erro", "Falha ao gerar relatório. Código HTTP: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            showAlert("Erro", "Falha ao gerar relatório: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Erro", "Erro inesperado: " + e.getMessage());
        }
    }

    private void showAlert(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
