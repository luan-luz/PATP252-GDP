package ideau.controlePatrimonioAPI.repository;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<IN, OUT> {
    Long salvar(IN objeto, Connection con);
    List<OUT> retornaTodos();
    OUT retornaPorId(Long id);
    OUT atualizar(IN objeto);
    void deletar(Long id);
}
