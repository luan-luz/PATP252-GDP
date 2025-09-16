package ideau.controlePatrimonioAPI.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GenericController<IN, OUT> {
    Map<Integer, OUT> cadastroEmLote(Map<Integer, IN> mapObjs);
    List<OUT> retornaTodos();
    OUT retornaPorId(Long id);
    OUT atualiza(IN obj);
    ResponseEntity<Void> deleta(Long id);
}
