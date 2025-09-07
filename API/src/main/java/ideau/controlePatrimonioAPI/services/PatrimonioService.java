package ideau.controlePatrimonioAPI.services;

import ideau.controlePatrimonioAPI.model.Patrimonio;

import java.util.List;
import java.util.Map;

public interface PatrimonioService extends GenericService<Patrimonio> {
    Map<Integer, Patrimonio> cadastrarLote(Map<Integer, Patrimonio> mapPatrimonios);
    List<Patrimonio> retornarPorIdCategoria(Long idCategoria);
    List<Patrimonio> retornarPorIdSetor(Long idSetor);
    List<Patrimonio> retornarPorIdStatus(Long idStatus);
    List<Patrimonio> retornarPorIdNota(Long idNota);
}
