package ideau.ControlePatrimonioDesktop.model;

public class FornecedorDTO {
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private String CNPJ;
    private String IE;
    private String nomeLogradouro;
    private String numero;
    private String complemento;

    public FornecedorDTO() {};

    public FornecedorDTO(String razaoSocial, String nomeFantasia, String CNPJ, String IE, String nomeLogradouro, String numero, String complemento) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.CNPJ = CNPJ;
        this.IE = IE;
        this.nomeLogradouro = nomeLogradouro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public FornecedorDTO(Long id, String razaoSocial, String nomeFantasia, String CNPJ, String IE, String nomeLogradouro, String numero, String complemento) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.CNPJ = CNPJ;
        this.IE = IE;
        this.nomeLogradouro = nomeLogradouro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String IE) {
        this.IE = IE;
    }

    public String getNomeLogradouro() {
        return nomeLogradouro;
    }

    public void setNomeLogradouro(String nomeLogradouro) {
        this.nomeLogradouro = nomeLogradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
