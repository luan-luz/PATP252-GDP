package ideau.controlePatrimonioAPI.model;

public class ItemDTO {
    private Long id;
    private String nomeItem;
    private String nomeCategoria;
    private String nomeSetor;
    private String nomeStatus;

//    Construtores
    public ItemDTO() {};

    public ItemDTO(Long id) {
        this.id = id;
    }

    public ItemDTO(Long id, String nomeItem, String nomeCategoria, String nomeSetor, String nomeStatus) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.nomeCategoria = nomeCategoria;
        this.nomeSetor = nomeSetor;
        this.nomeStatus = nomeStatus;
    }

    public ItemDTO(String nomeItem, String nomeCategoria, String nomeSetor, String nomeStatus) {
        this.nomeItem = nomeItem;
        this.nomeCategoria = nomeCategoria;
        this.nomeSetor = nomeSetor;
        this.nomeStatus = nomeStatus;
    }


//    Getters
    public Long getId() {
        return this.id;
    }
    public String getNomeItem() {
        return this.nomeItem;
    }
    public String getNomeCategoria() {
        return this.nomeCategoria;
    }
    public String getNomeSetor() {
        return this.nomeSetor;
    }
    public String getNomeStatus() {
        return this.nomeStatus;
    }

//    Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }
    public void setNomeStatus(String nomeStatus) {this.nomeStatus = nomeStatus;}

//    toString
    @Override
    public String toString() {
        return "id: " + id + "; nomeItem: " + nomeItem + "; nomeCategoria: " + nomeCategoria + "; nomeSetor: " + nomeSetor +
                "; nomeStatus: " + nomeStatus;
    }
}

