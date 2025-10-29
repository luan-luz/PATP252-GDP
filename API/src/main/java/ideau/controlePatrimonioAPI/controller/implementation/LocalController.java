package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.GenericController;
import ideau.controlePatrimonioAPI.model.Categoria;
import ideau.controlePatrimonioAPI.model.Local;
import ideau.controlePatrimonioAPI.services.implementation.CategoriaServiceImpl;
import ideau.controlePatrimonioAPI.services.implementation.LocalServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/local")
public class LocalController implements GenericController<Local, Local> {
    private final LocalServiceImpl svc;

    public LocalController(LocalServiceImpl svc) {
        this.svc = svc;
    }

    @Override
    @PostMapping
    public Map<Integer, Local> cadastra(@RequestBody Map<Integer, Local> mapObjs) {
        return svc.cadastrarLote(mapObjs);
    }

    @Override
    @GetMapping
    public List<Local> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params="id")
    public Local retornaPorId(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    @PutMapping
    public Local atualiza(@RequestBody Local obj) {
        return svc.atualizar(obj);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleta(Long id) {
        return svc.deletar(id);
    }
}
