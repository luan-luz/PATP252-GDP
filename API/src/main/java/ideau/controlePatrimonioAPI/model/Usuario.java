package ideau.controlePatrimonioAPI.model;


import jakarta.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long GrupoId;

    @Column(name = "usuario")
    private String usuario;

    @Column(nullable = false)
    private String senha;

    private String email;

    /*getters and setters */

    public String getSenha() {return senha;}
    public void setSenha(){this.senha = senha;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nome) {
        this.usuario = nome;
    } 

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getGrupoId(){   return GrupoId;}

    public void setGrupoId(){  this.GrupoId = GrupoId; }
}