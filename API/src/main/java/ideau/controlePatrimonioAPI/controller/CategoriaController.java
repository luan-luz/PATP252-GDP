package ideau.controlePatrimonioAPI.controller;

import ideau.controlePatrimonioAPI.controller.GenericController;
import ideau.controlePatrimonioAPI.model.Categoria;
import ideau.controlePatrimonioAPI.services.implementation.CategoriaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categoria")
public class CategoriaController implements GenericController<Categoria, Categoria> {
    private final CategoriaServiceImpl svc;

    public CategoriaController(CategoriaServiceImpl svc) {
        this.svc = svc;
    }

    @Override
    @PostMapping
    public Map<Integer, Categoria> cadastra(@RequestBody Map<Integer, Categoria> mapObjs) {
        return svc.cadastrarLote(mapObjs);
    }

    @Override
    @GetMapping
    public List<Categoria> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params="id")
    public Categoria retornaPorId(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    @PutMapping
    public Categoria atualiza(@RequestBody Categoria obj) {
        return svc.atualizar(obj);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleta(Long id) {
        return svc.deletar(id);
    }
}
