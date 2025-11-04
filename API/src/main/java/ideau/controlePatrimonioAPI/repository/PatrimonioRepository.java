package ideau.controlePatrimonioAPI.repository;

import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;

import java.util.List;

public interface PatrimonioRepository extends GenericRepository<Patrimonio, PatrimonioDTO> {
    List<PatrimonioDTO> retornaPorIdStatus(Long idStatus);
    List<PatrimonioDTO> retornaPorIdLocal(Long idLocal);
    List<PatrimonioDTO> retornaPorIdNota(Long idNota);
    boolean patrJaExiste(String numPatr);
}
