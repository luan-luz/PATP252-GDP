package ideau.controlePatrimonioAPI.services;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GenericService<IN, OUT> {
    Map<Integer, OUT> cadastrarLote(Map<Integer, IN> mapObjetos);
    List<OUT> retornarTodos();
    OUT retornaPorId(Long id);
    OUT atualizar(IN obj);
    ResponseEntity<Void> deletar(Long id);
}
