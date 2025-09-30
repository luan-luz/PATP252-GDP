package ideau.controlePatrimonioAPI.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PatrimonioDTO {
    private Long id;
    private String nomeItem;
    private String nomeSetor;
    private String nomeStatus;
    private String numNota;
    private String serieNota;
    private String numPatr;
    private BigDecimal valCompra;
    private BigDecimal aliqDeprecMes;
    private LocalDate dtAquisicao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public String getNomeStatus() {
        return nomeStatus;
    }

    public void setNomeStatus(String nomeStatus) {
        this.nomeStatus = nomeStatus;
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

    public String getNumPatr() {
        return numPatr;
    }

    public void setNumPatr(String numPatr) {
        this.numPatr = numPatr;
    }
    public BigDecimal getValCompra() {
        return valCompra;
    }

    public void setValCompra(BigDecimal valCompra) {
        this.valCompra = valCompra;
    }

    public BigDecimal getAliqDeprecMes() {
        return aliqDeprecMes;
    }

    public void setAliqDeprecMes(BigDecimal aliqDeprecMes) {
        this.aliqDeprecMes = aliqDeprecMes;
    }

    public LocalDate getDtAquisicao() {
        return dtAquisicao;
    }

    public void setDtAquisicao(LocalDate dtAquisicao) {
        this.dtAquisicao = dtAquisicao;
    }

    public PatrimonioDTO(Long id, String nomeItem, String nomeStatus, String nomeSetor, String numNota, String serieNota,
                         String numPatr, BigDecimal valCompra, BigDecimal aliqDeprecMes, LocalDate dtAquisicao) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.nomeStatus = nomeStatus;
        this.nomeSetor = nomeSetor;
        this.numNota = numNota;
        this.serieNota = serieNota;
        this.numPatr = numPatr;
        this.valCompra = valCompra;
        this.aliqDeprecMes = aliqDeprecMes;
        this.dtAquisicao = dtAquisicao;
    }
    public PatrimonioDTO(String nomeItem, String nomeStatus, String nomeSetor, String numNota, String serieNota,
                         String numPatr, BigDecimal valCompra, BigDecimal aliqDeprecMes, LocalDate dtAquisicao) {
        this.nomeItem = nomeItem;
        this.nomeStatus = nomeStatus;
        this.nomeSetor = nomeSetor;
        this.numNota = numNota;
        this.serieNota = serieNota;
        this.numPatr = numPatr;
        this.valCompra = valCompra;
        this.aliqDeprecMes = aliqDeprecMes;
        this.dtAquisicao = dtAquisicao;
    }
    public PatrimonioDTO(Long id) {
        this.id = id;
    }
}

