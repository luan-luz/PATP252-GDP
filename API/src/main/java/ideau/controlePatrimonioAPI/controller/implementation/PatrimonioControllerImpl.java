package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.PatrimonioController;
import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class PatrimonioControllerImpl implements PatrimonioController {
    @Override
    public List<PatrimonioDTO> retornaPorIdStatus(Long idStatus) {
        return List.of();
    }

    @Override
    public List<PatrimonioDTO> retornaPorIdSetor(Long idSetor) {
        return List.of();
    }

    @Override
    public List<PatrimonioDTO> retornaPorIdNota(Long idNota) {
        return List.of();
    }

    @Override
    public Map<Integer, PatrimonioDTO> cadastra(Map<Integer, Patrimonio> mapObjs) {
        return Map.of();
    }

    @Override
    public List<PatrimonioDTO> retornaTodos() {
        return List.of();
    }

    @Override
    public PatrimonioDTO retornaPorId(Long id) {
        return null;
    }

    @Override
    public PatrimonioDTO atualiza(Patrimonio obj) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleta(Long id) {
        return null;
    }
}
