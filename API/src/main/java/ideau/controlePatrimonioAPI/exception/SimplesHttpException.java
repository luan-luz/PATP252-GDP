package ideau.controlePatrimonioAPI.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class SimplesHttpException extends RuntimeException {
    HttpStatus httpStatus;
    public SimplesHttpException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
