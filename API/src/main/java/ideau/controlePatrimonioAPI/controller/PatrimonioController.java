package ideau.controlePatrimonioAPI.controller;

import ideau.controlePatrimonioAPI.model.Patrimonio;

import java.util.HashMap;
import java.util.List;

public interface PatrimonioController extends GenericController<Patrimonio> {
    List<Patrimonio> retornaPorIdCategoria(Long idCategoria);
    List<Patrimonio> retornaPorIdSetor(Long idSetor);
    List<Patrimonio> retornaPorIdStatus(Long idStatus);
    List<Patrimonio> retornaPorIdNota(Long idNota);
}
