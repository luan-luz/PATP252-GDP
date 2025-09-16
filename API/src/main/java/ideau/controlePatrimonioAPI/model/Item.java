package ideau.controlePatrimonioAPI.model;

public class Item {
    private Long id;
    private String nomeItem;
    private Long idCategoria;
    private Long idSetor;
    private Long idStatus;

//    Construtores
    public Item() {};

    public Item(Long id, String nomeItem, Long idCategoria, Long idSetor, Long idStatus) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.idCategoria = idCategoria;
        this.idSetor = idSetor;
        this.idStatus = idStatus;
    }

    public Item(String nomeItem, Long idCategoria, Long idSetor, Long idStatus) {
        this.nomeItem = nomeItem;
        this.idCategoria = idCategoria;
        this.idSetor = idSetor;
        this.idStatus = idStatus;
    }

//    Getters
    public Long getId() {
        return this.id;
    }
    public String getNomeItem() {
        return this.nomeItem;
    }
    public Long getIdCategoria() {return this.idCategoria;}
    public Long getIdSetor() {
        return this.idSetor;
    }
    public Long getIdStatus() {
        return this.idStatus;
    }

//    Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }
    public void setCategoria(Long idCategoria) {this.idCategoria = idCategoria;}
    public void setIdSetor(Long setor) {
        this.idSetor = setor;
    }
    public void setIdStatus(Long idStatus) {this.idStatus = idStatus;}

//    toString
    @Override
    public String toString() {
        return "id: " + id + "; nomeItem: " + nomeItem + "; categoria: " + idCategoria + "; setor: " + idSetor +
                "; idStatus: " + idStatus;
    }
}

