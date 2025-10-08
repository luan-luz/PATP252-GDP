package ideau.controlePatrimonioAPI.services;

import ideau.controlePatrimonioAPI.model.Nota;
import ideau.controlePatrimonioAPI.model.NotaDTO;

public interface NotaService extends GenericService<Nota, NotaDTO>{
    public NotaDTO retornarPorIdFornecedor(Long idFornecedor);
}
