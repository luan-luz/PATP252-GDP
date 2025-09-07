package ideau.controlePatrimonioAPI.services;

import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface GenericService<T> {
    Map<Integer, T> cadastrarLote(Map<Integer, T> mapObjetos) throws SQLException;
    List<T> retornarTodos();
    T retornaPorId(Long id);
    T atualizar(T obj);
    ResponseEntity<Void> deletar(Long id);
}
