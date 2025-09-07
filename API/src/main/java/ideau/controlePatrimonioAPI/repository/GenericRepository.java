package ideau.controlePatrimonioAPI.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T> {
    Long salvar(T objeto, Connection con);
    List<T> retornaTodos();
    T retornaPorId(Long id);
    void atualizar(T objeto);
    void deletar(Long id);
}
