package ideau.controlePatrimonioAPI.model;

public class Patrimonio {
    private Long id;
    private String nomeItem;
    private Long idCategoria;
    private Long idSetor;
    private Long idStatus;
    private Long idNota;

//    Construtores
    public Patrimonio() {};

    public Patrimonio(Long id, String nomeItem, Long idCategoria, Long idSetor, Long idStatus, Long idNota) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.idCategoria = idCategoria;
        this.idSetor = idSetor;
        this.idStatus = idStatus;
        this.idNota = idNota;
    }

    public Patrimonio(String nomeItem, Long idCategoria, Long idSetor, Long idStatus, Long idNota) {
        this.nomeItem = nomeItem;
        this.idCategoria = idCategoria;
        this.idSetor = idSetor;
        this.idStatus = idStatus;
        this.idNota = idNota;
    }

//    Getters
    public Long getId() {
        return this.id;
    }
    public String getNomeItem() {
        return this.nomeItem;
    }
    public Long getIdCategoria() {
        return this.idCategoria;
    }
    public Long getIdSetor() {
        return this.idSetor;
    }
    public Long getIdStatus() {
        return this.idStatus;
    }
    public Long getIdNota() {
        return this.idNota;
    }

//    Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
    public void setIdSetor(Long idSetor) {
        this.idSetor = idSetor;
    }
    public void setIdStatus(Long idStatus) {this.idStatus = idStatus;}
    public void setIdNota(Long idNota) {this.idNota = idNota;}

//    toString
    @Override
    public String toString() {
        return "id: " + id + "; nomeItem: " + nomeItem + "; idCategoria: " + idCategoria + "; idSetor: " + idSetor +
                "; idStatus: " + idStatus + "; idNota: " + idNota;
    }
}

