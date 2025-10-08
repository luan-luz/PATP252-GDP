package ideau.ControlePatrimonioDesktop.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Nota {
    private Long id;
    private String chaveNota;
    private String serieNota;
    private String numNota;
    private LocalDate dtEmissao;
    private BigDecimal vlrTotal;
    private Long idFornecedor;

    public Nota() {};

    public Nota(Long id, String numNota, String serieNota, String chaveNota, BigDecimal vlrTotal, LocalDate dtEmissao, Long idFornecedor) {
        this.id = id;
        this.serieNota = serieNota;
        this.numNota = numNota;
        this.chaveNota = chaveNota;
        this.dtEmissao = dtEmissao;
        this.vlrTotal = vlrTotal;
        this.idFornecedor = idFornecedor;
    }
    public Nota(String numNota, String serieNota, String chaveNota, BigDecimal vlrTotal, LocalDate dtEmissao, Long idFornecedor) {
        this.serieNota = serieNota;
        this.numNota = numNota;
        this.chaveNota = chaveNota;
        this.dtEmissao = dtEmissao;
        this.vlrTotal = vlrTotal;
        this.idFornecedor = idFornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerie(String serieNota) {
        this.serieNota = serieNota;
    }

    public String getNumNota() {
        return numNota;
    }

    public void setNumNota(String numNota) {
        this.numNota = numNota;
    }

    public String getChaveNota() {
        return chaveNota;
    }

    public void setChaveNota(String chaveNota) {
        this.chaveNota = chaveNota;
    }

    public LocalDate getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(LocalDate dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public BigDecimal getVlrTotal() {
        return vlrTotal;
    }

    public void setVlrTotal(BigDecimal vlrTotal) {
        this.vlrTotal = vlrTotal;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
