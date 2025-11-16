package ideau.ControlePatrimonioDesktop.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class DashboardController {

    @FXML
    private Button btnValorTotal;

    @FXML
    private Button btnQtdItens;

    @FXML
    private Button btnQtdSetores;

    @FXML
    private Button btnQtdPatrimonio;

    @FXML
    private Label lblQtdItens;

    @FXML
    private Label lblQtdPatirmonio;

    @FXML
    private Label lblValorTotal;

    @FXML
    private TableView<ItemPrecoMedio> tablePrecoMedio;

    @FXML
    private TableColumn<ItemPrecoMedio, String> colCategoria;

    @FXML
    private TableColumn<ItemPrecoMedio, String> colItem;

    @FXML
    private TableColumn<ItemPrecoMedio, Double> colPrecoMedio;

    @FXML
    private ListView<String> ListCategorias;

    @FXML
    private ListView<String> ListStatus;


    private static final String URL = "jdbc:postgresql://localhost:5432/patp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @FXML
    public void initialize() {
        carregarDashboard();
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colPrecoMedio.setCellValueFactory(new PropertyValueFactory<>("precoMedio"));

        carregarTabelaPrecoMedio();
    }

    private void carregarDashboard() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet rsItens = stmt.executeQuery("SELECT COUNT(*) FROM item");
            if (rsItens.next()) {
                btnQtdItens.setText(String.valueOf(rsItens.getInt(1)));
            }

            ResultSet rsPatr = stmt.executeQuery("SELECT COUNT(*) FROM patrimonio");
            if (rsPatr.next()) {
                btnQtdPatrimonio.setText(String.valueOf(rsPatr.getInt(1)));
            }

            ResultSet rsValor = stmt.executeQuery("SELECT COALESCE(SUM(val_compra), 0) FROM patrimonio");
            if (rsValor.next()) {
                btnValorTotal.setText("R$ " + String.format("%.2f", rsValor.getDouble(1)));
            }

            ResultSet rsListaCat = stmt.executeQuery("SELECT nome FROM categorias ORDER BY nome");
            while (rsListaCat.next()) {
                ListCategorias.getItems().add(rsListaCat.getString("nome"));
            }

            String sqlStatus = """
                    SELECT s.nome AS status, COUNT(p.id_item) AS qtd
                    FROM patrimonio p
                    JOIN status_item s ON s.id = p.id_status
                    GROUP BY s.nome
                    ORDER BY s.nome;
                    """;
            ResultSet rsStatus = stmt.executeQuery(sqlStatus);
            while (rsStatus.next()) {
                String linha = rsStatus.getString("status") + " - " + rsStatus.getInt("qtd") + " itens";
                ListStatus.getItems().add(linha);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar dados do dashboard: " + e.getMessage());
        }

    }
    private void carregarTabelaPrecoMedio() {
        String sql = """
            SELECT c.nome AS categoria,
                   i.nome_item AS item,
                   ROUND(AVG(p.val_compra)::numeric, 2) AS preco_medio
            FROM patrimonio p
            JOIN item i ON p.id_item = i.id
            JOIN categorias c ON i.categoria_id = c.id
            GROUP BY c.nome, i.nome_item
            ORDER BY c.nome, i.nome_item;
        """;

        ObservableList<ItemPrecoMedio> dados = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String categoria = rs.getString("categoria");
                String item = rs.getString("item");
                double precoMedio = rs.getDouble("preco_medio");

                dados.add(new ItemPrecoMedio(categoria, item, precoMedio));
            }

            tablePrecoMedio.setItems(dados);

        } catch (SQLException e) {
            System.err.println("Erro ao carregar tabela de preço médio: " + e.getMessage());
        }
    }

    // Classe modelo para TableView
    public static class ItemPrecoMedio {
        private final String categoria;
        private final String item;
        private final Double precoMedio;

        public ItemPrecoMedio(String categoria, String item, Double precoMedio) {
            this.categoria = categoria;
            this.item = item;
            this.precoMedio = precoMedio;
        }

        public String getCategoria() { return categoria; }
        public String getItem() { return item; }
        public Double getPrecoMedio() { return precoMedio; }
    }
}

