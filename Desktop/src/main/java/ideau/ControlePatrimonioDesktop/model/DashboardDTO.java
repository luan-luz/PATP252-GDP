package ideau.ControlePatrimonioDesktop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DashboardDTO {

    @JsonProperty("qtdItens")
    private int totalItens;

    @JsonProperty("qtdCategorias")
    private int totalCategorias;

    @JsonProperty("valorTotal")
    private double valorTotal;

    @JsonProperty("depreciacoes")
    private List<DepreciacaoDashboardDTO> depreciacoes;

    public DashboardDTO() {}

    public DashboardDTO(int totalItens, int totalCategorias, double valorTotal, List<DepreciacaoDashboardDTO> depreciacoes) {
        this.totalItens = totalItens;
        this.totalCategorias = totalCategorias;
        this.valorTotal = valorTotal;
        this.depreciacoes = depreciacoes;
    }

    public int getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(int totalItens) {
        this.totalItens = totalItens;
    }

    public int getTotalCategorias() {
        return totalCategorias;
    }

    public void setTotalCategorias(int totalCategorias) {
        this.totalCategorias = totalCategorias;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<DepreciacaoDashboardDTO> getDepreciacoes() {
        return depreciacoes;
    }

    public void setDepreciacoes(List<DepreciacaoDashboardDTO> depreciacoes) {
        this.depreciacoes = depreciacoes;
    }
}
