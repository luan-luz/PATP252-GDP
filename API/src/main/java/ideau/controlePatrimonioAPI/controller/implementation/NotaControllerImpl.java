package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.NotaController;
import ideau.controlePatrimonioAPI.model.Nota;
import ideau.controlePatrimonioAPI.model.NotaDTO;
import ideau.controlePatrimonioAPI.services.implementation.NotaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/nota")
public class NotaControllerImpl implements NotaController {
    private final NotaServiceImpl svc;

    public NotaControllerImpl(NotaServiceImpl svc) {
        this.svc = svc;
    }

    @Override
    public NotaDTO retornaPorIdFornecedor(Long idFornecedor) {
        return null;
    }

    @Override
    @PostMapping
    public Map<Integer, NotaDTO> cadastra(@RequestBody Map<Integer, Nota> mapObjs) {
        return svc.cadastrarLote(mapObjs);
    }

    @Override
    @GetMapping
    public List<NotaDTO> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params = "id")
    public NotaDTO retornaPorId(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    public NotaDTO atualiza(Nota obj) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleta(Long id) {
        return null;
    }
}
