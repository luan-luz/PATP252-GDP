package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.PatrimonioController;
import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.services.PatrimonioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patrimonio")
public class PatrimonioControllerImpl implements PatrimonioController {
    private final PatrimonioService svc;

    public PatrimonioControllerImpl(PatrimonioService svc) {
        this.svc = svc;
    }

    @GetMapping("/teste")
    public String getTeste() {
        return "API funcionando!";
    }

    @Override
    @PostMapping
    public Map<Integer, Patrimonio> cadastroEmLote(@RequestBody Map<Integer, Patrimonio> mapPatrimonios) {
        return svc.cadastrarLote(mapPatrimonios);
    }

    @Override
    @GetMapping
    public List<Patrimonio> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params = "id")
    public Patrimonio retornaPorId(Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    @GetMapping(params = "idCategoria")
    public List<Patrimonio> retornaPorIdCategoria(@RequestParam Long idCategoria) {
        return svc.retornarPorIdCategoria(idCategoria);
    }


    @Override
    @GetMapping(params = "idSetor")
    public List<Patrimonio> retornaPorIdSetor(@RequestParam Long idSetor) {
        return svc.retornarPorIdSetor(idSetor);
    }

    @Override
    @GetMapping(params = "idStatus")
    public List<Patrimonio> retornaPorIdStatus(@RequestParam Long idStatus) {
        return svc.retornarPorIdStatus(idStatus);
    }

    @Override
    @GetMapping(params = "idNota")
    public List<Patrimonio> retornaPorIdNota(@RequestParam Long idNota) {
        return svc.retornarPorIdNota(idNota);
    }

    @Override
    @PutMapping
    public Patrimonio atualiza(@RequestBody Patrimonio obj) {
        System.out.println(obj.toString());
        return svc.atualizar(obj);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleta(@PathVariable("id") Long id) {
        return svc.deletar(id);
    }
}
