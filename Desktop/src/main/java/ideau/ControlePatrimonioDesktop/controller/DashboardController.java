package ideau.ControlePatrimonioDesktop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideau.ControlePatrimonioDesktop.model.DashboardDTO;
import ideau.ControlePatrimonioDesktop.model.DepreciacaoDashboardDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DashboardController {

    @FXML private Button btnQtdItens;
    @FXML private Button btnQtdCategorias;
    @FXML private Button btnValorTotal;
    @FXML private Label lblQtdItens;
    @FXML private Label lblQtdCategorias;
    @FXML private Label lblValorTotal;
    @FXML private LineChart<String, Number> lineChart;

    @FXML private CategoryAxis xAxisDepreciacao;

    @FXML private NumberAxis yAxisDepreciacao;


    private static final String API_URL = "http://localhost:8080/api/dashboard";

    @FXML
    public void initialize() {
        carregarDadosDashboard();
    }

    private void carregarDadosDashboard() {
        new Thread(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .GET()
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());

                ObjectMapper mapper = new ObjectMapper();
                DashboardDTO dashboard = mapper.readValue(response.body(), new TypeReference<>() {});

                Platform.runLater(() -> {
                    atualizarTela(dashboard);
                    preencherGrafico(dashboard.getDepreciacoes());
                });

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void atualizarTela(DashboardDTO dashboard) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        btnQtdItens.setText(String.valueOf(dashboard.getTotalItens()));
        lblQtdItens.setText("Itens cadastrados");

        btnQtdCategorias.setText(String.valueOf(dashboard.getTotalCategorias()));
        lblQtdCategorias.setText("Categorias");

        btnValorTotal.setText(nf.format(dashboard.getValorTotal()));
        lblValorTotal.setText("Valor total");
    }


    private void preencherGrafico(List<DepreciacaoDashboardDTO> depreciacoes) {
        lineChart.getData().clear();
        XYChart.Series<String, Number> serieCompra = new XYChart.Series<>();
        serieCompra.setName("Valor de Compra");

        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        xAxis.setGapStartAndEnd(true); // adiciona espaço antes e depois das categorias

        XYChart.Series<String, Number> serieDepreciado = new XYChart.Series<>();
        serieDepreciado.setName("Valor Após Depreciação");
        xAxisDepreciacao.setAutoRanging(true);      // deixa JavaFX distribuir automaticamente
        for (DepreciacaoDashboardDTO dep : depreciacoes) {
            serieCompra.getData().add(new XYChart.Data<>(String.valueOf(dep.getAno()), dep.getTotalCompra()));
            serieDepreciado.getData().add(new XYChart.Data<>(String.valueOf(dep.getAno()), dep.getValorDepreciado()));
        }
        lineChart.getData().addAll(serieCompra, serieDepreciado);
    }
}
