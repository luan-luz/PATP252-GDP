package ideau.controlePatrimonioAPI.model;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {
    private long qtdItens;
    private long qtdCategorias;
    private BigDecimal valorTotal;
    private List<DepreciacaoDashboardDTO> depreciacoes;

    public DashboardDTO(long qtdItens, long qtdCategorias, BigDecimal valorTotal, List<DepreciacaoDashboardDTO> depreciacoes) {
        this.qtdItens = qtdItens;
        this.qtdCategorias = qtdCategorias;
        this.valorTotal = valorTotal;
        this.depreciacoes = depreciacoes;
    }

    public long getQtdItens() { return qtdItens; }
    public long getQtdCategorias() { return qtdCategorias; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public List<DepreciacaoDashboardDTO> getDepreciacoes() { return depreciacoes; }
}
