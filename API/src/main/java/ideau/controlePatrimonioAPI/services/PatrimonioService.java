package ideau.controlePatrimonioAPI.services;

import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;

import java.util.List;

public interface PatrimonioService extends GenericService<Patrimonio, PatrimonioDTO>{
    List<PatrimonioDTO> retornarPorIdStatus(Long idStatus);
    List<PatrimonioDTO> retornarPorIdLocal(Long idLocal);
    List<PatrimonioDTO> retornarPorIdNota(Long idNota);
    boolean verificaExistenciaNumPatr(String numPatr);
}
