package ideau.controlePatrimonioAPI.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ValidacaoException extends RuntimeException{
    Map<Integer, String> mapErros;
    HttpStatus httpStatus;
    String strMensagem;

    public ValidacaoException(HttpStatus httpStatus, String strMensagem, Map<Integer, String> mapErros) {
        super(strMensagem);
        this.httpStatus = httpStatus;
        this.strMensagem = strMensagem;
        this.mapErros = mapErros;
    }
    Map<Integer, String> getMapErros() {
        return this.mapErros;
    }
}

