package ideau.controlePatrimonioAPI.repository.implementation;

import ideau.controlePatrimonioAPI.model.DepreciacaoDashboardDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long countItens() {
        String sql = "SELECT COUNT(*) FROM patrimonio";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public long countCategorias() {
        String sql = "SELECT COUNT(*) FROM categorias";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public BigDecimal sumValorTotal() {
        String sql = "SELECT COALESCE(SUM(val_compra), 0) FROM patrimonio";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }

    public List<DepreciacaoDashboardDTO> findDepreciacaoUltimos6Anos() {
        String sql = """
            SELECT 
                EXTRACT(YEAR FROM dt_aquisicao) AS ano,
                SUM(val_compra) AS total_compra,
                SUM(val_compra - (val_compra * (aliq_deprec_mes / 100) * 12)) AS valor_depreciado
            FROM patrimonio
            WHERE EXTRACT(YEAR FROM dt_aquisicao) 
                  BETWEEN EXTRACT(YEAR FROM CURRENT_DATE) - 5 AND EXTRACT(YEAR FROM CURRENT_DATE)
            GROUP BY ano
            ORDER BY ano
        """;

        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> mapDepreciacao(rs));
    }

    private DepreciacaoDashboardDTO mapDepreciacao(ResultSet rs) throws SQLException {
        int ano = rs.getInt("ano");
        BigDecimal totalCompra = rs.getBigDecimal("total_compra");
        BigDecimal valorDepreciado = rs.getBigDecimal("valor_depreciado");
        return new DepreciacaoDashboardDTO(ano, totalCompra, valorDepreciado);
    }
}
