package ideau.ControlePatrimonioDesktop.model;

public class Categoria {
    Long id;
    String nome;

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public Categoria() {}
    public Categoria(String nome) {
        this.nome = nome;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
