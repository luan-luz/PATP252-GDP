package ideau.controlePatrimonioAPI.model;

import java.time.LocalDateTime;

public class Nota {
    private Long id;
    private String serie;
    private String numNota;
    private String chaveNota;
    private LocalDateTime dtEmissao;
    private Double vlrTotal;
    private Long idFornecedor;

    public Nota(Long id, String serie, String numNota, String chaveNota, LocalDateTime dtEmissao, Double vlrTotal, Long idFornecedor) {
        this.id = id;
        this.serie = serie;
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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
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

    public LocalDateTime getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(LocalDateTime dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public Double getVlrTotal() {
        return vlrTotal;
    }

    public void setVlrTotal(Double vlrTotal) {
        this.vlrTotal = vlrTotal;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
//falar com diretor