package ideau.ControlePatrimonioDesktop.model;

public class Item {
    private Long id;
    private String nomeItem;
    private Long idCategoria;

    //    Construtores
    public Item() {};

    public Item(Long id, String nomeItem, Long idCategoria) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.idCategoria = idCategoria;
    }

    public Item(String nomeItem, Long idCategoria) {
        this.nomeItem = nomeItem;
        this.idCategoria = idCategoria;
    }

    //    Getters
    public Long getId() {
        return this.id;
    }
    public String getNomeItem() {
        return this.nomeItem;
    }
    public Long getIdCategoria() {return this.idCategoria;}

    //    Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }
    public void setIdCategoria(Long idCategoria) {this.idCategoria = idCategoria;}

    //    toString
    @Override
    public String toString() {
        return "id: " + id + "; nomeItem: " + nomeItem + "; categoria: " + idCategoria + ";";
    }
}

