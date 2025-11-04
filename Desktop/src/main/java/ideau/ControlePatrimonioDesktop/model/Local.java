package ideau.ControlePatrimonioDesktop.model;

public class Local {
    Long id;
    String nome;

    public Local(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public Local() {};
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
