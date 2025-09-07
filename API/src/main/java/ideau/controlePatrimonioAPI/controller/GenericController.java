package ideau.controlePatrimonioAPI.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GenericController<T> {
    Map<Integer, T> cadastroEmLote(Map<Integer, T> mapObjs);
    List<T> retornaTodos();
    T retornaPorId(Long id);
    T atualiza(T obj);
    ResponseEntity<Void> deleta(Long id);
}
