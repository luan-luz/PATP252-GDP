package ideau.controlePatrimonioAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NotaDTO {
    private Long id;
    private String chaveNota;
    private String serieNota;
    private String numNota;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dtEmissao;
    private BigDecimal vlrTotal;
    private String nomeFornecedor;

    public NotaDTO() {};

    public NotaDTO(Long id) {this.id = id;};

    public NotaDTO(Long id, String numNota, String serieNota, String chaveNota, BigDecimal vlrTotal, LocalDate dtEmissao, String nomeFornecedor) {
        this.id = id;
        this.serieNota = serieNota;
        this.numNota = numNota;
        this.chaveNota = chaveNota;
        this.dtEmissao = dtEmissao;
        this.vlrTotal = vlrTotal;
        this.nomeFornecedor = nomeFornecedor;

    }
    public NotaDTO(String numNota, String serieNota, String chaveNota, BigDecimal vlrTotal, LocalDate dtEmissao, String nomeFornecedor) {
        this.serieNota = serieNota;
        this.numNota = numNota;
        this.chaveNota = chaveNota;
        this.dtEmissao = dtEmissao;
        this.vlrTotal = vlrTotal;
        this.nomeFornecedor = nomeFornecedor;
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

    public void setSerieNota(String serieNota) {
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

    public String getNomeFornecedor() {
        return this.nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
}
