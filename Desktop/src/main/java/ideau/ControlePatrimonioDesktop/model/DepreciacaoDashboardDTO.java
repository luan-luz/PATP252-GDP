package ideau.ControlePatrimonioDesktop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepreciacaoDashboardDTO {

    private String periodo; // opcional para uso interno no gráfico
    private double valor;   // opcional para uso interno no gráfico

    @JsonProperty("ano")
    private int ano;

    @JsonProperty("totalCompra")
    private double totalCompra;

    @JsonProperty("valorDepreciado")
    private double valorDepreciado;

    public DepreciacaoDashboardDTO() {
        // construtor vazio necessário para o Jackson
    }

    public DepreciacaoDashboardDTO(String periodo, double valor) {
        this.periodo = periodo;
        this.valor = valor;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public double getValorDepreciado() {
        return valorDepreciado;
    }

    public void setValorDepreciado(double valorDepreciado) {
        this.valorDepreciado = valorDepreciado;
    }
}
