package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.FornecedorController;
import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.model.Fornecedor;
import ideau.controlePatrimonioAPI.repository.FornecedorRepository;
import ideau.controlePatrimonioAPI.services.implementation.FornecedorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorControllerImpl implements FornecedorController {
    private final FornecedorServiceImpl svc;

    public FornecedorControllerImpl(FornecedorServiceImpl svc) {
        this.svc = svc;
    }


    @Override
    @PostMapping
    public Map<Integer, Fornecedor> cadastra(@RequestBody Map<Integer, Fornecedor> mapObjs) {
        return svc.cadastrarLote(mapObjs);
    }

    @Override
    @GetMapping
    public List<Fornecedor> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params="id")
    public Fornecedor retornaPorId(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    @PutMapping
    public Fornecedor atualiza(@RequestBody Fornecedor obj) {
        return svc.atualizar(obj);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleta(Long id) {
        return svc.deletar(id);
    }
}
