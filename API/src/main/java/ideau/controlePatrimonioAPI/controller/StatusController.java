package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.GenericController;
import ideau.controlePatrimonioAPI.model.Local;
import ideau.controlePatrimonioAPI.model.Status;
import ideau.controlePatrimonioAPI.services.implementation.LocalServiceImpl;
import ideau.controlePatrimonioAPI.services.implementation.StatusServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/status")
public class StatusController implements GenericController<Status, Status> {
    private final StatusServiceImpl svc;

    public StatusController(StatusServiceImpl svc) {
        this.svc = svc;
    }

    @Override
    @PostMapping
    public Map<Integer, Status> cadastra(@RequestBody Map<Integer, Status> mapObjs) {
        return svc.cadastrarLote(mapObjs);
    }

    @Override
    @GetMapping
    public List<Status> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params="id")
    public Status retornaPorId(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    @PutMapping
    public Status atualiza(@RequestBody Status obj) {
        return svc.atualizar(obj);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleta(Long id) {
        return svc.deletar(id);
    }
}
