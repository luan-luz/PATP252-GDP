package ideau.ControlePatrimonioDesktop.model;

public class RespostaHTTP {
    Integer httpStatus;
    String body;

    public RespostaHTTP(Integer httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
