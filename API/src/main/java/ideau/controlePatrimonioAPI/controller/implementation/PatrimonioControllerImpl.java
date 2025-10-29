package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.PatrimonioController;
import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import ideau.controlePatrimonioAPI.services.implementation.PatrimonioServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patrimonio")
public class PatrimonioControllerImpl implements PatrimonioController {
    private final PatrimonioServiceImpl svc;

    public PatrimonioControllerImpl(PatrimonioServiceImpl svc) {
        this.svc = svc;
    }

    @Override
    public List<PatrimonioDTO> retornaPorIdStatus(Long idStatus) {
        return List.of();
    }

    @Override
    public List<PatrimonioDTO> retornaPorIdLocal(Long idLocal) {
        return List.of();
    }

    @Override
    public List<PatrimonioDTO> retornaPorIdNota(Long idNota) {
        return List.of();
    }

    @Override
    @GetMapping(params = "numPatr")
    public boolean verificaExistenciaNumPatr(@RequestParam String numPatr) {
        return svc.verificaExistenciaNumPatr(numPatr);
    }

    @Override
    @PostMapping
    public Map<Integer, PatrimonioDTO> cadastra(@RequestBody Map<Integer, Patrimonio> mapObjs) {
        return svc.cadastrarLote(mapObjs);
    }

    @Override
    @GetMapping
    public List<PatrimonioDTO> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params = "id")
    public PatrimonioDTO retornaPorId(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    @PutMapping
    public PatrimonioDTO atualiza(@RequestBody Patrimonio obj) {
        return svc.atualizar(obj);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleta(Long id) {
        return svc.deletar(id);
    }
}
