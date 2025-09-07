package ideau.controlePatrimonioAPI.repository;

import ideau.controlePatrimonioAPI.model.Patrimonio;

import java.util.List;

public interface PatrimonioRepository extends GenericRepository<Patrimonio> {
    List<Patrimonio> retornaPorIdCategoria(Long idCategoria);
    List<Patrimonio> retornaPorIdSetor(Long idSetor);
    List<Patrimonio> retornaPorIdStatus(Long idStatus);
    List<Patrimonio> retornaPorIdNota(Long idNota);
}
