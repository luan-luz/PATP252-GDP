package ideau.controlePatrimonioAPI.controller;

import ideau.controlePatrimonioAPI.model.Nota;
import ideau.controlePatrimonioAPI.model.NotaDTO;

public interface NotaController extends GenericController<Nota, NotaDTO> {
    public NotaDTO retornaPorIdFornecedor(Long idFornecedor);
}
