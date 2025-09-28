package ideau.controlePatrimonioAPI.model;


public class Patrimonio {
    private Long id;
    private Long idItem;
    private Long idSetor;
    private Long idStatus;
    private Long idNota;
    private String numPatr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Long getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(Long idSetor) {
        this.idSetor = idSetor;
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

    public Patrimonio(Long id, Long idItem, Long idStatus, Long idSetor, Long idNota, String numPatr) {
        this.id = id;
        this.idItem = idItem;
        this.idStatus = idStatus;
        this.idSetor = idSetor;
        this.idNota = idNota;
        this.numPatr = numPatr;
    }
    public Patrimonio(Long idItem, Long idStatus, Long idSetor, Long idNota, String numPatr) {
        this.idItem = idItem;
        this.idStatus = idStatus;
        this.idSetor = idSetor;
        this.idNota = idNota;
        this.numPatr = numPatr;
    }
}
