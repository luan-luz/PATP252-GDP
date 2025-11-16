package ideau.ControlePatrimonioDesktop.model;

public class Status {
    Long id;
    String nome;

    public Status(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public Status(String nome) {
        this.nome = nome;
    }
    public Status() {};

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
