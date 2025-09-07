package ideau.controlePatrimonioAPI.model;

import java.util.List;

public class RespostaListaErrosHttp {
    String strMensagem;
    List<String> lstErros;

    public RespostaListaErrosHttp(String strMensagem, List<String> lstErros) {
        this.strMensagem = strMensagem;
        this.lstErros = lstErros;
    }

    public String getStrMensagem() {
        return this.strMensagem;
    }
    public List<String> getLstErros() {
        return this.lstErros;
    }
}
