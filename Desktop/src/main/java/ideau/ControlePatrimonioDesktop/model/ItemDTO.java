package ideau.ControlePatrimonioDesktop.model;

public class ItemDTO {
    private Long id;
    private String nomeItem;
    private String nomeCategoria;

    //    Construtores
    public ItemDTO() {};

    public ItemDTO(Long id) {
        this.id = id;
    }

    public ItemDTO(Long id, String nomeItem, String nomeCategoria) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.nomeCategoria = nomeCategoria;
    }

    public ItemDTO(String nomeItem, String nomeCategoria) {
        this.nomeItem = nomeItem;
        this.nomeCategoria = nomeCategoria;
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

    //    toString
    @Override
    public String toString() {
        return "id: " + id + "; nomeItem: " + nomeItem + "; nomeCategoria: " + nomeCategoria + ";";
    }
}

