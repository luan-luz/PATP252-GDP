package ideau.controlePatrimonioAPI.controller;

import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;

import java.util.List;

public interface PatrimonioController extends GenericController<Patrimonio, PatrimonioDTO>{
    List<PatrimonioDTO> retornaPorIdStatus(Long idStatus);
    List<PatrimonioDTO> retornaPorIdLocal(Long idLocal);
    List<PatrimonioDTO> retornaPorIdNota(Long idNota);
}
