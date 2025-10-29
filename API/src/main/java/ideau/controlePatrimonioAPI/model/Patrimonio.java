package ideau.controlePatrimonioAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Patrimonio {
    private Long id;
    private Long idItem;
    private Long idLocal;
    private Long idStatus;
    private Long idNota;
    private String numPatr;
    private BigDecimal valCompra;
    private BigDecimal aliqDeprecMes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dtAquisicao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Long idLocal) {
        this.idLocal = idLocal;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
        this.idNota = idNota;
    }

    public String getNumPatr() {
        return numPatr;
    }

    public void setNumPatr(String numPatr) {
        this.numPatr = numPatr;
    }

    public BigDecimal getvalCompra() {return this.valCompra;};

    public void setValCompra(BigDecimal valCompra) {this.valCompra = valCompra;};

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
    public Patrimonio() {};
    public Patrimonio(Long id, Long idItem, Long idStatus, Long idLocal, Long idNota, String numPatr,
                      BigDecimal valCompra, BigDecimal aliqDeprecMes, LocalDate dtAquisicao) {
        this.id = id;
        this.idItem = idItem;
        this.idStatus = idStatus;
        this.idLocal = idLocal;
        this.idNota = idNota;
        this.numPatr = numPatr;
        this.valCompra = valCompra;
        this.aliqDeprecMes = aliqDeprecMes;
        this.dtAquisicao = dtAquisicao;
    }

    public Patrimonio(Long idItem, Long idStatus, Long idLocal, Long idNota, String numPatr,
                      BigDecimal valCompra, BigDecimal aliqDeprecMes, LocalDate dtAquisicao) {
        this.idItem = idItem;
        this.idStatus = idStatus;
        this.idLocal = idLocal  ;
        this.idNota = idNota;
        this.numPatr = numPatr;
        this.valCompra = valCompra;
        this.aliqDeprecMes = aliqDeprecMes;
        this.dtAquisicao = dtAquisicao;
    }
}
