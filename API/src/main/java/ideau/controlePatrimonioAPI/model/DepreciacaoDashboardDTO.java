package ideau.controlePatrimonioAPI.model;

import java.math.BigDecimal;

public class DepreciacaoDashboardDTO {
    private int ano;
    private BigDecimal totalCompra;
    private BigDecimal valorDepreciado;

    public DepreciacaoDashboardDTO(int ano, BigDecimal totalCompra, BigDecimal valorDepreciado) {
        this.ano = ano;
        this.totalCompra = totalCompra;
        this.valorDepreciado = valorDepreciado;
    }

    public int getAno() { return ano; }
    public BigDecimal getTotalCompra() { return totalCompra; }
    public BigDecimal getValorDepreciado() { return valorDepreciado; }
}
